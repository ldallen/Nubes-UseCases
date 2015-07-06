package treeptik.ldallen.services;

import com.github.aesteve.vertx.nubes.annotations.services.Consumer;
import com.github.aesteve.vertx.nubes.services.Service;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;


import java.text.DateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * Created by louis-marie on 03/07/15.
 */

public class ChatService implements Service {

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


    @Consumer("chat.to.server")
    public void chatMessageHandler(Message<String> message) {
        // Create a timestamp string
        String timestamp = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(Date.from(Instant.now()));
        // Send the message back out to all clients with the timestamp prepended.
        vertx.eventBus().publish("chat.to.client", timestamp + ": " + message.body());
    }
}
