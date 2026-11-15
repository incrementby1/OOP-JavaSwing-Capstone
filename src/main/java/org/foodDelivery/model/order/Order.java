package org.foodDelivery.model.order;

import org.foodDelivery.model.Address;

import java.util.ArrayList;
import java.util.List;

public class Order {
	/**
	 * TODO:
	 * - [x] need to add separate addresses for user and restaurant
	 * - [ ] Make different states for paymentMode
	 * - [ ] OPTIONAL: make setAddress
	 */
	public Order() {
		this.entries = new ArrayList<>();
	}
	private List<OrderEntry> entries;
	private String paymentMode;
	private Address userAddress;
	private Address resAddress;

	public Order(List<OrderEntry> entries, String paymentMode, Address resAddress, Address userAddress) {
		this.entries = new ArrayList<>(entries);
		this.paymentMode = paymentMode;
		this.resAddress = resAddress;
		this.userAddress = userAddress;
	}

	public List<OrderEntry> getEntries() {
		return entries;
	}

	public void setEntries(List<OrderEntry> entries) {
		this.entries = new ArrayList<>(entries);
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Address getUserAddress() {
		return userAddress;
	}
	public Address getResAddress() { return resAddress; }

	/**
	 * Custom Address the user wants to add
	 * Will be an empty function for now
	 */
	public void setAddress(Address userAddress) {
		this.userAddress = userAddress;
	}
}
