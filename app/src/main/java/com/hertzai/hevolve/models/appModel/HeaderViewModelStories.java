package com.hertzai.hevolve.models.appModel;

public class HeaderViewModelStories {

    Integer teachers;
    String bytes_info;
    String know;

    public HeaderViewModelStories(Integer teachers, String bytes_info , String know) {
        this.teachers = teachers;
        this.bytes_info = bytes_info;
        this.know = know;
    }



    public String getKnow() {
        return know;
    }

    public void setKnow(String know) {
        this.know = know;
    }

    public Integer getTeachers() {

        return teachers;
    }

    public void setTeachers(Integer teachers) {
        this.teachers = teachers;
    }

    public String getBytes() {
        return bytes_info;
    }

    public void setBytes(String bytes) {
        this.bytes_info = bytes;
    }
}
