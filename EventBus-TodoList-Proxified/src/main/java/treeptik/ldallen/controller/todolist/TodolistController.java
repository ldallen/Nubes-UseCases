package treeptik.ldallen.controller.todolist;

import com.github.aesteve.vertx.nubes.annotations.Controller;
import com.github.aesteve.vertx.nubes.annotations.File;
import com.github.aesteve.vertx.nubes.annotations.params.Param;
import com.github.aesteve.vertx.nubes.annotations.routing.http.GET;
import com.github.aesteve.vertx.nubes.annotations.services.Service;
import io.vertx.ext.web.RoutingContext;
import treeptik.ldallen.services.TaskService;

@Controller("/todolist")
public class TodolistController {

	@Service("proxy__TaskServiceImpl")
		private TaskService tasks;

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

	@GET("/list/:id")
	public void testParam(RoutingContext context, @Param("id") Integer id) {
		tasks.getTask(id, res ->{
			context.response().end(res.result().toString());
		});
	}
}