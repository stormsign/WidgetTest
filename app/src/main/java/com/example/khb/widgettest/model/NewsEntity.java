package com.example.khb.widgettest.model;

import java.io.Serializable;

/**
 * Created by khb on 2016/6/6.
 */
public class NewsEntity implements Serializable{

    String id;
    String title;
    String image;
    String createTime;
    long propertyId;
    String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "NewsEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", createTime='" + createTime + '\'' +
                ", propertyId=" + propertyId +
                ", description='" + description + '\'' +
                '}';
    }

}
