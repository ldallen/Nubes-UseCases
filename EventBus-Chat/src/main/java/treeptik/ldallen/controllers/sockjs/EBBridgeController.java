package treeptik.ldallen.controllers.sockjs;

import com.github.aesteve.vertx.nubes.annotations.sockjs.bridge.EventBusBridge;
import com.github.aesteve.vertx.nubes.annotations.sockjs.bridge.InboundPermitted;
import com.github.aesteve.vertx.nubes.annotations.sockjs.bridge.OutboundPermitted;

@EventBusBridge("/eventbus/*")
@OutboundPermitted(address = "chat.to.client")
@InboundPermitted(address = "chat.to.server")
public class EBBridgeController {

}
