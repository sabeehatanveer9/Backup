package com.example.naveed.backup;

import java.util.ArrayList;

public class ModelData {

    private  String contactName, Number;

    public ModelData() {
    }

    public ModelData(String contactName, String number) {
        this.contactName = contactName;
        Number = number;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }
}
