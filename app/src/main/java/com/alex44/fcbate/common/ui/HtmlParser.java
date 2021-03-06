package com.alex44.fcbate.common.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.alex44.fcbate.common.model.IImageLoader;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

public abstract class HtmlParser implements FullScreenVideoListener {
    private static final String PARAGRAPH_SPLIT = "<\\/p>\\r\\n<p>|<p>|<\\/p>";
    private static final String IMAGE_SPLIT = "<img";
    private static final String IMAGE_URL = "src=\"(http:.*.jpg)\"";
    private static final String TABLE_SPLIT = "<\\/tr>\\r\\n<tr>|<tr>?|<\\/tr>";
    private static final String ROW_SPLIT = "<\\/td><td>|<\\/?td>?";
    private static final String VIDEO_SPLIT = "<iframe";
    private static final String VIDEO_URL = "src=.*(?:youtube.com|youtu.be).*\\/(\\w+)\\/?\"";

    private static final String STRONG_TAG = "<strong>";
    private static final String TABLE_TAG = "<table";


    private final Pattern imagePattern = Pattern.compile(IMAGE_URL, Pattern.CASE_INSENSITIVE);
    private final Pattern rowPattern = Pattern.compile(ROW_SPLIT);
    private final Pattern widthPattern = Pattern.compile("width=\"(\\d+)\"");
    private final Pattern videoPattern = Pattern.compile(VIDEO_URL);

    private Context context;
    private IImageLoader<ImageView> imageLoader;

    public HtmlParser(Context context, IImageLoader<ImageView> imageLoader) {
        this.context = context;
        this.imageLoader = imageLoader;
    }

    public LinearLayout parseHtmlTextInto(String text, LinearLayout linearLayout) {
        final String[] paragraphs = text.split(PARAGRAPH_SPLIT);
        final int margin = getInPx(12);
        for (String p : paragraphs) {
            boolean bold = false;
            if (p.isEmpty()) continue;
            if (p.contains(IMAGE_SPLIT)) {
                final String[] images = p.split(IMAGE_SPLIT);
                for (String img : images) {
                    if (img.isEmpty()) continue;
                    linearLayout.addView(createImageView(img, margin));
                }
            }
            else if (p.contains(TABLE_TAG)) {
                linearLayout.addView(createTableLayout(p, Color.WHITE, 16, getInPx(8)));
            }
            else if (p.contains(VIDEO_SPLIT)) {
                final String[] videos = p.split(VIDEO_SPLIT);
                for (String video: videos) {
                    if (video.isEmpty()) continue;
                    if (video.replaceAll("\r\n", "").isEmpty()) continue;
                    final Matcher matcher = videoPattern.matcher(video);
                    if (matcher.find()) {
                        final String videoId = matcher.group(1);
                        linearLayout.addView(createVideoView(videoId, margin));
                    }
                }
            }
            else {
//                if (p.contains(STRONG_TAG)) { //TODO: подправить, чтоб не весь текст абзаца был жирным
//                    bold = true;
//                }
                p = parseHtml(p);
                final int marginText = getInPx(8);
                if (p.isEmpty()) continue;
                final TextView textView = createTextView(p, Color.WHITE, 16, marginText, marginText, bold);
                int marginUpDown = (int)(8*context.getResources().getDisplayMetrics().density);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, marginUpDown, 0, marginUpDown);
                textView.setLayoutParams(lp);
                linearLayout.addView(textView);
            }
        }
        return linearLayout;
    }

    private TextView createTextView(String text, int color, int textSize, int marginH, int marginV, boolean isBold) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(marginH, marginV, marginH, marginV);

        final TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextColor(color);
        textView.setLayoutParams(lp);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        if (isBold) {
            textView.setTypeface(null, Typeface.BOLD);
        }
        return textView;
    }

    private ImageView createImageView(String text, int margin) {
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, margin, 0, margin);

        final ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(lp);
        imageView.setMinimumHeight(10);
        final Matcher matcher = imagePattern.matcher(text);
        if (matcher.find()) {
            imageLoader.loadInto(matcher.group(1), imageView, 60);
        }
        return imageView;
    }

    private TableLayout createTableLayout(String text, int color, int textSize, int marginUpDown) {
        final TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        final TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);

        tableParams.setMargins(0, marginUpDown, 0, marginUpDown);
        final TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(tableParams);

        text = text.replaceAll(".*<tbody>", "");
        final String[] rows = text.split(TABLE_SPLIT);
        final List<String> rowsList = new ArrayList<>();
        for (String r : rows) {
            r = r.replaceAll("\r", "").replaceAll("\n", "");
            if (r.isEmpty()) continue;
            rowsList.add(r);
        }

        final List<Integer> widths = new ArrayList<>();
        for (int rowNum = 0; rowNum < rowsList.size(); rowNum++) {
            final String row = rowsList.get(rowNum);
            if (row.isEmpty()) continue;
            final Matcher matcher = rowPattern.matcher(row);
            if (matcher.find()) {
                final List<String> columns = parseTableRow(row);
                final TableRow tableRow = new TableRow(context);
                tableRow.setLayoutParams(rowParams);
                tableRow.setGravity(Gravity.CENTER_VERTICAL);
                for (int columnNum = 0; columnNum<columns.size(); columnNum++) {
                    String column = columns.get(columnNum);
                    if (rowNum == 0) {
                        final Matcher widthMatcher = widthPattern.matcher(column);
                        if (widthMatcher.find()) {
                            widths.add(Integer.valueOf(widthMatcher.group(1)));
                        }
                    }
                    else {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            column = Html.fromHtml(column, Html.FROM_HTML_MODE_COMPACT).toString().trim();
                        } else {
                            column = Html.fromHtml(column).toString().trim();
                        }
                        final TextView textView = createTextView(column, color, textSize, marginUpDown, marginUpDown, false);
                        Timber.d(textView.getWidth()+" "+textView.getMeasuredWidth());
                        final TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, widths.get(columnNum));
                        lp.setMargins(getInPx(2), 0, getInPx(2), 0);
                        textView.setLayoutParams(lp);
                        tableRow.addView(textView);
                    }
                }
                if (rowNum == 0) {
                    final int maxWidth = widths.get(getMaxNum(widths));
                    for (int i = 0; i < widths.size(); i++) {
                        if (widths.get(i) == maxWidth) {
                            tableLayout.setColumnShrinkable(i, true);
                        }
                    }
                    if (columns.size() > 8) {
                        textSize -= 6;
                    }
                    else if (getWidthsSum(widths) > 850) {
                        textSize -= 5;
                    }
                }
                else {
                    tableLayout.addView(tableRow);
                }
            }
        }
        return tableLayout;
    }

    private View createVideoView(String videoId, int margin) {
        final RelativeLayout relativeLayout = new RelativeLayout(context);
        final RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        relativeLayoutParams.setMargins(0, margin, 0, margin);
        relativeLayout.setLayoutParams(relativeLayoutParams);

        final YouTubePlayerView youTubePlayerView = new YouTubePlayerView(context);
        final ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        youTubePlayerView.setLayoutParams(layoutParams);
        youTubePlayerView.setEnableAutomaticInitialization(false);

        final IFramePlayerOptions iFramePlayerOptions = new IFramePlayerOptions.Builder()
                .controls(0)
                .rel(0)
                .build();

        youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(videoId, 0);
            }
        }, true, iFramePlayerOptions);

        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                goToFullScreen(videoId);
            }
            @Override
            public void onYouTubePlayerExitFullScreen() {}
        });

        relativeLayout.addView(youTubePlayerView);
        return relativeLayout;
    }

    private int getInPx(int val) {
        return (int) (val*context.getResources().getDisplayMetrics().density);
    }

    private String parseHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT).toString().trim();
        } else {
            return Html.fromHtml(html).toString().trim();
        }
    }

    private int getMaxNum(List<Integer> list) {
        if (list == null || list.isEmpty()) return 0;
        int maxNum = 0;
        int maxVal = list.get(0);
        for (int i=0; i<list.size(); i++) {
            if (list.get(i) > maxVal) {
                maxVal = list.get(i);
                maxNum = i;
            }
        }
        return maxNum;
    }

    private int getWidthsSum(List<Integer> list) {
        if (list == null || list.isEmpty()) return 0;
        int result = 0;
        for (int w : list) {
            result += w;
        }
        return result;
    }

    private List<String> parseTableRow(String row) {
        final List<String> columns = new ArrayList<>();
        final String openTag = "<td>";
        final String openTagTitle = "<td";
        final String closeTag = "</td>";
        int openTagLength = openTag.length();
        int openTagIndex = row.indexOf(openTag);
        if (openTagIndex == -1) {
            openTagIndex = row.indexOf(openTagTitle);
            openTagLength = openTagTitle.length();
        }
        int closeTagIndex = row.indexOf(closeTag);
        while(openTagIndex != -1) {
            if (closeTagIndex != -1) {
                final String column = row.substring(openTagIndex+openTagLength, closeTagIndex);
                columns.add(column);
                row = row.substring(closeTagIndex+closeTag.length());
                openTagIndex = row.indexOf(openTag);
                if (openTagIndex == -1) {
                    openTagIndex = row.indexOf(openTagTitle);
                }
                closeTagIndex = row.indexOf(closeTag);
            }
            else break;
        }
        return columns;
    }

}
