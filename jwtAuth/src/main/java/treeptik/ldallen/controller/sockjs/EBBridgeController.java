package treeptik.ldallen.controller.sockjs;

import com.github.aesteve.vertx.nubes.annotations.sockjs.bridge.EventBusBridge;
import com.github.aesteve.vertx.nubes.annotations.sockjs.bridge.InboundPermitted;
import com.github.aesteve.vertx.nubes.annotations.sockjs.bridge.OutboundPermitted;

@EventBusBridge("/eventbus/*")
@OutboundPermitted(address = "taskList.update")
@InboundPermitted(address = "task.add")
@InboundPermitted(address = "task.del")
@InboundPermitted(address = "task.register")
public class EBBridgeController {

}