package edu.pucmm.eict;

import java.sql.SQLException;

import edu.pucmm.eict.controllers.MainController;
import edu.pucmm.eict.models.Position;
import edu.pucmm.eict.models.User;
import edu.pucmm.eict.services.DatabaseSetupServices;
import edu.pucmm.eict.services.PositionServices;
import edu.pucmm.eict.services.UserServices;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hola Mundo");

        //Creando la instancia del servidor.
        Javalin app = Javalin.create(config ->{
            config.addStaticFiles("/publico"); //desde la carpeta de resources
        }).start(7000);

        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
        DatabaseSetupServices.startDb();
        //PositionServices.getInstance().create(new Position(34.44, 56.55));

        if (UserServices.getInstance().find("admin") == null) {
            UserServices.getInstance().create(new User("admin", "admin", "admin", "admin", "admin"));
        }
        new MainController(app).applyRoutes();


    }
}
