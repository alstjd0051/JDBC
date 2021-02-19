package stock.vo;

import java.sql.Date;

public class Stock {

	private String productID;
	private String pName;
	private int price;
	private String description;
	private int stock;

	public Stock() {
		super();
	}

	public Stock(String productID, String pName, int price, String description, int stock) {
		super();
		this.productID = productID;
		this.pName = pName;
		this.price = price;
		this.description = description;
		this.stock = stock;
	}
	
	public Stock(String productID, String pName, Date date) {
		this.productID = productID;
		this.pName = pName;
	}
	
	public Stock(String productID, int price) {
		this.productID = productID;
		this.price = price;
	}
	
	public Stock(String productID, String description) {
		this.productID = productID;
		this.description = description;
	}
	
	public Stock(String productID) {
		this.productID = productID;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return productID + "\t\t" + pName + "\t\t" + price + "\t\t"
				+ description + "\t\t" + stock;
	}

}
