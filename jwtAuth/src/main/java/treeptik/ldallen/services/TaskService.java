package treeptik.ldallen.services;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;

import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;
import treeptik.ldallen.domains.Task;

import com.github.aesteve.vertx.nubes.annotations.services.Consumer;
import com.github.aesteve.vertx.nubes.services.Service;

public class TaskService implements Service {

	private Vertx vertx;
	private JDBCClient dbClient;
	private JsonObject config;

	public TaskService(JsonObject config){
		this.config = config;
	}

	@Override
	public void init(Vertx vertx) {
		this.vertx = vertx;
		dbClient = JDBCClient.createShared(vertx,config,"todolist");
	}

	@Override
	public void start(Future<Void> future) {

		//Create the tables, and set the users and roles
		dbClient.getConnection(res -> {
					if (res.failed()) {
						throw new RuntimeException(res.cause());
					}

					final SQLConnection conn = res.result();

					conn.execute("CREATE TABLE IF NOT EXISTS task (name VARCHAR(255), description VARCHAR(255))", ddl -> {
						if (ddl.failed()) {
							throw new RuntimeException(ddl.cause());
						}
					});
					conn.close();
				});

		future.complete();
	}

	@Override
	public void stop(Future<Void> future) {
		dbClient.close();
		future.complete();
	}

	public void add(Task task) {

		dbClient.getConnection(res -> {
			final SQLConnection conn = res.result();
			String insert = "INSERT INTO task VALUES (?,?)";

			String taskName = task.getName();
			String taskDescription = task.getDescription();

			JsonArray params = new JsonArray()
					.add(taskName)
					.add(taskDescription);

			conn.updateWithParams(insert, params, r -> {
				if (r.succeeded()) {
					conn.query("SELECT * FROM task", res2 -> {
						if (res2.succeeded()) {

							ResultSet rs = res2.result();
							List<JsonObject> rsRows = rs.getRows();

							JsonObject dbValues = new JsonObject();

							for (JsonObject row : rsRows) {
								String name = row.getString("NAME");
								String description = row.getString("DESCRIPTION");
								dbValues.put(name, description);
							}
							vertx.eventBus().publish("taskList.update", dbValues.toString());

							conn.close(close -> {
							});
						}
					});
				}
			});
		});
	}

	public void del(String name) {
		dbClient.getConnection(res -> {
			final SQLConnection conn = res.result();
			String delete = "DELETE FROM task WHERE task.name=\'"+name+"\'";

			conn.update(delete, r -> {
				if (r.succeeded()) {
					conn.query("SELECT * FROM task", res2 -> {
						if (res2.succeeded()) {

							ResultSet rs = res2.result();
							List<JsonObject> rsRows = rs.getRows();

							JsonObject dbValues = new JsonObject();

							for (JsonObject row : rsRows) {
								String taskname = row.getString("NAME");
								String taskdescription = row.getString("DESCRIPTION");
								dbValues.put(taskname, taskdescription);
							}
							vertx.eventBus().publish("taskList.update", dbValues.toString());

							conn.close(close -> {
							});
						}

					});
				}
			});
		});
	}

	public void sendTaskList() {

		dbClient.getConnection(res -> {
			final SQLConnection conn = res.result();
			conn.query("SELECT * FROM task", res2 -> {
				if (res2.succeeded()) {

					ResultSet rs = res2.result();
					List<JsonObject> rsRows = rs.getRows();

					JsonObject dbValues = new JsonObject();

					for (JsonObject row : rsRows) {
						String name = row.getString("NAME");
						String description = row.getString("DESCRIPTION");
						dbValues.put(name, description);
					}
					vertx.eventBus().publish("taskList.update", dbValues.toString());
					conn.close(close -> {
					});
				}
			});

		});
	}


	@Consumer("task.register")
	public void registerTaskHandler(Message<String> message) {
		sendTaskList();
	}

	@Consumer("task.add")
	public void addTaskHandler(Message<JsonObject> message) {
		JsonObject msg = message.body();
		add(new Task(msg.getString("taskname"), msg.getString("taskdescription")));
	}

	@Consumer("task.del")
	public void delTaskHandler(Message<JsonObject> message) {
		JsonObject msg = message.body();
		del(msg.getString("taskname"));
	}
}
