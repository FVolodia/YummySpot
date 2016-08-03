package com.yummyspots.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.github.johnkil.print.PrintView;
import com.yummyspots.R;

/**
 * Created by FVolodia on 25.09.15.
 */
public class BadgeView extends PrintView {

    private boolean selected;
    private View.OnClickListener clickListener;
    private int hightLightColor;

    public BadgeView(Context context) {
        super(context);
        init();
    }

    public BadgeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public int getHightLightColor() {
        return hightLightColor;
    }

    public void setHightLightColor(int hightLightColor) {
        this.hightLightColor = hightLightColor;
    }

    private void init() {
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelected(!selected);
                if (clickListener != null) {
                    clickListener.onClick(v);
                }
            }
        });
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
        setIconColor(selected ? ContextCompat.getColor(getContext(), hightLightColor)
                : ContextCompat.getColor(getContext(), R.color.icon_active));
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        this.clickListener = l;
    }

}
