package treeptik.ldallen.services;

import com.github.aesteve.vertx.nubes.annotations.services.Consumer;
import com.github.aesteve.vertx.nubes.services.Service;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import treeptik.ldallen.domains.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskService implements Service  {

    private List<Task> tasks; // database
    private Vertx vertx;


    @Override
    public void init(Vertx vertx) {
        this.vertx = vertx;
    }
    @Override
    public void start(Future<Void> future) {
        tasks = new ArrayList<Task>();
        future.complete();
    }

    @Override
    public void stop(Future<Void> future) {
        tasks.clear();
        future.complete();
    }

    public void add(Task task) {
        for(Task t : tasks){ //check if task already exists, wouldn't be necessary with an insert in db
            if (t.getName().equals(task.getName()) && t.getDescription().equals(task.getDescription())){
                return;
            }
        }
            tasks.add(task); // do an insert request to db
    }

    public void del(String name){
        for(Task t : tasks){
            if(t.getName().equals(name)) {
                tasks.remove(t);
                return;
            }
        }
    }

    public Task getTask(int i) {
        return tasks.get(i); // select request to db
    }

    public List<Task> getTasks() {
        return tasks; // select request to db
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public int size() {
        return tasks.size();
    }

    public void clear() {
        tasks.clear();
    }

    public void sendTaskList() {
        JsonObject jsonMsg = new JsonObject();
        String name = "";
        String description = "";
        for (Task t : tasks){
            name = t.getName();
            description = t.getDescription();
            jsonMsg.put(name,description);
        }

        vertx.eventBus().publish("taskList.update", jsonMsg.toString());
    }

    @Consumer("task.register")
    public void registerTaskHandler(Message<String> message){
        sendTaskList();
    }

    @Consumer("task.add")
    public void addTaskHandler(Message<JsonObject> message) {
        JsonObject msg = message.body();
        add(new Task(msg.getString("taskname"),msg.getString("taskdescription")));
        sendTaskList();

    }

    @Consumer("task.del")
    public void delTaskHandler(Message<JsonObject> message) {
        JsonObject msg = message.body();
        del(msg.getString("taskname"));
        sendTaskList();
    }
}
