package app.controller;

import app.entities.products.carport.Carport;
import app.entities.users.Customer;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class RoutingController {
    public static void routes(Javalin app) {
        app.get("/", ctx -> ctx.render("index.html"));}
}
