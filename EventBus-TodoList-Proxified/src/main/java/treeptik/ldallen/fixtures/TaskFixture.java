package treeptik.ldallen.fixtures;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import treeptik.ldallen.domains.Task;

import com.github.aesteve.vertx.nubes.annotations.services.Service;
import com.github.aesteve.vertx.nubes.fixtures.Fixture;
import treeptik.ldallen.services.TaskService;

public class TaskFixture extends Fixture {

	@Service("proxy__TaskServiceImpl")
	private TaskService tasks;

	@Override
	public int executionOrder() {
		return 1;
	}

	@Override
	public void startUp(Vertx vertx, Future<Void> future) {
		JsonObject task1 = new JsonObject().put("name","Init task").put("description", "Add other tasks");
		JsonObject task2 = new JsonObject().put("name","Test task").put("description", "Hi! I'm a task description");
		tasks.add(task1, res->{
			tasks.add(task2, res2->{
			});
		});
		future.complete();
	}

	@Override
	public void tearDown(Vertx vertx, Future<Void> future) {
		future.complete();
	}

}
