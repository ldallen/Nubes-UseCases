package treeptik.ldallen.fixtures;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import treeptik.ldallen.domains.Task;

import com.github.aesteve.vertx.nubes.annotations.services.Service;
import com.github.aesteve.vertx.nubes.fixtures.Fixture;
import treeptik.ldallen.services.TaskService;

public class TaskFixture extends Fixture {

	@Service("taskService")
	private TaskService tasks;

	@Override
	public int executionOrder() {
		return 1;
	}

	@Override
	public void startUp(Vertx vertx, Future<Void> future) {
		Task task1 = new Task("Init task", "Add other tasks");
		Task task2 = new Task("Test task", "Hi! I'm a task description");
		tasks.add(task1);
		tasks.add(task2);
		future.complete();
	}

	@Override
	public void tearDown(Vertx vertx, Future<Void> future) {
		tasks.clear();
		future.complete();
	}

}
