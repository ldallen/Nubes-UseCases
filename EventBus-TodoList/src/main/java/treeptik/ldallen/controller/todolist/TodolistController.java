package treeptik.ldallen.controller.todolist;

import com.github.aesteve.vertx.nubes.annotations.Controller;
import com.github.aesteve.vertx.nubes.annotations.File;
import com.github.aesteve.vertx.nubes.annotations.routing.http.GET;

@Controller("/todolist")
public class TodolistController {

	@GET
	@File
	public String rootView() {
		return "web/assets/index.html";
	}

	@GET("/list")
	@File
	public String listView() {
		return "web/assets/todolist.html";
	}
}