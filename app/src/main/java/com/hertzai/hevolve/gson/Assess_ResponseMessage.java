package com.hertzai.hevolve.gson;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Assess_ResponseMessage {
    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public Integer getAssessment_id() {
        return assessment_id;
    }

    public void setAssessment_id(Integer assessment_id) {
        this.assessment_id = assessment_id;
    }

    @SerializedName("subject")
private String subject;

    @SerializedName("assessment_id")
    private Integer assessment_id;

    @SerializedName("student_id")
    private String student_id;
    @SerializedName("assessment")
    private String assessment;

    public Assess_ResponseMessage(ArrayList<String> optionLists) {
        this.optionLists = optionLists;
    }

    public String getSubject() {
        return subject;
    }

    public ArrayList<String> getOptionLists() {
        return optionLists;
    }

    public void setOptionLists(ArrayList<String> optionLists) {
        this.optionLists = optionLists;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @SerializedName("user_id")
    private String user_id;
    @SerializedName("question_no")
    private Integer questionNo;
    @SerializedName("text")
    private ArrayList<String> answerList;
    @SerializedName("options")
    private ArrayList<String> optionLists;

    public String getQlist() {
        return qlist;
    }

    public void setQlist(String qlist) {
        this.qlist = qlist;
    }

    @SerializedName("qlist")
    private String qlist;

    public String getVideo_link() {
        return video_link;
    }

    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }

    @SerializedName("video_link")
    private String video_link;

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public Integer getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(Integer questionNo) {
        this.questionNo = questionNo;
    }

    public ArrayList<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(ArrayList<String> answerList) {
        this.answerList = answerList;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}


