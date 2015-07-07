package treeptik.ldallen.controller.todolist;

import io.vertx.core.http.HttpServerResponse;

import java.util.Collection;

import treeptik.ldallen.domains.Product;
import treeptik.ldallen.services.ProductService;

import com.github.aesteve.vertx.nubes.annotations.Controller;
import com.github.aesteve.vertx.nubes.annotations.mixins.ContentType;
import com.github.aesteve.vertx.nubes.annotations.params.Param;
import com.github.aesteve.vertx.nubes.annotations.params.RequestBody;
import com.github.aesteve.vertx.nubes.annotations.routing.http.DELETE;
import com.github.aesteve.vertx.nubes.annotations.routing.http.GET;
import com.github.aesteve.vertx.nubes.annotations.routing.http.POST;
import com.github.aesteve.vertx.nubes.annotations.routing.http.PUT;
import com.github.aesteve.vertx.nubes.annotations.services.Service;

@Controller("/")
@ContentType("application/json")
// since it sounds like a JSON API, the client should send/receive JSON (see "Accept" header, "Content-Type" header
public class ProductsController {

	@Service("productService")
	private ProductService products;

	@GET
	public void indexView(HttpServerResponse response) {
		response.end("This is a REST example using Vert.x Nubes framework");
	}

	@GET("products")
	public Collection<Product> getProducts() {
		return products.getProducts();
	}

	@GET("products/:id")
	public Product getProduct(@Param("id") Integer id) {
		return products.getProduct(id);
	}

	@POST("products")
	public Product createProduct(@RequestBody Product product) {
		products.add(product);
		return product;
	}

	@PUT("products/:id")
	public Product updateProduct(@RequestBody Product product, @Param("id") Integer id) {
		products.update(id, product);
		return product;
	}

	@DELETE("products/:id")
	public Product removeProduct(@Param("id") Integer id) {
		Product product = products.getProduct(id);
		products.del(id);
		return product; // can be convenient for clients to get what they deleted
	}

}