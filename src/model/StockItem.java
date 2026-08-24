package model;

import java.time.LocalDate;

public class StockItem {
	private int id;
	private String name;
	private String category;
	private int quantity;
	private String unit;
	private int unitPrice;
	private LocalDate expirationDate;

	public StockItem(int id, String name, String category, int quantity, String unit, int unitPrice,
			LocalDate expirationDate) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.quantity = quantity;
		this.unit = unit;
		this.unitPrice = unitPrice;
		this.expirationDate = expirationDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}
}
