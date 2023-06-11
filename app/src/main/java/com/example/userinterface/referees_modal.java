package com.example.userinterface;

public class referees_modal {
    private  String name;
    private  String title;
    private  String email;


    public referees_modal(String name, String title, String email) {
        this.name = name;
        this.title = title;
        this.email = email;

    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getEmail() {
        return email;
    }


}
