package com.jeevan.gcntask.Model;

public class Data {
    private String title;
    private String note;
    private String date;
    private String id;
    private String profilename;
    private String phoneno;
    private String officepost;
    private String officedepartment;


    public Data(){

    }

    public Data(String title, String note, String date, String id) {
        this.title = title;
        this.note = note;
        this.date = date;
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public Data(String profilename, String phoneno, String officepost, String officedepartment, String id) {
        this.profilename = profilename;
        this.phoneno = phoneno;
        this.officepost = officepost;
        this.officedepartment = officedepartment;
        this.id = id;


    }

    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno)  {
        this.phoneno = phoneno;
    }

    public String getOfficepost() {
        return officepost;
    }

    public void setOfficepost(String officepost) {
        this.officepost = officepost;
    }

    public String getOfficedepartment() {
        return officedepartment;
    }

    public void setOfficedepartment(String officedepartment) {
        this.officedepartment = officedepartment;
    }

}
