package edu.pucmm.eict.services;
import edu.pucmm.eict.models.User;

public class UserServices extends DatabaseOrmHandler<User>{
  
    private static UserServices instance = null;
  
    private UserServices() {
      super(User.class);
    }
  
    public static UserServices getInstance() {
      
      if (instance == null) {
        instance = new UserServices();
      }
      return instance;
    }
  
}