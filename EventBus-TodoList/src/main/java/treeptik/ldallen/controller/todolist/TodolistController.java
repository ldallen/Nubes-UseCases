package treeptik.ldallen.controller.todolist;

import com.github.aesteve.vertx.nubes.annotations.Controller;
import com.github.aesteve.vertx.nubes.annotations.File;
import com.github.aesteve.vertx.nubes.annotations.View;
import com.github.aesteve.vertx.nubes.annotations.routing.http.GET;
import com.github.aesteve.vertx.nubes.context.FileResolver;
import io.vertx.ext.web.RoutingContext;

@Controller("/todolist")
public class TodolistController {

    @GET
    @File("web/assets/index.html")
    public void rootView(RoutingContext context) {
        context.next();
    }

    @GET("/list")
    @File("web/assets/todolist.html")
    public void listView(RoutingContext context) {
        context.next();
    }

    @GET("/vertxbus.js")
    @File("web/vertxbus.js")
    public void getVertBusJS(RoutingContext context) {
        context.next();
    }

    @GET("/todolist.js")
    @File("web/todolist.js")
    public void getTodolistJS(RoutingContext context) {
        context.next();
    }

}