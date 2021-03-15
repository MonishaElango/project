package com.hertzai.hevolve.models.appModel;

public class MainModel {

    Integer teacherImages;
    String name;

    public Integer getTeacherImages() {
        return teacherImages;
    }

    public MainModel(Integer teacherImages, String name) {
        this.teacherImages = teacherImages;
        this.name = name;
    }

    public void setTeacherImages(Integer teacherImages) {
        this.teacherImages = teacherImages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
