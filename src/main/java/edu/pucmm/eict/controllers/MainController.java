
package edu.pucmm.eict.controllers;

import edu.pucmm.eict.models.Form;
import edu.pucmm.eict.models.FormJson;
import edu.pucmm.eict.models.MyRole;
import edu.pucmm.eict.models.Position;
import edu.pucmm.eict.services.FormServices;
import edu.pucmm.eict.services.PositionServices;

import edu.pucmm.eict.models.User;
import edu.pucmm.eict.services.UserServices;
import edu.pucmm.eict.utils.BaseController;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.websocket.WsErrorContext;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.core.security.SecurityUtil.roles;

import java.util.List;



public class MainController extends BaseController{

  public MainController(Javalin app) {
    super(app);
  }

  public void applyRoutes() {
    // Set the access-manager that Javalin should use
    app.config.accessManager((handler, ctx, permittedRoles) -> {
      HashMap<String, MyRole> userRole = getUserRoles(ctx);
      if (permittedRoles.contains(userRole.get("DEFAULT")) || permittedRoles.contains(userRole.get("ADMIN")) || permittedRoles.contains(userRole.get("POLLSTER")) ) {
        handler.handle(ctx);
      } else {
        ctx.render("/publico/templates/inApp/unauthorized.html");
      }
    });

    app.get("/", ctx -> {
      ctx.render("/publico/templates/home/index.html");

    },roles(MyRole.DEFAULT));

    app.get("/login", ctx -> {
      ctx.render("/publico/templates/home/login.html");
    },roles(MyRole.DEFAULT));

    app.post("/validate-login", ctx -> {
      String username = ctx.formParam("username");
      User user = UserServices.getInstance().find(username);

      if(user != null){
        if (user.getPassword().equalsIgnoreCase(ctx.formParam("password"))) {
          ctx.redirect("/inapp");
          ctx.sessionAttribute("logged", username);
          ctx.cookie("rememberMe", username);
        } else {
          ctx.redirect("/login");
        }
      }
      else {
        ctx.redirect("/login");
      }

    },roles(MyRole.DEFAULT));

    app.before("/inapp/", ctx -> {
      String logged = ctx.sessionAttribute("logged");
      String rememberMe = ctx.cookie("rememberMe");
      System.out.println("rememberMe "+rememberMe);
      if (logged == null) {
        
        if (rememberMe == null) {
          ctx.redirect("/login");
        }
        else  {
          ctx.sessionAttribute("logged", rememberMe);
        }
      }
    });

    app.after("/inApp/offline.appcache", ctx -> {
      ctx.contentType("text/cache-manifest");
    });
    
    app.routes(() -> {
      path("/inapp", () -> {
        before(ctx -> {
          String logged = ctx.sessionAttribute("logged");
          String rememberMe = ctx.cookie("rememberMe");
          System.out.println("rememberMe "+rememberMe);
          if (logged == null) {
        
            if (rememberMe == null) {
              ctx.redirect("/login");
            }
            else  {
              ctx.sessionAttribute("logged", rememberMe);
            }
          }
        });

        get("/", ctx -> {
          //Looking for user
          String username = ctx.sessionAttribute("logged");
          User user = UserServices.getInstance().find(username);

          //creating model
          Map<String, Object> model = new HashMap<>();
          model.put("user", user);
          model.put("action","/inapp/add-form");
          model.put("edit", false);

          ctx.render("/publico/templates/inApp/main-form.html",model);
        },roles(MyRole.ADMIN,MyRole.POLLSTER));

        get("/edit-form/:idform", ctx -> {
          int idform = ctx.pathParam("idform", Integer.class).get();
          Map<String, Object> model = new HashMap<>();
          model.put("edit", true);
          model.put("formId", idform);
          ctx.render("/publico/templates/inApp/main-form.html",model);
        }, roles(MyRole.ADMIN,MyRole.POLLSTER));

        // get("/edit-form/:idForm", ctx -> {
        //   int idForm = ctx.pathParam("idForm",Integer.class).get();
        //   Form form = FormServices.getInstance().find(idForm);

        //   //Looking for user
        //   String username = ctx.sessionAttribute("logged");
        //   User user = UserServices.getInstance().find(username);

        //   //creating model
        //   Map<String, Object> model = new HashMap<>();
        //   model.put("user", user);
        //   model.put("action","/inapp/edit-form");
        //   model.put("edit", true);
        //   model.put("form", form);

        //   ctx.render("/templates/inApp/main-form.html",model);
        // },roles(MyRole.ADMIN,MyRole.POLLSTER));

        post("/add-form", ctx -> {
          String name = ctx.formParam("name");
          String lastName = ctx.formParam("lastname");
          String area = ctx.formParam("sector");
          String nivelEscolar = ctx.formParam("schoolLevel");
          double latitude = ctx.formParam("latitude",Double.class).get();
          double longitude = ctx.formParam("longitude",Double.class).get();
          String photoBase64 = ctx.formParam("photob64");
          //Looking for user
          String username = ctx.sessionAttribute("logged");
          User user = UserServices.getInstance().find(username);



          Position position = new Position(latitude,longitude);
          PositionServices.getInstance().create(position);
          Form form = new Form(name,lastName,area,nivelEscolar,user,position, photoBase64);
          FormServices.getInstance().create(form);
          ctx.redirect("/inapp");

        },roles(MyRole.ADMIN,MyRole.POLLSTER));

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

        },roles(MyRole.ADMIN,MyRole.POLLSTER));

        get("/delete-form/:idForm", ctx -> {
          int idForm = ctx.pathParam("idForm",Integer.class).get();
          boolean form = FormServices.getInstance().delete(idForm);
          ctx.redirect("/inapp/list-form");
        },roles(MyRole.ADMIN,MyRole.POLLSTER));


        get("/list-form", ctx -> {
          List<Form> forms = FormServices.getInstance().findAll();
          Map<String, Object> model = new HashMap<>();
          model.put("forms",forms);

          ctx.render("/publico/templates/inApp/list-form.html",model);
        },roles(MyRole.ADMIN,MyRole.POLLSTER));

        get("/user-manage", ctx -> {
          Map<String, Object> model = new HashMap<>();
          model.put("edit", false);
          model.put("mode", "create");
          ctx.render("/publico/templates/inApp/user-manage.html", model);
        },roles(MyRole.ADMIN));

        get("/list-user", ctx -> {

          List<User> users = UserServices.getInstance().findAll();
          Map<String, Object> model = new HashMap<>();
          model.put("users", users);
          ctx.render("/publico/templates/inApp/list-user.html", model);
        },roles(MyRole.ADMIN));

        post("/user-manage/:mode", ctx -> {
          String name = ctx.formParam("firstName");
          String lastName = ctx.formParam("lastName");
          String username = ctx.formParam("username");
          String password = ctx.formParam("password");
          String roleAdmin = ctx.formParam("roleAdmin");
          String rolePollster = ctx.formParam("rolePollster");

          String mode = ctx.pathParam("mode");
          System.out.println("Username: "+username);
          System.out.println("Name: "+name);

          if (mode.equals("create")) {

            User user = new User(name, lastName, username, password);
            if(roleAdmin != null){
              user.setRolAdmin(true);
            }
            if(rolePollster != null){
              user.setRolPollster(true);
            }
            UserServices.getInstance().create(user);
          } else if (mode.equals("edit")) {
            User user = UserServices.getInstance().find(username);
            user.setName(name);
            user.setLastName(lastName);
            user.password(password);
            user.setRolAdmin(false); //reset rol
            user.setRolPollster(false);//reset rol
            //reassign rol
            if(roleAdmin != null){
              user.setRolAdmin(true);
            }
            if(rolePollster != null){
              user.setRolPollster(true);
            }
            UserServices.getInstance().update(user);
          }
          ctx.redirect("/inapp/user-manage");
        },roles(MyRole.ADMIN));

        get("/delete-user/:username", ctx -> {
          String username = ctx.pathParam("username");
          boolean user = UserServices.getInstance().delete(username);
          ctx.redirect("/inapp/user-manage");
        },roles(MyRole.ADMIN));

        get("/edit-user/:username", ctx -> {
          String username = ctx.pathParam("username");
          User user = UserServices.getInstance().find(username);
          Map<String, Object> model = new HashMap<>();
          model.put("user", user);
          model.put("edit", true);
          model.put("mode", "edit");
          ctx.render("/publico/templates/inApp/user-manage.html", model);
        },roles(MyRole.ADMIN));

        get("/logout", ctx -> {
          ctx.removeCookie("rememberMe", "/");
          ctx.sessionAttribute("logged", null);
          ctx.redirect("/");
        },roles(MyRole.ADMIN,MyRole.POLLSTER));

        get("/hypo", ctx -> {
          ctx.render("/publico/templates/inApp/main-form-offline.html");
        }, roles(MyRole.ADMIN, MyRole.POLLSTER));


        ws("/push-forms", ws -> {
          ws.onConnect(ctx -> {
            System.out.println("Session: "+ctx.getSessionId());
          });

          
          ws.onMessage(ctx -> {

            Gson json = new Gson();
            System.out.println("Message: "+ctx.message());
            System.out.println("Tipo: "+ctx.message().getClass());


            FormJson[] forms = json.fromJson(ctx.message(), FormJson[].class);
            for (FormJson form : forms) {
              Position position = null;
              if (form.getLatitude() != null && form.getLongitude() != null) {
                position = new Position(Double.parseDouble(form.getLatitude()), Double.parseDouble(form.getLongitude()));
              } else {
                position = new Position(0,0);
              }
              PositionServices.getInstance().create(position);

              User user = UserServices.getInstance().find(form.getUser());

              Form formToSet = new Form(form.getName(), form.getLastName(), form.getArea(), form.getSchoolLevel(), user, position, form.getPhoto());

              FormServices.getInstance().create(formToSet);
            }
            System.out.println(FormServices.getInstance().findAll().get(0).getPhoto());
            String formsJson = new Gson().toJson(FormServices.getInstance().findAll());
            ctx.send(formsJson);
          });
          ws.onClose(ctx -> {
            System.out.println("Closing: "+ctx.getSessionId());
          });
          ws.onError(ctx -> {
            System.out.println("Error: "+ctx.error());
          });
        }, roles(MyRole.ADMIN,MyRole.POLLSTER));
          
      });
    });
  }

  static HashMap<String, MyRole> getUserRoles(Context ctx) {


    /*
    if(username != null){
      user = UserServices.getInstance().find(username);
    }

     */
    HashMap<String, MyRole> myRoles = new HashMap<String, MyRole>();
    //Looking for user
    String username = ctx.sessionAttribute("logged");
    User user = null;

    if(username != null){
      user = UserServices.getInstance().find(username);
    }

    myRoles.put("DEFAULT",MyRole.DEFAULT); // Always

    if(user != null){
      if(user.getRolAdmin()){
        myRoles.put("ADMIN",MyRole.ADMIN);
      }
      if(user.getRolPollster()){
        myRoles.put("POLLSTER",MyRole.POLLSTER);
      }
    }

    return myRoles;
  }

}