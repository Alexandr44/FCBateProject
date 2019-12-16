package com.alex44.fcbate.info.ui;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.alex44.fcbate.App;
import com.alex44.fcbate.BuildConfig;
import com.alex44.fcbate.R;
import com.alex44.fcbate.common.ui.BackButtonListener;
import com.alex44.fcbate.common.ui.FileUtils;
import com.alex44.fcbate.info.presenter.InfoPresenter;
import com.alex44.fcbate.info.view.InfoView;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends MvpAppCompatFragment implements InfoView, BackButtonListener {

    private static final String SITE_URL = "https://www.fcbate.by/";
    private static final String FORUM_URL = "https://forum.fcbate.by/";
    
    private static final String DEVELOPER_EMAIL = "developer@fcbate.ru";

    private List<String> sizeLabels;

    private View view;
    private Unbinder unbinder;

    @InjectPresenter
    InfoPresenter presenter;

    @BindView(R.id.version_text)
    protected TextView versionText;
    @BindView(R.id.cache_size_text)
    protected TextView cacheText;

    public InfoFragment() {
    }

    public static Fragment newInstance() {
        return new InfoFragment();
    }

    @ProvidePresenter
    protected InfoPresenter createPresenter() {
        final InfoPresenter infoPresenter = new InfoPresenter();
        App.getInstance().getAppComponent().inject(infoPresenter);
        return infoPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        sizeLabels = new ArrayList<String>() {{
            add(getResources().getString(R.string.byte_txt));
            add(getResources().getString(R.string.kilobyte_txt));
            add(getResources().getString(R.string.megabyte_txt));
            add(getResources().getString(R.string.gigabyte_txt));
            add(getResources().getString(R.string.terabyte_txt));
        }};
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

    @OnClick(R.id.site_layout)
    public void siteClick() {
        presenter.siteClicked();
    }

    @OnClick(R.id.forum_layout)
    public void forumClick() {
        presenter.forumClicked();
    }

    @OnClick(R.id.rate_layout)
    public void rateClick() {
        presenter.rateClicked();
    }

    @OnClick(R.id.write_layout)
    public void writeClick() {
        presenter.writeClicked();
    }

    @Override
    public void rate() {

    }

    @OnClick(R.id.clean_data_layout)
    public void cleanCache() {
        presenter.cleanCacheClicked();
    }

    @Override
    public void writeToDevelopers() {
        if (checkPackagemanager()) {
            final PackageManager packageManager = getContext().getPackageManager();

            final Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ DEVELOPER_EMAIL });
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.email_subject));
            emailIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.email_body));
            
            if (emailIntent.resolveActivity(packageManager) != null) {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
            else {
                showMessage("There are no email clients installed");
            }
        }
    }

    @Override
    public void openSite() {
        launchWebIntent(SITE_URL);
    }

    @Override
    public void openForum() {
        launchWebIntent(FORUM_URL);
    }

    @Override
    public void updateVersion() {
        final String versionStr = "v " + BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")";
        versionText.setText(versionStr);
    }

    @Override
    public void cleanAppCache() {
        if (getContext() != null) {
            FileUtils.deleteDir(getContext().getCacheDir());
            if (getContext().getExternalCacheDir() != null) {
                FileUtils.deleteDir(getContext().getExternalCacheDir());
            }
        }
        presenter.cleanFinished();
    }

    @Override
    public void countAppCacheSize() {
        long cacheSize = 0L;
        if (getContext() != null) {
            cacheSize = FileUtils.getDirSize(getContext().getCacheDir());
            if (getContext().getExternalCacheDir() != null) {
                cacheSize += FileUtils.getDirSize(getContext().getExternalCacheDir());
            }
        }
        presenter.cacheSizeCalculated(FileUtils.getHumanableSize(cacheSize, sizeLabels));
    }

    @Override
    public void updateCacheSize(String sizeStr) {
        cacheText.setText(sizeStr);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    private boolean checkPackagemanager() {
        if (getContext() == null || getContext().getPackageManager() == null) {
            showMessage("Package manager is unavailable");
            return false;
        }
        return true;
    }

    private void launchWebIntent(String url) {
        if (checkPackagemanager()) {
            final PackageManager packageManager = getContext().getPackageManager();
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent);
            }
        }
    }

}
