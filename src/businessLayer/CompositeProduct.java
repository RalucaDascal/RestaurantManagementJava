package businessLayer;

import java.util.*;
/**
 * Aceasta clasa mosteneste clasa MenuItem si mai adauga variabila price si defineste metoda
 * abstracta conform categoriei elementului
 */
public class CompositeProduct extends MenuItem {
	private double price;
    private ArrayList<MenuItem> ingredients=new ArrayList<MenuItem>();
	
	public CompositeProduct(int idItem, String name,double price2, String category,List <MenuItem> products) {
		super(idItem, name, category);
		this.setPrice(price2);
		this.ingredients=(ArrayList<MenuItem>) products;
	}
	public ArrayList<MenuItem> getProducts() {
		return ingredients;
	}

	public void setProducts(ArrayList<MenuItem> products) {
		this.ingredients = products;
	}

	public double computePrice() {
		double totalPrice = 0;
		for(MenuItem product:ingredients) {
			totalPrice = totalPrice+product.computePrice();
		}
		return totalPrice;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	

}
