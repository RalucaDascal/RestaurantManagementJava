package businessLayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
/**
 * Este definita interfata pentru toate metodele din clasa Restaurant 
 *
 */
public interface IRestaurantProcessing {
	//administrator
	public void addMenuItem(int idItem, String name, double price, ArrayList<MenuItem> items);
	public void deleteMenuItem(String name);
	public void editMenuItem(String sName, int idItem, String name, double price, ArrayList<MenuItem> items);
	public MenuItem selectMenuItem(String sName) ;
	//waiter
	public Order createNewOrder(int orderID, Date date, int table, ArrayList<MenuItem> ord);
	public double computePrice(Order o) ;
	public void gBill(int orderID, String date, int table,double price, ArrayList<MenuItem> orderList);
	public ArrayList<MenuItem> getMenu();
}
