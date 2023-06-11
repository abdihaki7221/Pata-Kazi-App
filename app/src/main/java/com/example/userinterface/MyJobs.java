package com.example.userinterface;

public class MyJobs {
    private String job_title;

    private  String job_posted_date;
    private  String job_deadline_date;
    private  String acronym_text;

    public MyJobs() {
    }

    //constructors


    public MyJobs(String job_title, String job_posted_date, String job_deadline_date,String acronym_text) {

            this.job_title=job_title;

        this.job_posted_date = job_posted_date;
        this.job_deadline_date = job_deadline_date;
        this.acronym_text = acronym_text;
    }

    //getters
    public String getJob_title() {
        return job_title;
    }



    public String getJob_posted_date() {
        return job_posted_date;
    }

    public String getJob_deadline_date() {
        return job_deadline_date;
    }

    public String getAcronym_text() {
        return acronym_text;
    }
}
