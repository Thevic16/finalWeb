package edu.pucmm.eict;

import java.sql.SQLException;

import edu.pucmm.eict.controllers.ApiRestController;
import edu.pucmm.eict.controllers.MainController;
import edu.pucmm.eict.controllers.SoapController;
import edu.pucmm.eict.models.User;
import edu.pucmm.eict.services.DatabaseSetupServices;
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
            config.enableCorsForAllOrigins();

        });

        DatabaseSetupServices.startDb();
        new SoapController(app).applyRoutes();
        
        app.start(7000);
        //Filtro para enviar el header de validación
        app.after(ctx -> {
            //System.out.println("Enviando el header de seguridad para el Service Worker");
            ctx.header("Service-Worker-Allowed", "/");
        });
        
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
        //PositionServices.getInstance().create(new Position(34.44, 56.55));
        
        if (UserServices.getInstance().find("admin") == null) {
            User user = new User("admin", "admin", "admin", "admin");
            user.setRolPollster(true);
            user.setRolAdmin(true);
            UserServices.getInstance().create(user);
        }
        
        new MainController(app).applyRoutes();
        new ApiRestController(app).applyRoutes();
    }
}
