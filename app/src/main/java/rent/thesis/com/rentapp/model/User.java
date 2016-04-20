package rent.thesis.com.rentapp.model;

public class User {

    public String username;
    public String completeName;
    public String emailAddress;
    public String contactNo;
    public String address;
    public String provider;
    public boolean hasBusiness;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isHasBusiness() {
        return hasBusiness;
    }

    public void setHasBusiness(boolean hasBusiness) {
        this.hasBusiness = hasBusiness;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(){

    }

    public User(String address, String completeName, String contactNo, String emailAddress, boolean hasBusiness, String provider, String username) {
        this.address = address;
        this.completeName = completeName;
        this.contactNo = contactNo;
        this.emailAddress = emailAddress;
        this.hasBusiness = hasBusiness;
        this.provider = provider;
        this.username = username;
    }


}
