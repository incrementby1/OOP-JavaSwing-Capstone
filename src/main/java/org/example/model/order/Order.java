import java.util.ArrayList;
import java.util.List;

public class Order {
	private List<OrderEntry> entries;
	private String paymentMode;
	private String address;

	public Order() {
		this.entries = new ArrayList<>();
	}

	public Order(List<OrderEntry> entries, String paymentMode, String address) {
		this.entries = new ArrayList<>(entries);
		this.paymentMode = paymentMode;
		this.address = address;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
