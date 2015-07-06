package treeptik.ldallen.services;

import com.github.aesteve.vertx.nubes.services.Service;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import treeptik.ldallen.domains.Product;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProductService implements Service  {

    private Map<String, Product> products ;
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

    public void add( int id,Product product) {
        products.put(Integer.toString(id), product);
    }

    public void del(int id){
        products.remove(Integer.toString(id));
    }

    public Product getProduct(int id) {
        return products.get(Integer.toString(id)); // select request to db
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
