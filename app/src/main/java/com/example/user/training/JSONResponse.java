package com.example.user.training;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSONResponse {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("image")
    @Expose
    private String image;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
