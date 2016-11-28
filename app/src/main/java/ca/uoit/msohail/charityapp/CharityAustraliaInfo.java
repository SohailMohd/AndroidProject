package ca.uoit.msohail.charityapp;

/**
 * Created by 100485582 on 11/26/2016.
 */

public class CharityAustraliaInfo {

    private String charityName;
    private String charityDescrip;
    private String charityAddress;
    private String charityPhone;
    private String charityWebsite;

    public CharityAustraliaInfo(String name, String descrip, String website){
        setCharityName(name);
        setCharityDescrip(descrip);
        //setCharityAddress(address);
        //setCharityPhone(phone);
        setCharityWebsite(website);
    }

    public void setCharityName(String charityName) {
        this.charityName = charityName;
    }

    public void setCharityDescrip(String charityDescrip){
        this.charityDescrip = charityDescrip;
    }

    public void setCharityAddress(String charityAddress) {
        this.charityAddress = charityAddress;
    }

    public void setCharityPhone(String charityPhone) {
        this.charityPhone = charityPhone;
    }

    public void setCharityWebsite(String charityWebsite) {
        this.charityWebsite = charityWebsite;
    }


    public String getCharityName() {return charityName;}

    public String getCharityDescrip() {return charityDescrip;}

    public String getCharityAddress() {return charityAddress;}

    public String getCharityPhone() {return charityPhone;}

    public String getCharityWebsite() {return charityWebsite;}

}
