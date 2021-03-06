package businessLayer;

import java.util.*;
/**
 * 
 * Creeaza o comanda
 *
 */
public class Order {
	private int idOrder;
	private Date date;
	private int table;

	public Order(int orderID, Date date, int table) {
		this.setOrderID(orderID);
		this.setDate(date);
		this.setTable(table);
	}
/**
 * Genereaza un hashcode pentru un HashMap
 */
	public int hashCode() {
       int hash=1;
       hash=7*hash+idOrder;
       hash=7*hash+((date==null)? 0 :date.hashCode());
       hash=7*hash+table;
       return hash;
	}
	
	
	public int getOrderID() {
		return idOrder;
	}

	public void setOrderID(int orderID) {
		this.idOrder = orderID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getTable() {
		return table;
	}

	public void setTable(int table) {
		this.table = table;
	}

}
