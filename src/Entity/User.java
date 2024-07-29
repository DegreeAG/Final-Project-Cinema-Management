package Entity;

import Constant.UserRole;

public class User {
    private static int AUTO_ID = 1;

    private int id;
    private String email;
    private String password;
    private UserRole role;
    private String phone;
    private String address;
    private double balance;
    private String name;

    public User(int id, String email, String password, UserRole role, String phone, String address, double balance, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.phone = phone;
        this.address = address;
        this.balance = balance;
        this.name = name;
    }

    public static int getAutoId() {
        return AUTO_ID;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", balance=" + balance +
                ", name='" + name + '\'' +
                '}';
    }
}
