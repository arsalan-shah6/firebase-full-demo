package com.app.firebasefulldemo.Model;

public class CustomModel {
    String Course, Contact, Name, ImageLink;

    public CustomModel() {
    }

    public CustomModel(String course, String contact, String name, String imageLink) {
        Course = course;
        Contact = contact;
        Name = name;
        ImageLink = imageLink;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageLink() {
        return ImageLink;
    }

    public void setImageLink(String imageLink) {
        ImageLink = imageLink;
    }
}
