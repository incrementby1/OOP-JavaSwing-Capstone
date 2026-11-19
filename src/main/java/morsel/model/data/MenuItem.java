package morsel.model.data;

/**
 * Represents a menu item in the catalog.
 */
public class MenuItem {
    private String name;
    private String description;
    private double price;
    private long merchantId;
    private String category;
    
    public MenuItem() {
    }
    
    public MenuItem(String name, String description, double price, long merchantId, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.merchantId = merchantId;
        this.category = category;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public long getMerchantId() {
        return merchantId;
    }
    
    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    @Override
    public String toString() {
        return String.format("%-20s | %-40s | $%.2f | %-15s", 
            name, 
            description != null ? description : "", 
            price,
            category != null ? category : "");
    }
}
