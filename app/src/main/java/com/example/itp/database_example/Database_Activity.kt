package com.example.itp.database_example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest

import java.util.ArrayList
import java.util.Arrays
import java.util.HashMap

/**
 * Created by ITP on 04-Sep-17.
 */

abstract class Database_Activity : AppCompatActivity() {
    internal abstract var db: DB_Helper
    internal lateinit var single_id: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first)

        val btn = findViewById(R.id.button) as Button
        val pay_btn = findViewById(R.id.button_pay) as Button
        val delete_btn = findViewById(R.id.button_deleteall) as Button
        val et_val = findViewById(R.id.et_updatevalue) as EditText

        db = DB_Helper(this)

        /**
         * CRUD Operations
         */
        // Inserting Contacts
        /*Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));

        db.addContact(new Contact("Karthik", "9533333333"));

        db.updateContact(new Contact("Karthik", "000000003"));

        db.deleteContact(new Contact("Karthik", "000000003"));

        db.addContact(new Contact("Karthik", "9533333333"));
*/
        // Reading all contacts
        Log.e("Reading: ", "Reading all contacts..")
        val contacts = db.allContacts

        for (cn in contacts) {
            val log = "Id: " + cn.crF_No + " ,Name: " + cn.name + " ,Flag: " + cn.flag
            // Writing Contacts to log
            Log.e("DATABASE ITEMS: ", log)
        }
        if (contacts.size > 0) {
            single_id = db.getContact("VXL2220000130C0048069")

            val log = "Id: " + single_id.crF_No + " ,Name: " + single_id.name + " ,Phone: " + single_id.mobile_num
            // Writing Contacts to log
            Log.e("Single value: ", log)
            et_val.setText(single_id.name)
        }

        val name = "S/O Abc Asdfv dfgt"
        // String[] arrrayname = name.split(" ");
        // name = arrrayname[1];
        Log.e("sssssss", name + "---fullnammeeee" + name.subSequence(name.indexOf(" "), 4))

        btn.setOnClickListener { request() }

        delete_btn.setOnClickListener {
            db.deleteAllContacts()
            Toast.makeText(this@Database_Activity, "Deleted successfully", Toast.LENGTH_SHORT).show()
        }


        pay_btn.setOnClickListener {
            single_id.name = et_val.text.toString()
            single_id.flag = 1
            db.updateContact(single_id)
            val a = db.getContact("VXL2220000130C0048069").flag
            Toast.makeText(this@Database_Activity, "FLAGGG---" + a, Toast.LENGTH_SHORT).show()
        }

        Toast.makeText(this@Database_Activity, "" + db.contactsCount, Toast.LENGTH_SHORT).show()
    }

    fun request() {
        val url = "http://192.168.1.61/testing/ezycabledigi/app/index.php/GetRequest"

        val postRequest = object : StringRequest(Request.Method.POST, url, Response.Listener { response ->
            Log.d("accessToken:", response)
            Toast.makeText(this@Database_Activity, "" + response, Toast.LENGTH_LONG).show()
            val code_length = response.substring(0, response.indexOf(' '))
            val content = response.substring(response.indexOf(" ")).replaceFirst("^\\s*".toRegex(), "").replace("#", "")/*.replaceAll("\\s+", "")*/

            val splits = splitEqually(content, 194)

            Log.e("Length", splitEqually(content, 194).size.toString())

            Log.e("CodeLength", code_length)
            Log.e("Content", content)


            for (i in splits.indices) {

                Log.e("Items  ----" + i, splits[i])

                var CRFno = splits[i].subSequence(0, 25)
                var name = splits[i].subSequence(26, 49)
                var address = splits[i].subSequence(50, 95)
                var billamount = splits[i].subSequence(96, 121)
                var pendingamount = splits[i].subSequence(122, 133)
                var mobilenumber = splits[i].subSequence(134, 154)
                var totalamount = splits[i].subSequence(155, 168)
                var businessname = splits[i].subSequence(169, 194)

                // Inserting Contacts
                Log.d("Insert: ", "Inserting ..")
                CRFno = CRFno.replace("\\s+".toRegex(), "")
                name = name.replace("\\s+".toRegex(), "")
                address = address.replace("\\s+".toRegex(), "")
                billamount = billamount.replace("\\s+".toRegex(), "")
                pendingamount = pendingamount.replace("\\s+".toRegex(), "")
                mobilenumber = mobilenumber.replace("\\s+".toRegex(), "")
                totalamount = totalamount.replace("\\s+".toRegex(), "")
                businessname = businessname.replace("\\s+".toRegex(), "")

                db.addContact(Contact(CRFno, name, address, billamount, pendingamount, mobilenumber,
                        totalamount, businessname, 0))
            }
        }, Response.ErrorListener { volleyError ->
            Log.d("error:", volleyError.toString())
            Toast.makeText(this@Database_Activity, "Error", Toast.LENGTH_SHORT).show()
        }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                val machineID = fixedLengthString("lkk!@!#!", 11)
                val version = fixedLengthString("013", 3)
                val command = fixedLengthString(":70", 3)
                val startcount = fixedLengthString("", 3)
                val endcount = fixedLengthString("036", 3)

                params.put("ScrNo", machineID + version + command + startcount + endcount)//280e900002f  lkk!@!#!  L4049C14
                return params
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Accept", "application/json")
                headers.put("Content-Type", "application/x-www-form-urlencoded")
                return headers
            }

        }
        AppController.getInstance().addToRequestQueue(postRequest)

    }

    companion object {

        fun splitEqually(text: String, size: Int): ArrayList<String> {
            // Give the list the right capacity to start with. You could use an array
            // instead if you wanted.
            val ret = ArrayList<String>((text.length + size - 1) / size)

            var start = 0
            while (start < text.length) {
                ret.add(text.subSequence(start, Math.min(text.length, start + size)).toString())
                start += size
            }
            return ret
        }

        fun fixedLengthString(string: String, length: Int): String {
            return String.format("%1$" + length + "s", string)
        }
    }
}
