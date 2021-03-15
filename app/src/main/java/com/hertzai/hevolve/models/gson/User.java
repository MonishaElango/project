package com.hertzai.hevolve.models.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class User {

    @SerializedName("user")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @SerializedName("detail")
    private String detail;

    @SerializedName("name")
    private String name;

    @SerializedName("gender")
    private String gender;

    @SerializedName("age")
    private Integer age;

    @SerializedName("email_address")
    private String email_address;

    @SerializedName("phone_number")
    private String phone_number;

    @SerializedName("who_pays_for_course")
    private String who_pays_for_course;

    @SerializedName("english_proficiency")
    private String english_proficiency;

    @SerializedName("preferred_language")
    private String preferred_language;

    @SerializedName("student_id")
    private String student_id;

    @SerializedName("school")
    private String school;

    @SerializedName("batch")
    private String batch;

    @SerializedName("client")
    private String client;

    @SerializedName("access_token")
    String accessToken;

    @SerializedName("refresh_token")
    String refreshToken;

    public User(String name, String gender, Integer age, String email_address, String phone_number, String who_pays_for_course, String english_proficiency, String preferred_language, String student_id, String school, String batch, String client) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.email_address = email_address;
        this.phone_number = phone_number;
        this.who_pays_for_course = who_pays_for_course;
        this.english_proficiency = english_proficiency;
        this.preferred_language = preferred_language;
        this.student_id = student_id;
        this.school = school;
        this.batch = batch;
        this.client = client;
    }

    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getWho_pays_for_course() {
        return who_pays_for_course;
    }

    public void setWho_pays_for_course(String who_pays_for_course) {
        this.who_pays_for_course = who_pays_for_course;
    }

    public String getEnglish_proficiency() {
        return english_proficiency;
    }

    public void setEnglish_proficiency(String english_proficiency) {
        this.english_proficiency = english_proficiency;
    }

    public String getPreferred_language() {
        return preferred_language;
    }

    public void setPreferred_language(String preferred_language) {
        this.preferred_language = preferred_language;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public static String toJson(User user) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(user);
    }
    public static User parse(String userData) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.fromJson(userData, User.class);
    }


    }


