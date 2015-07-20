package treeptik.ldallen.controller;

import com.github.aesteve.vertx.nubes.annotations.Controller;
import com.github.aesteve.vertx.nubes.annotations.File;
import com.github.aesteve.vertx.nubes.annotations.routing.http.GET;

@Controller("/")
public class BaseController {

    @GET
    @File
    public String rootView() {
        return "web/assets/index.html";
    }

}