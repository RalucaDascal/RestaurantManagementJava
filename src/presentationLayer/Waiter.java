package presentationLayer;

import businessLayer.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
/**
 * 
 *  Cuprinde toate operatiile pe care trebuie sa le poata face un Waiter
 */
public class Waiter extends JFrame {

	private static IRestaurantProcessing r;
	private static JFrame frame = new JFrame();
	private static JLabel labelname = new JLabel("Product: ");
	private static JComboBox comboname = new JComboBox();
	private static JLabel labeltable = new JLabel("Table: ");
	private static JTextField texttable = new JTextField(10);

	private static JButton product = new JButton("Add Product");
	private static JButton order = new JButton("Add Order/Bill");
	private static JButton menu = new JButton("Menu");

	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();
	private JPanel generalPanel = new JPanel();

	private JScrollPane scrollOrder;
	private static DefaultTableModel dTable;
	private static JTable tabelOrder;
	private static ArrayList<MenuItem> menuList = new ArrayList<MenuItem>();
	/**
	 * 
	 * @param r reprezinta interfata
	 */
	public Waiter(IRestaurantProcessing r) {
		this.r = r;
		frame.setSize(1000, 600);
		frame.setTitle("Waiter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel1.add(labelname);
		panel1.add(comboname);
		panel1.add(labeltable);
		panel1.add(texttable);
		panel2.add(menu);
		panel2.add(product);
		panel2.add(order);

		String[] tableHeader1 = { "OrderID", "Date","Products", "Table" };
		dTable = new DefaultTableModel(tableHeader1, 0);
		tabelOrder = new JTable(dTable);
		scrollOrder = new JScrollPane(tabelOrder);
		ArrayList<String[]> data = createTable();
		for (int i = 0; i < data.size(); i++) {
			dTable.addRow(data.get(i));
		}
		panel3.add(scrollOrder);
		generalPanel.add(panel1);
		generalPanel.add(panel2);
		generalPanel.add(panel3);
		generalPanel.setLayout(new BoxLayout(generalPanel, BoxLayout.Y_AXIS));

		frame.setContentPane(generalPanel);
		frame.setVisible(true);

	}
/**
 * 
 * Crearea unui tabel in interfata
 */
	private ArrayList<String[]> createTable() {
		ArrayList<MenuItem> itemList = this.r.getMenu();
		ArrayList<String[]> data = new ArrayList<String[]>();
		for (int i = 0; i < itemList.size(); i++) {
			String[] item = { Integer.toString(itemList.get(i).getIdItem()), itemList.get(i).getName(),
					Double.toString(itemList.get(i).computePrice()) };
			data.add(item);

		}
		return data;
	}

	public static void addActionListener() {
		/**
		 * Afisarea intregului meniu
		 */
		menu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				List<MenuItem> menu = new ArrayList<MenuItem>();
				menu = r.getMenu();
				for (int i = 0; i < menu.size(); i++) {
					if (menu.get(i).getCategory() == "compositeProduct") {

						comboname.addItem(menu.get(i).getName());
					} else {
						comboname.addItem(menu.get(i).getName());
					}
				}

			}
		});
   /**
    * Permite adaugarea unui produs in comanda
    */
		product.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				MenuItem proS = r.selectMenuItem((String) comboname.getSelectedItem());
				menuList.add(proS);
			}
		});
/**
 * Creeaza o comanda, adica genereaza un fisier text cu datele despre comanda si adauga comanda in tabelul din interfata
 */
		order.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int orderID = tabelOrder.getRowCount() + 1;
				Date date = new Date();
				String data="";
				String nrTable = texttable.getText();
				 for (int i=0;i<menuList.size();i++)
				 {
					 data=data+" "+menuList.get(i).getName();
				 }
				String[] dataRow = { Integer.toString(orderID), date.toString(),data, texttable.getText() };
				dTable.addRow(dataRow);
				Order o = r.createNewOrder(orderID, date, Integer.parseInt(nrTable), menuList);
				double totalprice = r.computePrice(o);
				r.gBill(orderID, date.toString(), Integer.parseInt(nrTable), totalprice,menuList);
                menuList.removeAll(menuList);
			}

		});

	}


}
