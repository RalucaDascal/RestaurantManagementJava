package businessLayer;
/**
 * Aceasta clasa mosteneste clasa MenuItem si mai adauga variabila price si defineste metoda
 * abstracta conform categoriei elementului
 */
public class BaseProduct extends MenuItem {
	private double price;

	public BaseProduct(int idItem, String name, double d,String category) {
		super(idItem, name, category);
		this.price = d;
	}

	public double computePrice() {
		return this.price;
	}
}
