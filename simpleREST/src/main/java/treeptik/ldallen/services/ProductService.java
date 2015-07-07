package treeptik.ldallen.services;

import io.vertx.core.Future;
import io.vertx.core.Vertx;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import treeptik.ldallen.domains.Product;

import com.github.aesteve.vertx.nubes.services.Service;

public class ProductService implements Service {

	private Map<Integer, Product> products;
	private Vertx vertx;

	@Override
	public void init(Vertx vertx) {
		this.vertx = vertx;
	}

	@Override
	public void start(Future<Void> future) {
		products = new HashMap<>();
		future.complete();
	}

	@Override
	public void stop(Future<Void> future) {
		products.clear();
		future.complete();
	}

	// ~= UPDATE FROM ...
	public void update(int id, Product product) {
		products.put(id, product);
	}

	// ~= INSERT INTO (with a sequence)
	public void add(Product product) {
		// find the max index in map, like a relational DB using an auto-generated sequence
		Integer max = products.keySet().stream().max(Integer::compare).orElse(0);
		update(max + 1, product);
	}

	public void del(int id) {
		products.remove(id);
	}

	public Product getProduct(int id) {
		return products.get(id); // select request to db
	}

	public Collection<Product> getProducts() {
		return products.values();
	}

	public boolean isEmpty() {
		return products.isEmpty();
	}

	public int size() {
		return products.size();
	}

	public void clear() {
		products.clear();
	}

}
