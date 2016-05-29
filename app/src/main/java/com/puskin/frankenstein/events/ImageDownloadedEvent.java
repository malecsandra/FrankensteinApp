package com.puskin.frankenstein.events;

import com.puskin.frankenstein.models.ImageModel;

/**
 * Created by Alexandra on 29-May-16.
 */
public class ImageDownloadedEvent {
    private int responseCode;
    private String responseMessage;

    ImageModel imageModel;

    public ImageDownloadedEvent(int responseCode, String responseMessage, ImageModel imageModel) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.imageModel = imageModel;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public ImageModel getImageModel() {
        return imageModel;
    }

    public void setImageModel(ImageModel imageModel) {
        this.imageModel = imageModel;
    }
}
