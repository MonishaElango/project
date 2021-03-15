package com.hertzai.hevolve.models.appModel;

import java.util.ArrayList;

public class StoriesItemModel {

    Integer teachers;
    String bytes;

    public StoriesItemModel(Integer teachers, String bytes) {
        this.teachers = teachers;
        this.bytes = bytes;
    }


    public Integer getTeachers() {
        return teachers;
    }

    public void setTeachers(Integer teachers) {
        this.teachers = teachers;
    }

    public String getBytes() {
        return bytes;
    }

    public void setBytes(String bytes) {
        this.bytes = bytes;
    }
}
