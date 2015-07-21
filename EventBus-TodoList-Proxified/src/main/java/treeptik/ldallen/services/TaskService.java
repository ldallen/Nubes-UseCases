package treeptik.ldallen.services;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

@ProxyGen
public interface TaskService {

	public void add(JsonObject task, Handler<AsyncResult<String>> handler);

	public void del(String name, Handler<AsyncResult<String>> handler);

	public void getTask(int id, Handler<AsyncResult<String>> handler);

	public void getTasks(Handler<AsyncResult<String>> handler);

	public void register(Handler<AsyncResult<String>> handler);
}
