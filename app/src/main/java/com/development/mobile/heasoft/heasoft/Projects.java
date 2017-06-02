package com.development.mobile.heasoft.heasoft;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Projects {

    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("projects")
    @Expose
    private List<Project> projects = null;

    public String getCompany() {
        return company;
    }

    public String getCopyright() {
        return copyright;
    }

    public List<Project> getProjects() {
        return projects;
    }

}