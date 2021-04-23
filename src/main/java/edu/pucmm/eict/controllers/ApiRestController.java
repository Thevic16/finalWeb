package edu.pucmm.eict.controllers;

import edu.pucmm.eict.models.Form;
import edu.pucmm.eict.models.FormApi;
import edu.pucmm.eict.models.MyRole;
import edu.pucmm.eict.models.User;
import edu.pucmm.eict.services.FormServices;
import edu.pucmm.eict.services.UserServices;
import edu.pucmm.eict.utils.BaseController;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.util.ArrayList;
import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.core.security.SecurityUtil.roles;

public class ApiRestController extends BaseController {

    public ApiRestController(Javalin app) {
        super(app);
    }

    @Override
    public void applyRoutes() {
        app.routes(() ->{
            path("/api",() ->{

                path("/v1",() ->{

                    path("/form",() ->{

                        after(ctx -> {
                            ctx.header("Content-Type", "application/json");
                        });

                        get("/:userName", ctx -> {
                            String userName = ctx.pathParam("userName");
                            List<FormApi> filteredForms = FormApi.getFilteredForms(userName);

                            ctx.json(filteredForms);
                        },roles(MyRole.DEFAULT));

                        post("/", ctx -> {
                            // parsing the POJO information must come in json format.
                            FormApi tmp = ctx.bodyAsClass(FormApi.class);
                            //create.
                            ctx.json(tmp);
                        },roles(MyRole.DEFAULT));

                    });

                    path("/user",() ->{

                        //verify if user correct
                        get("/:userName/:password", ctx -> {
                            String userName = ctx.pathParam("userName");
                            String password = ctx.pathParam("password");
                            Boolean isCorrect = verifyUser(userName,password);

                            if(isCorrect){
                                ctx.sessionAttribute("userApi",userName);
                            }

                            ctx.json(isCorrect);
                        },roles(MyRole.DEFAULT));

                        get("/", ctx -> {
                            String userApi = ctx.sessionAttribute("userApi");

                            if(userApi != null){
                                ctx.json(userApi);
                            }
                            else {
                                throw new NotFoundResponse("User no found!");
                            }

                        },roles(MyRole.DEFAULT));

                    });

                });

            });


        });

    }

    public Boolean verifyUser(String userName, String password){
        Boolean isCorrect = false;
        List<User> users = UserServices.getInstance().findAll();

        for (User user:users) {
            if(user.getUserName().equals(userName) && user.getPassword().equals(password)){
                isCorrect = true;
                return isCorrect;
            }
        }

        return isCorrect;
    }

}
