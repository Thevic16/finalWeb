
package edu.pucmm.eict.controllers;

import edu.pucmm.eict.utils.BaseController;
import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

public class MainController extends BaseController{

  public MainController(Javalin app) {
    super(app);
  }

  public void applyRoutes() {
    app.get("/", ctx -> {
      ctx.render("/templates/home/index.html");
    });

    app.get("login", ctx -> {
      ctx.render("/templates/home/login.html");
    });

    app.routes(() -> {
      path("/inapp", () -> {
        before(ctx -> {
          String logged = ctx.sessionAttribute("logged");
          if (logged == null) ctx.redirect("/login");
        });

        get("/", ctx -> {
          ctx.render("/templates/inApp/main-form.html");
        });

      });
    });
  }


  
}