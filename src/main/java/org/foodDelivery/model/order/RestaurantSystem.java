import  java.util.ArrayList;
import java.util.List;

public class RestaurantSystem {
    List<User> users;
    List<Restaurant> restaurant;

    System()
    {
        users = new ArrayList<>();
        restaurant = new ArrayList<>();
    }

    public Order placeOrder(MenuItem entry, User user)
    {
        int user_id = user.uuid;

        Order = new Order();
    }

    public List<MenuItem> retrieveCatalog(Restaurant rstrnt)
    {
        return rstrnt.getMenu();
    }

    public PaymentReciept placePayment(Restaurant rstrnt, User user)
    {

    }

    public OrderStatus assignOrder(Order order)
    {
        // return order.
    }

    public Rider assignRider(Order order)
    {

    }
}