package treeptik.ldallen.services;

import com.github.aesteve.vertx.nubes.annotations.services.Consumer;
import com.github.aesteve.vertx.nubes.annotations.services.PeriodicTask;
import com.github.aesteve.vertx.nubes.services.Service;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;

/**
 * Created by louis-marie on 29/06/15.
 */

public class EchoService implements Service {

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


    @PeriodicTask(3000)
    public void sendPeriodic() {
        vertx.eventBus().publish("services.mainstream", "new msg from echo");
    }

    @Consumer("services.echo")
    public void echoBack(Message<String> message) {
        System.out.println("received: " + message.body());
        message.reply(message.body());
    }
}
