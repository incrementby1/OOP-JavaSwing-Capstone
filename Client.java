import java.util.List;
import java.util.ArrayList;
public class Client {
        
    private User user;
    private List<Order> activeOrders;

    public Client(User user) {
        this.user = user;
        this.activeOrders = new ArrayList<>();
        this.cart = new ArrayList<>();
    }

    public boolean userAuth(){
        return false;
    }

    public void userLogout(){
        
    }

    public List<MenuItem> retrieveCatalog(Restaurant restaurant){
        return new ArrayList<>();
    }

    public List<Order> monitorActiveOrders() {
        return activeOrders;
    }

    public void retrieveUserOrderFeedback(OrderStatus os) {

    }

    public Order placeOrder(MenuItem category, User user) {
    return null;
    }
}
