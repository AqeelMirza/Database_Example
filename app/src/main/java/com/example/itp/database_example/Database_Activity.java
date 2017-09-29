package com.example.itp.database_example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ITP on 04-Sep-17.
 */

public class Database_Activity extends AppCompatActivity {
    DB_Helper db;
    Contact single_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first);

        Button btn = (Button) findViewById(R.id.button);
        Button pay_btn = (Button) findViewById(R.id.button_pay);
        Button delete_btn = (Button) findViewById(R.id.button_deleteall);
        final EditText et_val = (EditText) findViewById(R.id.et_updatevalue);

        db = new DB_Helper(this);

        /**
         * CRUD Operations
         * */
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
        Log.e("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: " + cn.getCRF_No() + " ,Name: " + cn.getName() + " ,Flag: " + cn.getFlag();
            // Writing Contacts to log
            Log.e("DATABASE ITEMS: ", log);
        }
        if (contacts.size() > 0) {
            single_id = db.getContact("VXL2220000130C0048069");

            String log = "Id: " + single_id.getCRF_No() + " ,Name: " + single_id.getName() + " ,Phone: " + single_id.getMobile_num();
            // Writing Contacts to log
            Log.e("Single value: ", log);
            et_val.setText(single_id.getName());
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteAllContacts();
            }
        });


        pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                single_id.setName(et_val.getText().toString());
                single_id.setFlag(1);
                db.updateContact(single_id);
                int a = db.getContact("VXL2220000130C0048069").getFlag();
                Toast.makeText(Database_Activity.this, "FLAGGG---" + a, Toast.LENGTH_SHORT).show();

            }
        });

        Toast.makeText(Database_Activity.this, "" + db.getContactsCount(), Toast.LENGTH_SHORT).show();

    }

    public void request() {
        String url = "http://192.168.1.61/testing/ezycabledigi/app/index.php/GetRequest";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("accessToken:", response);
                Toast.makeText(Database_Activity.this, "" + response, Toast.LENGTH_LONG).show();
                String code_length = response.substring(0, response.indexOf(' '));
                String content = response.substring(response.indexOf(" ")).replaceFirst("^\\s*", "").replace("#", "");/*.replaceAll("\\s+", "")*/

                ArrayList<String> splits = splitEqually(content, 194);

                Log.e("Length", String.valueOf(splitEqually(content, 194).size()));

                Log.e("CodeLength", code_length);
                Log.e("Content", content);


                for (int i = 0; i < splits.size(); i++) {

                    Log.e("Items  ----" + i, splits.get(i));

                    String CRFno = splits.get(i).substring(0, 25);
                    String name = splits.get(i).substring(26, 49);
                    String address = splits.get(i).substring(50, 95);
                    String billamount = splits.get(i).substring(96, 121);
                    String pendingamount = splits.get(i).substring(122, 133);
                    String mobilenumber = splits.get(i).substring(134, 154);
                    String totalamount = splits.get(i).substring(155, 168);
                    String businessname = splits.get(i).substring(169, 194);

                    // Inserting Contacts
                    Log.d("Insert: ", "Inserting ..");
                    CRFno = CRFno.replaceAll("\\s+", "");
                    name = name.replaceAll("\\s+", "");
                    address = address.replaceAll("\\s+", "");
                    billamount = billamount.replaceAll("\\s+", "");
                    pendingamount = pendingamount.replaceAll("\\s+", "");
                    mobilenumber = mobilenumber.replaceAll("\\s+", "");
                    totalamount = totalamount.replaceAll("\\s+", "");
                    businessname = businessname.replaceAll("\\s+", "");

                    db.addContact(new Contact(CRFno, name, address, billamount, pendingamount, mobilenumber,
                            totalamount, businessname, 0));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("error:", volleyError.toString());
                Toast.makeText(Database_Activity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String machineID = fixedLengthString("lkk!@!#!", 11);
                String version = fixedLengthString("013", 3);
                String command = fixedLengthString(":70", 3);
                String startcount = fixedLengthString("", 3);
                String endcount = fixedLengthString("036", 3);

                params.put("ScrNo", machineID + version + command + startcount + endcount);//280e900002f  lkk!@!#!  L4049C14
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

        };
        AppController.getInstance().addToRequestQueue(postRequest);

    }

    public static ArrayList<String> splitEqually(String text, int size) {
        // Give the list the right capacity to start with. You could use an array
        // instead if you wanted.
        ArrayList<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;
    }

    public static String fixedLengthString(String string, int length) {
        return String.format("%1$" + length + "s", string);
    }


}
