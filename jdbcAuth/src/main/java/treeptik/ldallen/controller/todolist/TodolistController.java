package treeptik.ldallen.controller.todolist;

import com.github.aesteve.vertx.nubes.annotations.Controller;
import com.github.aesteve.vertx.nubes.annotations.File;
import com.github.aesteve.vertx.nubes.annotations.auth.Auth;
import com.github.aesteve.vertx.nubes.annotations.routing.http.GET;
import com.github.aesteve.vertx.nubes.auth.AuthMethod;
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
	public String redirectMethod(){
		return "web/assets/todolist.html";
	}

	@GET("/loginpage")
	@File
	public String loginView() {
		return "web/assets/loginpage.html";
	}

	@GET("/list/logout")
	public void logoutMethod(RoutingContext context){
		context.clearUser();
		context.response().putHeader("location", "/todolist").setStatusCode(302).end();
	}
}