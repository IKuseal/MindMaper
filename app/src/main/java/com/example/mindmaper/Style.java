package com.example.mindmaper;

public class Style {
    private int styleId;
    private int borderWidth;
    private int borderColor;
    private int textSize;
    private int textColor;
    private int backgroundColor;

    public Style(int styleId, int borderWidth, int borderColor, int textSize, int textColor, int backgroundColor) {
        this.styleId = styleId;
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
        this.textSize = textSize;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
