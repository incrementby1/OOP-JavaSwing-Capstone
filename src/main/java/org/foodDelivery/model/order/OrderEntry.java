package org.foodDelivery.model.order;

import org.foodDelivery.model.order.MenuItem;

public class OrderEntry {
	private MenuItem menuItem;
	private int quantity;

	public OrderEntry() {
	}

	public OrderEntry(MenuItem menuItem, int quantity) {
		this.menuItem = menuItem;
		this.quantity = quantity;
	}

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
