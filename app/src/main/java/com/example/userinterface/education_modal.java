package com.example.userinterface;

public class education_modal {

    private  String field;
    private  String level;
    private  String institution;
    private  String start_year;
    private  String end_year;

    public education_modal(String field, String level, String institution, String start_year, String end_year) {
        this.field = field;
        this.level = level;
        this.institution = institution;
        this.start_year = start_year;
        this.end_year = end_year;
    }

    public String getField() {
        return field;
    }

    public String getLevel() {
        return level;
    }

    public String getInstitution() {
        return institution;
    }

    public String getStart_year() {
        return start_year;
    }

    public String getEnd_year() {
        return end_year;
    }
}
