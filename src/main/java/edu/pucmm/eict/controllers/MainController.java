
package edu.pucmm.eict.controllers;

import edu.pucmm.eict.models.Form;
import edu.pucmm.eict.models.Position;
import edu.pucmm.eict.services.FormServices;
import edu.pucmm.eict.services.PositionServices;

import edu.pucmm.eict.models.User;
import edu.pucmm.eict.services.UserServices;
import edu.pucmm.eict.utils.BaseController;
import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        get("/add-form", ctx -> {
          String name = ctx.formParam("name");
          String lastName = ctx.formParam("lasname");
          String area = ctx.formParam("sector");
          String nivelEscolar = ctx.formParam("sector");
          double latitude = ctx.formParam("latitude",Double.class).get();
          double longitude = ctx.formParam("longitude",Double.class).get();

          PositionServices.getInstance().create(new Position(latitude,longitude));
          //FormServices.getInstance().create(new Form(name,lastName,area,nivelEscolar,new Position(latitude,longitude), new ));
          ctx.redirect("/");

        });

        get("/list-form", ctx -> {
          ctx.render("/templates/inApp/list-form.html");
        });

        get("/user-manage", ctx -> {
          ctx.render("/templates/inApp/user-manage.html");
        });

        get("/list-user", ctx -> {

          List<User> users = UserServices.getInstance().findAll();
          Map<String, Object> model = new HashMap<>();
          model.put("users", users);
          ctx.render("/templates/inApp/list-user.html", model);
        });

        post("/user-manage", ctx -> {
          String name = ctx.formParam("firstName");
          String lastName = ctx.formParam("lastName");
          String username = ctx.formParam("username");
          String password = ctx.formParam("password");
          String role = ctx.formParam("role");

          User user = new User(name, lastName, username, password, role);
          UserServices.getInstance().create(user);
          ctx.redirect("/inapp/user-manage");
        });

        get("/delete-user/:username", ctx -> {
          String username = ctx.pathParam("username");
          boolean user = UserServices.getInstance().delete(username);
          ctx.redirect("/inapp/user-manage");
        });

        get("/edit-user/:username", ctx -> {
          String username = ctx.pathParam("username");
          User user = UserServices.getInstance().find(username);
          Map<String, Object> model = new HashMap<>();
          model.put("user", user);
          model.put("editing", true);
        });
      });
    });
  }

}