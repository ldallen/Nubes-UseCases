package treeptik.ldallen.controller.todolist;

import com.github.aesteve.vertx.nubes.annotations.Controller;
import com.github.aesteve.vertx.nubes.annotations.File;
import com.github.aesteve.vertx.nubes.annotations.auth.Auth;
import com.github.aesteve.vertx.nubes.annotations.cookies.Cookies;
import com.github.aesteve.vertx.nubes.annotations.params.RequestBody;
import com.github.aesteve.vertx.nubes.annotations.routing.http.GET;
import com.github.aesteve.vertx.nubes.annotations.routing.http.POST;
import com.github.aesteve.vertx.nubes.auth.AuthMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

@Controller("/todolist")
public class TodolistController {

	@GET
	@File
	public String rootView() {
		return "web/assets/index.html";
	}


	@GET("/list")
	@Auth(method = AuthMethod.REDIRECT, authority = "role:standard", redirectURL = "/todolist/loginpage")
	@File
	public String listView() {
		return "web/assets/todolist.html";
	}

	@GET("/loginpage")
	@File
	public String loginView() {
		return "web/assets/loginpage.html";
	}

	@POST("/loginhandler")
	@Auth(method = AuthMethod.FORM, authority = "")
	@File
	public String listView2() {
		return "web/assets/todolist.html"; // I didn't know what to do here..
	}
}