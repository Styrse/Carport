package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.controller.RoutingController;
import app.entities.products.carport.Carport;
import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;
import app.entities.products.materials.roof.RoofCover;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;

import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=public";
    private static final String DB = "carport";

    public static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args) {
        // Initializing Javalin and Jetty webserver

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        RoutingController.routes(app);

        Carport carport = new Carport(0, 0, "Carport", "Open", 10000, 16700, 600, 1150, 220, "flat",
                new Post(0, 0, "Post", "Wood", 12, 45, 400, 10, 600, "m", 10, true, 5),
                new Beam(0,0, "Beam", "Wood", 13, 23, 600, 20, 600, "m", 20, 340),
                new Rafter(0,0,"Rafter", "Wood", 23, 45, 600, 30, 600, "m", 30),
                new Fascia(0, 0, "Fascia", "Wood", 34, 50, 600, 30, 600, "m", 23),
                new RoofCover(0,0,"Rood", "Plastic", 45, 98, 600, 100, 600, "m", 20, 10, 55)
                );


    }
}