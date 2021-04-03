
package edu.pucmm.eict.controllers;

import edu.pucmm.eict.models.Form;
import edu.pucmm.eict.models.Position;
import edu.pucmm.eict.services.FormServices;
import edu.pucmm.eict.services.PositionServices;

import edu.pucmm.eict.models.User;
import edu.pucmm.eict.services.UserServices;
import edu.pucmm.eict.utils.BaseController;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

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
          //Looking for user
          String username = ctx.sessionAttribute("logged");
          User user = UserServices.getInstance().find(username);

          //creating model
          Map<String, Object> modelo = new HashMap<>();
          modelo.put("user", user);

          ctx.render("/templates/inApp/main-form.html",modelo);
        });

        post("/add-form", ctx -> {
          String name = ctx.formParam("name");
          String lastName = ctx.formParam("lastname");
          String area = ctx.formParam("sector");
          String nivelEscolar = ctx.formParam("schoolLevel");
          double latitude = ctx.formParam("latitude",Double.class).get();
          double longitude = ctx.formParam("longitude",Double.class).get();

          //Looking for user
          String username = ctx.sessionAttribute("logged");
          User user = UserServices.getInstance().find(username);



          Position position = new Position(latitude,longitude);
          PositionServices.getInstance().create(position);
          Form form = new Form(name,lastName,area,nivelEscolar,user,position);
          FormServices.getInstance().create(form);
          ctx.redirect("/inapp");

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

      });
    });
  }

}