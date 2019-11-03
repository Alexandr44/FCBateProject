package com.alex44.fcbate.teamdetail.ui;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alex44.fcbate.App;
import com.alex44.fcbate.R;
import com.alex44.fcbate.common.model.IImageLoader;
import com.alex44.fcbate.common.ui.BackButtonListener;
import com.alex44.fcbate.common.ui.ViewPagerExt;
import com.alex44.fcbate.team.model.enums.TeamItemType;
import com.alex44.fcbate.teamdetail.presenter.TeamDetailPresenter;
import com.alex44.fcbate.teamdetail.view.TeamDetailView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.material.tabs.TabLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class TeamDetailFragment extends MvpAppCompatFragment implements TeamDetailView, BackButtonListener {

    Pattern patternH = Pattern.compile("Рост:([\\d,.]+)");
    Pattern patternW = Pattern.compile("Вес:([\\d,.]+)");
    Pattern patternC = Pattern.compile("Гражданство:([\\S]+)");

    private View view;
    private Unbinder unbinder;

    @InjectPresenter
    TeamDetailPresenter presenter;

    @Named("Glide")
    @Inject
    protected IImageLoader<ImageView> imageLoader;

    @BindView(R.id.team_detail_pager)
    protected ViewPagerExt pager;

    @BindView(R.id.team_detail_tab_layout)
    protected TabLayout tabLayout;

    @BindView(R.id.team_detail_photo)
    protected ImageView photo;
    @BindView(R.id.team_detail_text_fio)
    protected TextView textFio;
    @BindView(R.id.team_detail_text_number)
    protected TextView textNum;
    @BindView(R.id.team_detail_text_spec)
    protected TextView textSpec;
    @BindView(R.id.team_detail_text_params)
    protected TextView textParams;
    @BindView(R.id.team_detail_text_country)
    protected TextView textCountry;

    public TeamDetailFragment() {
        App.getInstance().getAppComponent().inject(this);
    }

    public static TeamDetailFragment newInstance(TeamItemType type, Long newsId) {
        final Bundle arguments = new Bundle();
        arguments.putSerializable("type", type);
        arguments.putLong("id", newsId);
        final TeamDetailFragment fragment = new TeamDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @ProvidePresenter
    protected TeamDetailPresenter createPresenter() {
        TeamItemType type = null;
        Long id = null;
        if (getArguments() != null) {
            id = getArguments().getLong("id");
            type = (TeamItemType)getArguments().getSerializable("type");
        }

        final TeamDetailPresenter teamDetailPresenter = new TeamDetailPresenter(type, id, AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(teamDetailPresenter);
        return teamDetailPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_team_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Boolean backClick() {
        presenter.backClick();
        return true;
    }

    @Override
    public void initPager(TeamItemType type, Long id) {
        final TeamDetailPagerAdapter pagerAdapter = new TeamDetailPagerAdapter(getChildFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pagerAdapter.addFragment(new TeamDetailAnketaFragment(presenter.getTeamDetailAnketaPresenter()), "Анкета");
        pagerAdapter.addFragment(new TeamDetailBiographyFragment(presenter.getTeamDetailBiographyPresenter()), "Биография");
        pagerAdapter.addFragment(createTeamDetailPhotoFragment(type, id), "Фото");
        if (type == TeamItemType.PLAYERS) {
            pagerAdapter.addFragment(createTeamDetailStatisticFragment(id), "Статистика");
        }

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                final Fragment fragment = pagerAdapter.getItem(position);
                final View view = fragment.getView();
                pager.setViewForMeasure(view);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        pager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(pager);
    }

    private TeamDetailPhotoFragment createTeamDetailPhotoFragment(TeamItemType type, Long id) {
        final Bundle arguments = new Bundle();
        arguments.putSerializable("type", type);
        arguments.putLong("id", id);
        final TeamDetailPhotoFragment fragment = new TeamDetailPhotoFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    private TeamDetailStatisticFragment createTeamDetailStatisticFragment(Long id) {
        final Bundle arguments = new Bundle();
        arguments.putLong("id", id);
        final TeamDetailStatisticFragment fragment = new TeamDetailStatisticFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPhoto(String url) {
        imageLoader.loadIntoWithCrop(url, photo, 20);
    }

    @Override
    public void setFIO(String text) {
        textFio.setText(text);
    }

    @Override
    public void setNum(String text) {
        textNum.setText(String.format("№%s", text));
    }

    @Override
    public void setSpec(String text) {
        textSpec.setText(text);
    }

    @Override
    public void setParams(int age, String content) {
        final StringBuilder text = new StringBuilder(getAgeText(age) + "\n");
        final String contentStr = Html.fromHtml(content).toString().trim();

        final Matcher matcherH = patternH.matcher(contentStr);
        if (matcherH.find()) {
            text.append("Рост ")
                    .append(matcherH.group(1))
                    .append(" см\n");
        }

        final Matcher matcherW = patternW.matcher(contentStr);
        if (matcherW.find()) {
            text.append("Вес ")
                    .append(matcherW.group(1))
                    .append(" кг");
        }
        textParams.setText(text.toString());
    }

    @Override
    public void setAgeOnly(int age) {
        textParams.setText(getAgeText(age));
    }

    @Override
    public void setCountry(String text) {
        textCountry.setText(text);
    }

    @Override
    public void setCountryFromContent(String content) {
        final String contentStr = Html.fromHtml(content).toString().trim();
        final Matcher matcherC = patternC.matcher(contentStr);
        if (matcherC.find()) {
            textCountry.setText(matcherC.group(1));
        }
    }

    private String getAgeText(int age) {
        String ageStr = String.valueOf(age);
        final String lastDigit = ageStr.substring(ageStr.length() - 1);
        if (lastDigit.equals("1")) {
            ageStr += " год";
        } else if ((lastDigit.equals("2") || lastDigit.equals("3") || lastDigit.equals("4")) &&
        (age != 11 && age != 12 && age != 13 && age != 14)) {
            ageStr += " года";
        } else {
            ageStr += " лет";
        }
        return ageStr;
    }

}
