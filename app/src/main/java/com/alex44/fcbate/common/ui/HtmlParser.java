package com.alex44.fcbate.common.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alex44.fcbate.common.model.IImageLoader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

public class HtmlParser {
    private static final String PARAGRAPH_SPLIT = "<\\/p>\\r?\\n?<p>|<p>|<\\/p>";
    private static final String STRONG_TAG = "<strong>";
    private static final String IMAGE_TAG = "<img";
    private static final String IMAGE_URL = "src=\"(http:.*.jpg)\"";

    private final Pattern imagePattern = Pattern.compile(IMAGE_URL);
    private Context context;
    private IImageLoader<ImageView> imageLoader;

    public HtmlParser(Context context, IImageLoader<ImageView> imageLoader) {
        this.context = context;
        this.imageLoader = imageLoader;
    }

    public LinearLayout parseHtmlTextInto(String text, LinearLayout linearLayout) {
        final String[] paragraphs = text.split(PARAGRAPH_SPLIT);
        for (String p : paragraphs) {
            boolean bold = false;
            if (p.isEmpty()) continue;
            if (p.contains(IMAGE_TAG)) {
                final String[] images = p.split(IMAGE_TAG);
                for (String img : images) {
                    if (img.isEmpty()) continue;
                    linearLayout.addView(createImageView(img, 12));
                }
            }
            else {
//                if (p.contains(STRONG_TAG)) { //TODO: подправить, чтоб не весь текст абзаца был жирным
//                    bold = true;
//                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    p = Html.fromHtml(p, Html.FROM_HTML_MODE_COMPACT).toString().trim();
                } else {
                    p = Html.fromHtml(p).toString().trim();
                }
                if (p.isEmpty()) continue;
                linearLayout.addView(createTextView(p, Color.WHITE, 20, 8, bold));
            }
        }
        return linearLayout;
    }

    private TextView createTextView(String text, int color, int textSize, int marginUpDown, boolean isBold) {
        marginUpDown *= context.getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, marginUpDown, 0, marginUpDown);

        final TextView textView = new TextView(context);
        textView.setLayoutParams(lp);
        textView.setText(text);
        textView.setTextColor(color);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        if (isBold) {
            textView.setTypeface(null, Typeface.BOLD);
        }
        return textView;
    }

    private ImageView createImageView(String text, int marginUpDown) {
        marginUpDown *= context.getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, marginUpDown, 0, marginUpDown);

        final ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(lp);
        imageView.setMinimumHeight(10);
        final Matcher matcher = imagePattern.matcher(text);
        if (matcher.find()) {
            Timber.d("image url: %s", matcher.group(1));
            imageLoader.loadInto(matcher.group(1), imageView, 60);
        }
        return imageView;
    }

}
