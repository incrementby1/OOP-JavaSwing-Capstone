public class OrderStatus {
	private String status;

	public OrderStatus() {
		this.status = "Pending";
	}

	public OrderStatus(String status) {
		this.status = status;
	}

	public String pollOrderStatus() {
		return status;
	}

	public void confirmOrderReceived() {
		status = "Received";
	}
}
