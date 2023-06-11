package com.example.userinterface;

public class experience_modal {
    private String company;
    private String title;
    private String start_year;
    private String end_year;

    public experience_modal(String company, String title, String start_year, String end_year) {
        this.company = company;
        this.title = title;
        this.start_year = start_year;
        this.end_year = end_year;
    }

    public String getCompany() {
        return company;
    }

    public String getTitle() {
        return title;
    }

    public String getStart_year() {
        return start_year;
    }

    public String getEnd_year() {
        return end_year;
    }
}
