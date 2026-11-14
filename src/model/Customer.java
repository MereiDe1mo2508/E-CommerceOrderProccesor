package model;

public class Customer {
    private int customerId;
    private String name;
    private String email;
    private String phone;
    public Customer(int customerId, String name, String email, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    public Customer() {
    }
    public void setId(int customerId) {
        this.customerId = customerId;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String displayInfo() {
        return "Id: " + customerId + "\nName: " + name + "\nEmail: " + email + "\nPhone Number: " + phone;
    }
}
