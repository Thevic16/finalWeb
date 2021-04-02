
package edu.pucmm.eict.controllers;

import edu.pucmm.eict.utils.BaseController;
import io.javalin.Javalin;

public class MainController extends BaseController{

  public MainController(Javalin app) {
    super(app);
  }

@Override
public void applyRoutes() {
  app.get("/", ctx -> {
    ctx.render("/templates/home/index.html");
  });

  app.get("/inapp", ctx -> {
    ctx.render("/templates/inApp/main-form.html");
  });
}

  
}