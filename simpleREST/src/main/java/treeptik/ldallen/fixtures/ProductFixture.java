package treeptik.ldallen.fixtures;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import treeptik.ldallen.domains.Product;

import com.github.aesteve.vertx.nubes.annotations.services.Service;
import com.github.aesteve.vertx.nubes.fixtures.Fixture;
import treeptik.ldallen.services.ProductService;

public class ProductFixture extends Fixture {

	@Service("productService")
	private ProductService products;

	@Override
	public int executionOrder() {
		return 1;
	}

	@Override
	public void startUp(Vertx vertx, Future<Void> future) {
		Product product1 = new Product("Egg Whisk",3.99,150);
		Product product2 = new Product("Tea Cosy",5.99,100);
		Product product3 = new Product("Spatula",1.00,80);
		products.add(1,product1);
		products.add(2,product2);
		products.add(3,product3);
		future.complete();
	}

	@Override
	public void tearDown(Vertx vertx, Future<Void> future) {
		products.clear();
		future.complete();
	}

}
