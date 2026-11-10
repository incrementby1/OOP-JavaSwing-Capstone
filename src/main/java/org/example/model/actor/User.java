public class User{
    private String name;
    private String address;
    private int uid;

    public User() {
    }

    public User(String name, String address, int uid) {
        this.name = name;
        this.address = address;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}