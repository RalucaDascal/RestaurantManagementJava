package businessLayer;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import dataLayer.FileW;
/**
 * 
 * In aceasta clasa se definesc metode necesare aplicatiei
 *
 */
public class Restaurant implements IRestaurantProcessing {
	private static final MenuItem ITEM = null;
	private static List<MenuItem> menu = new ArrayList<MenuItem>();
	private Map<Order, ArrayList<MenuItem>> newOrder = new HashMap();

	/**
	 * In aceasta metoda se adauga un element in meniu
	 */
	public void addMenuItem(int idItem, String name, double price, ArrayList<MenuItem> items) {
		MenuItem product = null;
		if (items.size() == 0) {
			product = new BaseProduct(idItem, name, price, "baseProduct");
			product.computePrice();
		} else if (items.size() >= 1) {
			product = new CompositeProduct(idItem, name, price, "compositeProduct", items);
			product.computePrice();
		}
		menu.add(product);

	}
	/**
	 * Aceasta metoda permite stergerea unui elemenent cunoscandu-i numele
	 */

	public void deleteMenuItem(String name) {
		ArrayList<MenuItem> ingredients;
		CompositeProduct product;
		for (int i = 0; i < menu.size(); i++) {
			if (menu.get(i).getName().equals(name)) {
				menu.remove(i);
			}

		}
	}
	
/**
 * Metoda permite modificarea unui element specificat prin nume
 */
	public void editMenuItem(String sName, int idItem, String name, double price, ArrayList<MenuItem> items) {
	   deleteMenuItem(sName);
	   addMenuItem( idItem, name,  price, items);
	}

	/**
	 * Aceasta metoda returneaza obiectul de tip MenuItem specificat prin nume
	 */
	public MenuItem selectMenuItem(String sName) {

		for (MenuItem product : menu) {
			if (product.getName().equals(sName)) {
				return product;
			}
		}
		return ITEM;
	}

	/**
	 * Metoda creeaza o noua comanda si asigura adaugarea acesteia in HashMap
	 */
	public Order createNewOrder(int orderID, Date date, int table, ArrayList<MenuItem> ord) {
		Order order = new Order(orderID, date, table);
		newOrder.put(order, ord);
		return order;
	}
	/**
	 * Apeleaza o functie care genereaza un fisier text care cuprinde informatiile despre o coamnda
	 */
	public void gBill(int orderID, String date, int table,double price, ArrayList<MenuItem> orderList) 
	{
		FileW.cBill(orderID,date,table,price,orderList);
	}

	/**
	 * Returneaza pretul unei comenzi 
	 */
	public double computePrice(Order o) {
		ArrayList<MenuItem> ingredients = new ArrayList<MenuItem>();
		ingredients = newOrder.get(o);
		double totalprice = 0;
		for (int i = 0; i < ingredients.size(); i++) {
		if (ingredients.get(i).getCategory().equals("compositeProduct"))
			{
			totalprice = totalprice +((CompositeProduct) ingredients.get(i)).getPrice();
			}
			else {
				totalprice = totalprice +ingredients.get(i).computePrice();
			}
			
		}
		return totalprice;
	}
 
	/**
	 * Getter pentru meniu
	 */
	public ArrayList<MenuItem> getMenu() {
		return (ArrayList<MenuItem>) menu;
	}
/**
 * 
 * @param menu -datele care vor fi introduse in meniu
 */
	public static void setMenu(List<MenuItem> menu) {
		Restaurant.menu = menu;
	}

}
