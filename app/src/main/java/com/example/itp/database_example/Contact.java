package com.example.itp.database_example;

public class Contact {

    //private variables
    String CRF_No;
    String name;
    String address;
    String bill_amt;
    String pending_amt;
    String mobile_num;
    String total_amt;
    String businessname;
    int flag;


    // Empty constructor
    public Contact() {

    }


    // constructor
    public Contact(String crfno, String name, String address, String billamt, String pendingamt, String mobilenumber, String totalamt,
                   String business, int flag) {
        this.CRF_No = crfno;
        this.name = name;
        this.address = address;
        this.bill_amt = billamt;
        this.pending_amt = pendingamt;
        this.mobile_num = mobilenumber;
        this.total_amt = totalamt;
        this.businessname = business;
        this.flag = flag;
    }

    public String getCRF_No() {
        return CRF_No;
    }

    public void setCRF_No(String CRF_No) {
        this.CRF_No = CRF_No;
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

    public String getBill_amt() {
        return bill_amt;
    }

    public void setBill_amt(String bill_amt) {
        this.bill_amt = bill_amt;
    }

    public String getPending_amt() {
        return pending_amt;
    }

    public void setPending_amt(String pending_amt) {
        this.pending_amt = pending_amt;
    }

    public String getMobile_num() {
        return mobile_num;
    }

    public void setMobile_num(String mobile_num) {
        this.mobile_num = mobile_num;
    }

    public String getTotal_amt() {
        return total_amt;
    }

    public void setTotal_amt(String total_amt) {
        this.total_amt = total_amt;
    }

    public String getBusinessname() {
        return businessname;
    }

    public void setBusinessname(String businessname) {
        this.businessname = businessname;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

}
