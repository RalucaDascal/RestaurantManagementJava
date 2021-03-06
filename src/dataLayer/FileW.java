package dataLayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import businessLayer.MenuItem;
/**
 * 
 * Genereaza un fisier text cu informatii despre o comanda
 *
 */
public class FileW {

	public static File output;
 /**
  * 
  * @param orderID reprezinta numarul comenzii
  * @param date data la care aceasta a fost inregistrata
  * @param nrTable reprezinta masa de la care a fost solocitata
  * @param price pretul total al comenzii
  * @param orderList produsele pe care le contine comanda
  */
	public static void cBill(int orderID, String date, int nrTable, double price, ArrayList<MenuItem> orderList)  {
		 output = new File("Bill" + orderID + ".txt");
		 String data="";
		 for (int i=0;i<orderList.size();i++)
		 {
			 data=data+" "+orderList.get(i).getName();
		 }
	   if (!output.exists()) {
			try {
				output.createNewFile();
				PrintWriter p = new PrintWriter(output);
				p.println("Comanada "+orderID+"-"+ data +" a fost plasata la data "+date+" la masa "+nrTable+ ". Pret= " +price);
				p.close();
			} catch (IOException e) {
				System.out.println("Exception");
			}
		}
		else {
			try {
		
				PrintWriter p = new PrintWriter(output);
				p.println("Comanada "+orderID+"-"+ data +" a fost plasata la data "+date+"la masa "+nrTable+ " Pret= " +price);
				p.close();
			} catch (IOException e) {
				System.out.println("Exception");
			}
		
		}
	}
}
