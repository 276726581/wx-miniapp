package com.jaspercloud.shipkeeper.entity;

public class ImageInfo extends ImageSize {

    private String fullName;
    private String imageName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public ImageInfo() {
    }


}
