package treeptik.ldallen.controllers.view;

import com.github.aesteve.vertx.nubes.annotations.Controller;
import com.github.aesteve.vertx.nubes.annotations.File;
import com.github.aesteve.vertx.nubes.annotations.routing.http.GET;

@Controller("/")
public class ChatController {

	@GET
	@File
	public String chatView() {
		return "web/assets/index.html";
	}
}