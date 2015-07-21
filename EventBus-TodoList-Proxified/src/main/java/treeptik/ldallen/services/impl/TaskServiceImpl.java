package treeptik.ldallen.services.impl;

import com.github.aesteve.vertx.nubes.annotations.services.Proxify;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import treeptik.ldallen.domains.Task;
import treeptik.ldallen.services.TaskService;

import java.util.ArrayList;
import java.util.List;

@Proxify("service.tasks")
public class TaskServiceImpl implements TaskService {

    private List<Task> tasks; // database

    public TaskServiceImpl(){
        tasks = new ArrayList<Task>();
    }

    @Override
    public void add(JsonObject task, Handler<AsyncResult<String>> handler) {
        Task t = new Task(task.getString("name"),task.getString("description"));
        if(tasks != null)
            tasks.add(t);
        else{
            tasks = new ArrayList<Task>();
            tasks.add(t);
        }
        handler.handle(Future.succeededFuture(getTasksAsJson().toString()));
    }

    @Override
    public void del(String name,  Handler<AsyncResult<String>> handler) {
        for (Task t : tasks) {
            if (t.getName().equals(name)) {
                tasks.remove(t);
                break;
            }
        }
        handler.handle(Future.succeededFuture(getTasksAsJson().toString()));
    }

    @Override
    public void getTask(int id,  Handler<AsyncResult<String>> handler) {
        JsonObject task = new JsonObject();
        if(id<tasks.size()) {
            Task t = tasks.get(id);
            task.put(t.getName(), t.getDescription());
            handler.handle(Future.succeededFuture(task.toString()));
        }
        else{
            handler.handle(Future.succeededFuture("task with id: " + id + " not found"));
        }
    }

    @Override
    public void getTasks(Handler<AsyncResult<String>> handler) {
        handler.handle(Future.succeededFuture(getTasksAsJson().toString()));
    }

    @Override
    public void register(Handler<AsyncResult<String>> handler){
        handler.handle(Future.succeededFuture(getTasksAsJson().toString()));
    }

    private JsonObject getTasksAsJson(){
        JsonObject json = new JsonObject();
        for (Task t : tasks){
            json.put(t.getName(),t.getDescription());
        }
        return json;
    }
}
