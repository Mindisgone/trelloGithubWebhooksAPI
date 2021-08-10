package com.teeceetech.trellogithubwebhooksapi.web.models.trello;

public class Cover {
    public Object idAttachment;
    public Object color;
    public Object idUploadedBackground;
    public String size;
    public String brightness;
    public Object idPlugin;

    public Cover() {
    }

    public Object getIdAttachment() {
        return idAttachment;
    }

    public void setIdAttachment(Object idAttachment) {
        this.idAttachment = idAttachment;
    }

    public Object getColor() {
        return color;
    }

    public void setColor(Object color) {
        this.color = color;
    }

    public Object getIdUploadedBackground() {
        return idUploadedBackground;
    }

    public void setIdUploadedBackground(Object idUploadedBackground) {
        this.idUploadedBackground = idUploadedBackground;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBrightness() {
        return brightness;
    }

    public void setBrightness(String brightness) {
        this.brightness = brightness;
    }

    public Object getIdPlugin() {
        return idPlugin;
    }

    public void setIdPlugin(Object idPlugin) {
        this.idPlugin = idPlugin;
    }
}
