package treeptik.ldallen.services;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
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

					//auth test
					conn.execute("create table user (username varchar(255), password varchar(255), password_salt varchar(255) );", t -> {
					});
					conn.execute("create table user_roles (username varchar(255), role varchar(255));", t -> {
					});
					conn.execute("create table roles_perms (role varchar(255), perm varchar(255));", t -> {
					});

					conn.execute("insert into user values ('tim', 'EC0D6302E35B7E792DF9DA4A5FE0DB3B90FCAB65A6215215771BF96D498A01DA8234769E1CE8269A105E9112F374FDAB2158E7DA58CDC1348A732351C38E12A0', 'C59EB438D1E24CACA2B1A48BC129348589D49303858E493FBE906A9158B7D5DC');", t -> {
					});
					conn.execute("insert into user values ('admin', 'B83DBF72D08031A25E5FBCE46601AA9F263CD1548B63A5F57388C80EC1804E069C9703B52183EE8D40C8E0CB5E9C555EF01414C88667D0F728D7D716440EDB64', '');", t -> {
					});
					conn.execute("insert into user_roles values ('tim', 'standard');", t -> {
					});
					conn.execute("insert into user_roles values ('admin', 'standard');", t -> {
					});
					conn.execute("insert into user_roles values ('admin', 'admin');", t -> {
					});
					conn.execute("insert into roles_perms values ('standard', 'read_list');", t -> {
					});
					conn.execute("insert into roles_perms values ('admin', 'add_task');", t -> {
					});
					conn.execute("insert into roles_perms values ('admin', 'del_task');", t -> {
					});

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
