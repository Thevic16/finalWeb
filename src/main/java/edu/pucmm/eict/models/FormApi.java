package edu.pucmm.eict.models;

import edu.pucmm.eict.services.FormServices;
import edu.pucmm.eict.services.PositionServices;
import edu.pucmm.eict.services.UserServices;

import javax.xml.stream.Location;
import java.util.ArrayList;
import java.util.List;

public class FormApi {
    //This one do not need to have a ID because the ID is manage by DB. This one is only a interface.
    private String name;
    private String lastName;
    private String area;
    private String nivelEscolar;
    private String user;
    private double latitude;
    private double longitude;

    private String photo;

    public FormApi(String name, String lastName, String area, String nivelEscolar, String user,double latitude,double longitude, String photo) {
        this.name = name;
        this.lastName = lastName;
        this.area = area;
        this.nivelEscolar = nivelEscolar;
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
        this.photo = photo;
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public static FormApi createForm(FormApi tmp){
        // I need to implement this later
        // We need to confirm that the form is not already in the database.
        Position position = new Position(tmp.getLatitude(),tmp.getLongitude());
        PositionServices.getInstance().create(position);
        Form newForm = new Form(tmp.getName(),tmp.getLastName(),tmp.getArea(),tmp.getNivelEscolar(), UserServices.getInstance().find(tmp.getUser()),position,tmp.getPhoto());
        FormServices.getInstance().create(newForm);

        return tmp;
    }

    public static List<FormApi> getFilteredForms(String userName){
        List<Form> forms = FormServices.getInstance().findAll();
        List<FormApi> filteredForms = new ArrayList<FormApi>();

        for (Form form:forms) {
            if(form.getUser().getUserName().equals(userName)){
                FormApi formApi = new FormApi(form.getName(),form.getLastName(),form.getArea(),
                        form.getNivelEscolar(),form.getUser().getUserName(),form.getPosition().getLatitude(),form.getPosition().getLongitude(),
                        form.getPhoto());
                filteredForms.add(formApi);
            }
        }
        return filteredForms;
    }

}

