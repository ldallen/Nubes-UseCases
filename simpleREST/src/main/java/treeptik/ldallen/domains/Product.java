package treeptik.ldallen.domains;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Product {

	private String name;
	private double price;
	private int weight;

	public Product() {
		this.name = "productName";
		this.price = 10.0;
		this.weight = 100;
	}

	public Product(String name, double price, int weight) {
		this.name = name;
		this.price = price;
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return name + "/" + price +"/" +weight;
	}
}
