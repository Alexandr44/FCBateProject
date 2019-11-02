package com.alex44.fcbate.common.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class ViewPagerExt extends ViewPager {

    private View viewForMeasure;

    public ViewPagerExt(Context context) {
        super(context);
    }

    public ViewPagerExt(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int mode = MeasureSpec.getMode(heightMeasureSpec);
//        // Unspecified means that the ViewPager is in a ScrollView WRAP_CONTENT.
//        // At Most means that the ViewPager is not in a ScrollView WRAP_CONTENT.
        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
            // super has to be called in the beginning so the child views can be initialized.
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int height = 0;
//            int count = getChildCount();
//            int curItem = getCurrentItem();
//            int childPos = (mCurrentPagePosition == 0) ? 0 : 1;
//            List<View> views = new ArrayList<>();
//            for (int i = 0; i < getChildCount(); i++) {
//                views.add(getChildAt(i));
//            }

//            for (int i = 0; i < getChildCount(); i++) {
                final View child = viewForMeasure!=null?viewForMeasure:getChildAt(0);// getChildAt(childPos);
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                final int h = child.getMeasuredHeight();
                if (h > height) height = h;
//            }
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
//        // super has to be called again so the new specs are treated as exact measurements
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


//        try {
//            View child = getChildAt(mCurrentPagePosition);
//            if (child != null) {
//                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//                int h = child.getMeasuredHeight();
//                heightMeasureSpec = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

//    public void reMeasureCurrentPage(int position) {
//        mCurrentPagePosition = position;
//        requestLayout();
//    }

    public void setViewForMeasure(View view) {
        this.viewForMeasure = view;
    }
}