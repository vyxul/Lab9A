package com.example.st1013838.phonebook;

/**
 * Created by Steven on 4/22/2018.
 */

public class Contact {
    private String FName, LName, PNumber;

    public Contact (){
    }

    public Contact (String first, String last, String phone){
        FName = first;
        LName = last;
        PNumber = phone;
    }

    public void setFName (String s){
        FName = s;
    }

    public void setLName (String s){
        LName = s;
    }

    public void setPNumber (String s){
        PNumber = s;
    }

    public String getFName (){
        return FName;
    }

    public String getLName (){
        return LName;
    }

    public String getPNumber (){
        return PNumber;
    }
}
