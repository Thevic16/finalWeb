package edu.pucmm.eict.services.soap;

import edu.pucmm.eict.models.*;
import edu.pucmm.eict.services.FormServices;
import edu.pucmm.eict.services.PositionServices;
import edu.pucmm.eict.services.UserServices;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class FormWebService {

  private FormServices formServices = FormServices.getInstance();

  @WebMethod
  public List<FormSoap> getForms() {
    List<Form> rawForms = formServices.findAll();
    List<FormSoap> formsSoap = new ArrayList<>();
    for (Form rawForm : rawForms) {
      FormSoap formSoap = new FormSoap(rawForm.getName(), rawForm.getLastName(), rawForm.getArea(), rawForm.getNivelEscolar(), Double.toString(rawForm.getPosition().getLongitude()), Double.toString(rawForm.getPosition().getLatitude()), rawForm.getUser().getUserName(), rawForm.getPhotoBase64());
      formsSoap.add(formSoap);
    }
    return formsSoap;
  }

  @WebMethod
  public FormSoap createForm(FormSoap formSoap) {

    User user = UserServices.getInstance().find(formSoap.getUser());

    Position position = new Position(Double.parseDouble(formSoap.getLatitude()), Double.parseDouble(formSoap.getLongitude()));

    position = PositionServices.getInstance().create(position);

    if (user == null || position == null) {
      formSoap = null;
      return formSoap;
    }

    Form form = new Form(formSoap.getName(), formSoap.getLastName(), formSoap.getArea(), formSoap.getSchoolLevel(), user, position, formSoap.getPhotoBase64());

    form = formServices.create(form);

    if (form == null) formSoap = null;

    return formSoap;
  }

}