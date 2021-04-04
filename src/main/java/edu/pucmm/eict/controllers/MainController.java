
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
          //Looking for user
          String username = ctx.sessionAttribute("logged");
          User user = UserServices.getInstance().find(username);

          //creating model
          Map<String, Object> model = new HashMap<>();
          model.put("user", user);
          model.put("button","Enviar");
          model.put("action","/inapp/add-form");
          model.put("edit", false);

          ctx.render("/templates/inApp/main-form.html",model);
        });

        get("/edit-form/:idForm", ctx -> {
          int idForm = ctx.pathParam("idForm",Integer.class).get();
          Form form = FormServices.getInstance().find(idForm);

          //Looking for user
          String username = ctx.sessionAttribute("logged");
          User user = UserServices.getInstance().find(username);

          //creating model
          Map<String, Object> model = new HashMap<>();
          model.put("user", user);
          model.put("button","Editar");
          model.put("action","/inapp/edit-form");
          model.put("edit", true);
          model.put("form", form);

          ctx.render("/templates/inApp/main-form.html",model);
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

        post("/edit-form", ctx -> {
          int id = ctx.formParam("IdForm",Integer.class).get();
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
          //Form form = new Form(name,lastName,area,nivelEscolar,user,position);
          Form form = FormServices.getInstance().find(id);
          form.setName(name);
          form.setLastName(lastName);
          form.setArea(area);
          form.setNivelEscolar(nivelEscolar);
          form.setPosition(position);
          form.setUser(user);

          FormServices.getInstance().update(form);
          ctx.redirect("/inapp");

        });

        get("/delete-form/:idForm", ctx -> {
          int idForm = ctx.pathParam("idForm",Integer.class).get();
          boolean form = FormServices.getInstance().delete(idForm);
          ctx.redirect("/inapp/list-form");
        });

        get("/list-form", ctx -> {
          List<Form> forms = FormServices.getInstance().findAll();
          Map<String, Object> model = new HashMap<>();
          model.put("forms",forms);

          ctx.render("/templates/inApp/list-form.html",model);
        });

        get("/user-manage", ctx -> {
          Map<String, Object> model = new HashMap<>();
          model.put("edit", false);
          model.put("mode", "create");
          ctx.render("/templates/inApp/user-manage.html", model);
        });

        get("/list-user", ctx -> {

          List<User> users = UserServices.getInstance().findAll();
          Map<String, Object> model = new HashMap<>();
          model.put("users", users);
          ctx.render("/templates/inApp/list-user.html", model);
        });

        post("/user-manage/:mode", ctx -> {
          String name = ctx.formParam("firstName");
          String lastName = ctx.formParam("lastName");
          String username = ctx.formParam("username");
          String password = ctx.formParam("password");
          String role = ctx.formParam("role");
          String mode = ctx.pathParam("mode");
          System.out.println("Username: "+username);
          System.out.println("Name: "+name);

          if (mode.equals("create")) {

            User user = new User(name, lastName, username, password, role);
            UserServices.getInstance().create(user);
          } else if (mode.equals("edit")) {
            User user = UserServices.getInstance().find(username);
            user.setName(name);
            user.setLastName(lastName);
            user.password(password);
            user.role(role);
            UserServices.getInstance().update(user);
          }
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
          model.put("edit", true);
          model.put("mode", "edit");
          ctx.render("/templates/inApp/user-manage.html", model);
        });
      });
    });
  }

}