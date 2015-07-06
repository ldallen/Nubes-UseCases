package treeptik.ldallen.controllers.view;

import com.github.aesteve.vertx.nubes.annotations.Controller;
import com.github.aesteve.vertx.nubes.annotations.File;
import com.github.aesteve.vertx.nubes.annotations.routing.http.GET;
import com.github.aesteve.vertx.nubes.annotations.services.Service;
import io.vertx.ext.web.RoutingContext;
import treeptik.ldallen.services.ChatService;

@Controller("/")
public class ChatController {

    @Service("chatService")
    private ChatService chat;

    @GET
    @File("web/assets/index.html")
    public void chatView(RoutingContext context) {
        context.next();
    }

    @GET("vertxbus.js")
    @File("web/vertxbus.js")
    public void vertxbusLoad(RoutingContext context) {
        context.next();
    }

}