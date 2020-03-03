package com.example.clinic;

public class RegisterUser {
    String email;
    String password;
    String fname;
    String lname;
    String dob;
    String profileimageurl;



    public RegisterUser(String fname, String lname, String dob, String email, String password) {
        this.email = email;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.profileimageurl=" ";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getimageurl() {
        return profileimageurl;
    }

    public void setimageurl(String profileimageurl) {
        this.profileimageurl = profileimageurl;
    }
}
