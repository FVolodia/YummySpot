package com.yummyspots.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.yummyspots.R;

/**
 * Created by FVolodia on 29.11.15.
 */
public class PlaceDetailView extends RelativeLayout {

    private String iconDetail;
    private String mainText;
    private String label;
    private PrintView printView;
    private TextView textViewMain;
    private TextView textViewLabel;

    public PlaceDetailView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_place_detail, this);
    }

    public PlaceDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PlaceDetailView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public PlaceDetailView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void setIconDetail(String iconDetail) {
        this.iconDetail = iconDetail;
        printView.setIconText(iconDetail);
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
        textViewMain.setText(mainText);
    }

    public void setLabel(String label) {
        this.label = label;
        textViewLabel.setText(label);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.PlaceDetailView, 0, 0);

        try {
            iconDetail = a.getString(R.styleable.PlaceDetailView_iconDetail);
            mainText = a.getString(R.styleable.PlaceDetailView_mainText);
            label = a.getString(R.styleable.PlaceDetailView_label);

        } finally {
            a.recycle();
        }

        LayoutInflater.from(context).inflate(R.layout.view_place_detail, this);

        printView = (PrintView) this.findViewById(R.id.pvIcon);
        printView.setIconText(iconDetail);

        textViewMain = (TextView) findViewById(R.id.tvMainText);
        textViewMain.setText(mainText);

        textViewLabel = (TextView) findViewById(R.id.tvLabel);
        textViewLabel.setText(label);
    }
}
