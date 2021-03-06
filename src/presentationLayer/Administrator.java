package presentationLayer;

import businessLayer.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
 *  Cuprinde toate operatiile pe care trebuie sa le poata face un administrator
 */
public class Administrator extends JFrame {
	private static IRestaurantProcessing r;
	private static JFrame frame = new JFrame();
	private static JLabel labelname = new JLabel("Name: ");
	private static JTextField textname = new JTextField(20);
	private static JLabel labelprice = new JLabel("Price: ");
	private static JTextField textprice = new JTextField(20);

	private static JComboBox ingredients = new JComboBox();
	private static JButton select = new JButton("Add Ingredient");

	private JLabel labelremovename = new JLabel("Remove/Edit item: ");
	private static JTextField textremovename = new JTextField(20);

	private static JButton add = new JButton("Add");
	private static JButton edit = new JButton("Edit");
	private static JButton delete = new JButton("Delete");

	private JPanel panel1 = new JPanel();
	private JPanel panel2 = new JPanel();
	private JPanel panel3 = new JPanel();
	private JPanel panel4 = new JPanel();
	private JPanel panel5 = new JPanel();
	private JPanel generalPanel = new JPanel();

	private JScrollPane scrollMenu;
	private static DefaultTableModel dTable;
	private static JTable tabelMenu;
	private static ArrayList<MenuItem> ingredientsList = new ArrayList<MenuItem>();
/**
 * 
 * @param r reprezinta interfata
 */
	public Administrator(IRestaurantProcessing r) {
		this.r = r;
		frame.setSize(1000, 600);
		frame.setTitle("Administrator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel1.add(labelname);
		panel1.add(textname);
		panel1.add(labelprice);
		panel1.add(textprice);
		panel2.add(ingredients);
		ingredients.setEditable(true);
		panel2.add(select);
		panel3.add(labelremovename);
		panel3.add(textremovename);
		panel4.add(add);
		panel4.add(delete);
		panel4.add(edit);

		String[] tableHeader1 = { "ID", "Name", "Ingredients", "Price" };
		dTable = new DefaultTableModel(tableHeader1, 0);
		tabelMenu = new JTable(dTable);
		scrollMenu = new JScrollPane(tabelMenu);
		ArrayList<String[]> data = createTable();
		for (int i = 0; i < data.size(); i++) {
			dTable.addRow(data.get(i));
		}
		panel5.add(scrollMenu);
		generalPanel.add(panel1);
		generalPanel.add(panel2);
		generalPanel.add(panel3);
		generalPanel.add(panel4);
		generalPanel.add(panel5);
		generalPanel.setLayout(new BoxLayout(generalPanel, BoxLayout.Y_AXIS));
		frame.setContentPane(generalPanel);
		frame.setVisible(true);

	}


	public static void addActionListener() {
		/**
		 * Realizeaza adaugarea unui element atat in meniu cat si in tabelul din interfata
		 */
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idItem = tabelMenu.getRowCount() + 1;
				String ingData = "";
				double price = 0;
				if (ingredientsList.size() != 0) {
					for (int i = 0; i < ingredientsList.size() - 1; i++) {
						ingData = ingData + ingredientsList.get(i).getName() + ",";
						price = price + ingredientsList.get(i).computePrice();

					}
					ingData = ingData + ingredientsList.get(ingredientsList.size() - 1).getName();
					price = price + ingredientsList.get(ingredientsList.size() - 1).computePrice();
				} else {
					ingredients.addItem(textname.getText());
				}
				if (price != 0) {
					String[] data = { Integer.toString(idItem), textname.getText(), ingData, String.valueOf(price) };
					dTable.addRow(data);
				} else {
					String[] data = { Integer.toString(idItem), textname.getText(), "-",
							String.valueOf(Double.parseDouble(textprice.getText())) };
					dTable.addRow(data);

				}

				ArrayList<MenuItem> i = new ArrayList<MenuItem>();
				if (price == 0) {
					r.addMenuItem(idItem, textname.getText(), Double.parseDouble(textprice.getText()), i);
				} else {
					r.addMenuItem(idItem, textname.getText(), price, ingredientsList);
				}
				ingredientsList.removeAll(ingredientsList);
			}
		});

		/**
		 * Realizeaza stergerea unui element atat din meniu cat si din tabelul din interfata
		 */
		delete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String name = "";
				name = textremovename.getText();
				int i = 0;
				while (i < tabelMenu.getRowCount()) {
					if (tabelMenu.getValueAt(i, 1).equals(name)) {
						ingredients.removeItem(tabelMenu.getValueAt(i, 1));
						dTable.removeRow(i);
						r.deleteMenuItem(name);
					} else {
						int k = 0;
						String data = (String) tabelMenu.getValueAt(i, 2);
						String[] sir = data.split(",");
						for (int j = 0; j < sir.length; j++) {
							if (sir[j].equals(name)) {
								r.deleteMenuItem((String) tabelMenu.getValueAt(i, 1));
								dTable.removeRow(i);
								k++;
							}
						}
						if (k == 0) {
							i++;
						}
					}
				}
				/*
				 * List<MenuItem> menu = new ArrayList<MenuItem>(); menu=r.getMenu(); for (
				 * i=0;i<menu.size();i++) { System.out.println(menu.get(i).getName() +
				 * menu.get(i).getCategory()); }
				 */
			}
		});
     /**
      * Realizeaza adaugarea unui produs de baza intr-un produs compus
      */
		select.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				MenuItem ingS = r.selectMenuItem((String) ingredients.getSelectedItem());
				ingredientsList.add(ingS);
				textprice.setText("Nu introduceti pret");

			}
		});
 /**
  * Permite schimabrea numelui sau a pretului a unui produs deja existent 
  */
		edit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int idItem = 0;
				int[] sirId = new int[100];
				for (int i = 0; i < tabelMenu.getRowCount(); i++) {
					sirId[i] = 0;
				}
				String name = textremovename.getText();
				for (int i = 0; i < tabelMenu.getRowCount(); i++) {
					if (tabelMenu.getValueAt(i, 1).equals(name)) {
						idItem = Integer.parseInt((String) tabelMenu.getValueAt(i, 0));
						dTable.removeRow(i);
						String[] data = { Integer.toString(idItem), textname.getText(), "-",
								String.valueOf(Double.parseDouble(textprice.getText())) };
						dTable.addRow(data);
						r.editMenuItem(name, idItem, textname.getText(), Double.parseDouble(textprice.getText()),
								ingredientsList);
					}
				}
				for (int i = 0; i < tabelMenu.getRowCount(); i++) {
					int k = 0;
					String ingData = "";
					Double price = (double) 0;
					String dataSplit = (String) tabelMenu.getValueAt(i, 2);
					if (!dataSplit.equals("-")
							&& sirId[Integer.parseInt((String) tabelMenu.getValueAt(i, 0)) - 1] == 0) {
						String[] sir = dataSplit.split(",");
						for (int j = 0; j < sir.length; j++) {
							if (sir[j].equals(name)) {
								k++;
							}
							MenuItem ingS = r.selectMenuItem(sir[j]);
							if (sir[j].equals(name)) {
								ingS = r.selectMenuItem(textname.getText());
							}
							ingredientsList.add(ingS);
						}
						for (int j = 0; j < ingredientsList.size() - 1; j++) {
							ingData = ingData + ingredientsList.get(j).getName() + ",";
							price = price + ingredientsList.get(j).computePrice();
						}

						ingData = ingData + ingredientsList.get(ingredientsList.size() - 1).getName();
						price = price + ingredientsList.get(ingredientsList.size() - 1).computePrice();

						if (k != 0) {
							String[] data = { (String) tabelMenu.getValueAt(i, 0), (String) tabelMenu.getValueAt(i, 1),
									ingData, String.valueOf(price) };
							sirId[Integer.parseInt((String) tabelMenu.getValueAt(i, 0)) - 1] = 1;
							r.editMenuItem((String) tabelMenu.getValueAt(i, 1),
									Integer.parseInt((String) tabelMenu.getValueAt(i, 0)),
									(String) tabelMenu.getValueAt(i, 1), Double.parseDouble(textprice.getText()),
									ingredientsList);
							dTable.removeRow(i);
							dTable.addRow(data);

						}
					}
				}

			}
		});

	}

	public static void main(String[] args) {
		Restaurant r = new Restaurant();
		addActionListener();
		Administrator ad = new Administrator(r);
		Waiter wa = new Waiter(r);
		wa.addActionListener();

	}

}
