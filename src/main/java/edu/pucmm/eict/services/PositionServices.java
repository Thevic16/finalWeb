package edu.pucmm.eict.services;

import edu.pucmm.eict.models.Position;

public class PositionServices extends DatabaseOrmHandler<Position>{
  
    private static PositionServices instance = null;
  
    private PositionServices() {
      super(Position.class);
    }
  
    public static PositionServices getInstance() {
      
      if (instance == null) {
        instance = new PositionServices();
      }
      return instance;
    }
  
}