package edu.pucmm.eict.services;

import edu.pucmm.eict.models.Form;

public class FormServices extends DatabaseOrmHandler<Form>{
  
    private static FormServices instance = null;
  
    private FormServices() {
      super(Form.class);
    }
  
    public static FormServices getInstance() {
      
      if (instance == null) {
        instance = new FormServices();
      }
      return instance;
    }
  
}