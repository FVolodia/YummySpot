package com.yummyspots.foursquare.model;

/**
 * Created by FVolodia on 02.12.15.
 */
public class BestPhoto {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 360;
    private static final String SIZE_PARAM = WIDTH + "x" + HEIGHT;

    private String prefix;
    private String suffix;

    private String urlPhoto;

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getUrlPhoto() {
        this.urlPhoto = getPrefix() + BestPhoto.SIZE_PARAM + getSuffix();
        return urlPhoto;
    }


}
