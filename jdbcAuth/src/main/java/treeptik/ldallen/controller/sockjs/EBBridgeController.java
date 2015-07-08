package treeptik.ldallen.controller.sockjs;

import com.github.aesteve.vertx.nubes.annotations.sockjs.bridge.EventBusBridge;
import com.github.aesteve.vertx.nubes.annotations.sockjs.bridge.InboundPermitted;
import com.github.aesteve.vertx.nubes.annotations.sockjs.bridge.OutboundPermitted;

@EventBusBridge("/eventbus/*")
@OutboundPermitted(address = "taskList.update", requiredAuthority = "read_list")
@InboundPermitted(address = "task.add", requiredAuthority = "add_task")
@InboundPermitted(address = "task.del", requiredAuthority = "del_task")
@InboundPermitted(address = "task.register")
public class EBBridgeController {

}