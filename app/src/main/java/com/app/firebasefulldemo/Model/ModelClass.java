package com.app.firebasefulldemo.Model;

public class ModelClass {
    String name,coures,contact;

    public ModelClass() {
    }

    public ModelClass(String name, String coures, String contact) {
        this.name = name;
        this.coures = coures;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoures() {
        return coures;
    }

    public void setCoures(String coures) {
        this.coures = coures;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
