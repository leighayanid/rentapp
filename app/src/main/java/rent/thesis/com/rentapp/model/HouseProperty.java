package rent.thesis.com.rentapp.model;

/**
 * Created by user on 4/11/2016.
 */
public class HouseProperty {

    public String companyName; //if any
    public String companyAddress;
    public String contact;
    public String propertyType;

    public HouseProperty() {

    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public HouseProperty(String companyAddress, String companyName, String contact, String propertyType) {
        this.companyAddress = companyAddress;
        this.companyName = companyName;
        this.contact = contact;
        this.propertyType = propertyType;
    }


}
