package edu.pucmm.eict.models;

import edu.pucmm.eict.services.FormServices;

import java.util.ArrayList;
import java.util.List;

public class FormApi {
    //This one do not need to have a ID because the ID is manage by DB. This one is only a interface.
    private String name;
    private String lastName;
    private String area;
    private String nivelEscolar;
    private String userName;
    private double latitude;
    private double longitude;

    private String photoBase64;

    public FormApi(String name, String lastName, String area, String nivelEscolar, String userName,double latitude,double longitude, String photo) {
        this.name = name;
        this.lastName = lastName;
        this.area = area;
        this.nivelEscolar = nivelEscolar;
        this.userName = userName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photoBase64 = photo;
    }

    public FormApi(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getNivelEscolar() {
        return nivelEscolar;
    }

    public void setNivelEscolar(String nivelEscolar) {
        this.nivelEscolar = nivelEscolar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }

    public static FormApi createForm(FormApi tmp){
        // I need to implement this later
        // We need to confirm that the form is not already in the database.

        return tmp;
    }

    public static List<FormApi> getFilteredForms(String userName){
        List<Form> forms = FormServices.getInstance().findAll();
        List<FormApi> filteredForms = new ArrayList<FormApi>();

        for (Form form:forms) {
            if(form.getUser().getUserName().equals(userName)){
                FormApi formApi = new FormApi(form.getName(),form.getLastName(),form.getArea(),
                        form.getNivelEscolar(),form.getUser().getUserName(),form.getPosition().getLatitude(),form.getPosition().getLongitude(),
                        "foto");
                filteredForms.add(formApi);
            }
        }
        return filteredForms;
    }

}

