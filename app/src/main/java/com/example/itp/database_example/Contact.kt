package com.example.itp.database_example

 class Contact {

    //private variables
    lateinit var crF_No: String
    lateinit var name: String
    lateinit var address: String
    lateinit var bill_amt: String
    lateinit var pending_amt: String
    lateinit var mobile_num: String
    lateinit var total_amt: String
    lateinit var businessname: String
    var flag: Int = 0


    // Empty constructor
    constructor() {

    }

    // constructor
    constructor(crfno: String, name: String, address: String, billamt: String, pendingamt: String, mobilenumber: String, totalamt: String,
                business: String, flag: Int) {
        this.crF_No = crfno
        this.name = name
        this.address = address
        this.bill_amt = billamt
        this.pending_amt = pendingamt
        this.mobile_num = mobilenumber
        this.total_amt = totalamt
        this.businessname = business
        this.flag = flag
    }

}
