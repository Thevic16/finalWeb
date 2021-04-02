package edu.pucmm.eict;

import edu.pucmm.eict.controllers.MainController;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hola Mundo");

        //Creando la instancia del servidor.
        Javalin app = Javalin.create(config ->{
            config.addStaticFiles("/publico"); //desde la carpeta de resources
        }).start(7000);

        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");

        new MainController(app).applyRoutes();
    }
}
