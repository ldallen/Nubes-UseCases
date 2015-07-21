package treeptik.ldallen.services;

import com.github.aesteve.vertx.nubes.annotations.services.Consumer;
import com.github.aesteve.vertx.nubes.services.Service;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

public class InterceptorService implements Service {

	private Vertx vertx;

	@Override
	public void init(Vertx vertx) {
		this.vertx = vertx;
	}

	@Override
	public void start(Future<Void> future) {
		future.complete();
	}

	@Override
	public void stop(Future<Void> future) {
		future.complete();
	}


	@Consumer("web.tasks.service")
	public void taskServiceHandler(Message<JsonObject> message) {

		JsonObject json = message.body();
		JsonObject jsonOptions = json.getJsonObject("options");
		DeliveryOptions options = new DeliveryOptions(jsonOptions);
		JsonObject body  = json.getJsonObject("body");
		vertx.eventBus().send("service.tasks",body,options, reply ->{
			if(reply.succeeded())
				message.reply(reply.result().body().toString());
		});
	}
}
