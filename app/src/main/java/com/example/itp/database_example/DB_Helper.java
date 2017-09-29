package com.example.itp.database_example;

/**
 * Created by ITP on 04-Sep-17.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DB_Helper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "offline_database_3";

    // Contacts table name
    private static final String TABLE_OFFLINE_DATA = "offline_data_3";

    // Contacts Table Columns names
    private static final String KEY_CRFNO = "CRF_No";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_BILL_AMT = "bill_amt";
    private static final String KEY_PENDING_AMT = "pending_amt";
    private static final String KEY_MOBILE_NO = "mobile_num";
    private static final String KEY_TOTAL_AMT = "total_amt";
    private static final String KEY_BUSINESS_NAME = "businessname";
    private static final String KEY_FLAG = "flag";

    public DB_Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_OFFLINE_DATA + "("
                + KEY_CRFNO + " TEXT PRIMARY KEY NOT NULL," + KEY_NAME + " TEXT,"
                + KEY_ADDRESS + " TEXT," + KEY_BILL_AMT + " TEXT," + KEY_PENDING_AMT +
                " TEXT," + KEY_MOBILE_NO + " TEXT," + KEY_TOTAL_AMT + " TEXT,"
                + KEY_BUSINESS_NAME + " TEXT," + KEY_FLAG + " INTEGER" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OFFLINE_DATA);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CRFNO, contact.getCRF_No());   // Contact CRF
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_ADDRESS, contact.getAddress()); // Contact KEY_ADDRESS
        values.put(KEY_BILL_AMT, contact.getBill_amt()); // Contact billamt
        values.put(KEY_PENDING_AMT, contact.getPending_amt()); // Contact pendingamt
        values.put(KEY_MOBILE_NO, contact.getMobile_num()); // Contact mobilenum
        values.put(KEY_TOTAL_AMT, contact.getTotal_amt()); // Contact totalamt
        values.put(KEY_BUSINESS_NAME, contact.getBusinessname()); // Contact businessname
        values.put(KEY_BUSINESS_NAME, contact.getBusinessname()); // Contact Flag


        // Inserting Row
        db.insert(TABLE_OFFLINE_DATA, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    Contact getContact(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_OFFLINE_DATA, new String[]{KEY_CRFNO,
                        KEY_NAME, KEY_ADDRESS, KEY_BILL_AMT, KEY_PENDING_AMT,
                        KEY_MOBILE_NO, KEY_TOTAL_AMT, KEY_BUSINESS_NAME, KEY_FLAG}, KEY_CRFNO + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),
                cursor.getInt(8));
        // return contact
        return contact;
    }

    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_OFFLINE_DATA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setCRF_No(cursor.getString(0));
                contact.setName(cursor.getString(1));
                contact.setAddress(cursor.getString(2));
                contact.setBill_amt(cursor.getString(3));
                contact.setPending_amt(cursor.getString(4));
                contact.setMobile_num(cursor.getString(5));
                contact.setTotal_amt(cursor.getString(6));
                contact.setBusinessname(cursor.getString(7));
                contact.setFlag(cursor.getInt(8));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }

    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME, contact.getName());
        values.put(KEY_MOBILE_NO, contact.getMobile_num());
        values.put(KEY_FLAG, contact.getFlag());

        // updating row
        return db.update(TABLE_OFFLINE_DATA, values, KEY_CRFNO + " = ?",
                new String[]{(contact.getCRF_No())});
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_OFFLINE_DATA, KEY_NAME + " = ?",
                new String[]{contact.getName()});
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_OFFLINE_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public void deleteAllContacts(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_OFFLINE_DATA);

    }

}
