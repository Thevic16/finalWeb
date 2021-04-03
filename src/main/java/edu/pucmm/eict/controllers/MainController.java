
package edu.pucmm.eict.controllers;

import edu.pucmm.eict.models.User;
import edu.pucmm.eict.services.UserServices;
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

    app.get("/login", ctx -> {
      ctx.render("/templates/home/login.html");
    });

    app.post("/validate-login", ctx -> {
      String username = ctx.formParam("username");
      User user = UserServices.getInstance().find(username);
      
      if (user.getPassword().equalsIgnoreCase(ctx.formParam("password"))) {
        ctx.redirect("/inapp");
        ctx.sessionAttribute("logged", username);
        ctx.cookie("rememberMe", username);
      } else {
        ctx.redirect("/login");
      }
      
    });
    app.routes(() -> {
      path("/inapp", () -> {
        before(ctx -> {
          String logged = ctx.sessionAttribute("logged");
          if (logged == null || ctx.cookie("rememberMe") == null) ctx.redirect("/login");
        });

        get("/", ctx -> {
          ctx.render("/templates/inApp/main-form.html");
        });

        get("/list-form", ctx -> {
          ctx.render("/templates/inApp/list-form.html");
        });

        get("/user-manage", ctx -> {
          ctx.render("/templates/inApp/user-manage.html");
        });

        get("/list-user", ctx -> {
          ctx.render("/templates/inApp/list-user.html");
        });

        post("/user-manage")

      });
    });
  }


  
}