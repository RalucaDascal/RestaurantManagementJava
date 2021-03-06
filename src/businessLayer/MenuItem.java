package businessLayer;
/**
 *Aceasta clasa defineste fiecare cate un element din meniu
 * specificand id-ul sau, numele si tipul acestuia
 *Clasa cuprinde gettere si settere pentru fiecare variabila
 *si o metoda abstracta pe care o vom defini in clasele ce mostenesc aceasta clasa
 */
public abstract class MenuItem {
	protected int idItem;
	protected String name;
	protected String category;

	public MenuItem(int idItem, String name, String category) {
		this.setIdItem(idItem);
		this.setName(name);
		this.setCategory(category);
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getIdItem() {
		return idItem;
	}

	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public abstract double computePrice();
	
}
