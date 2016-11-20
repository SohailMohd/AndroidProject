package ca.uoit.msohail.charityapp;

/**
 * Created by 100485582 on 11/17/2016.
 */

public class User {

    private String userName;
    private String userEmail;
    private String address;
    private String phoneNumber;


    public User(String name, String email, String phoneNumber, String address){
        setUserName(name);
        setAddress(address);
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }

    public void setUserName(String userName) {this.userName = userName; }

    public void setEmail(String email) {this.userEmail = email; }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail(){ return  userEmail; }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


}
