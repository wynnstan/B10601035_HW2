package com.example.B10601035_HW2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.B10601035_HW2.data.WaitlistContract;
import com.example.B10601035_HW2.data.WaitlistDbHelper;

public class addGuestActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;

    private EditText newGuestNameEditText;
    private EditText newPartySizeEditText;

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_guest);

        newGuestNameEditText = (EditText) this.findViewById(R.id.person_name_edit_text);
        newPartySizeEditText = (EditText) this.findViewById(R.id.party_size_edit_text);

        WaitlistDbHelper dbHelper = new WaitlistDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
    }

    public void addToWaitlist(View view) {
        if (newGuestNameEditText.getText().length() == 0 || newGuestNameEditText.getText().length() == 0) {
            return;
        }
        int partySize = 1;
        try {
            partySize = Integer.parseInt(newPartySizeEditText.getText().toString());
        } catch (NumberFormatException ex) {
            Log.e(LOG_TAG, "Failed to parse party size text to number: " + ex.getMessage());
        }

        addNewGuest(newGuestNameEditText.getText().toString(), partySize);
        backToHome();
    }

    private void backToHome() {
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
    }

    public void backToHome(View view) {
        backToHome();
    }

    private Cursor getAllGuests() {
        return mDb.query(WaitlistContract.WaitlistEntry.TABLE_NAME,
                null, null, null, null, null,
                WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP);
    }

    private long addNewGuest(String name, int partySize) {
        ContentValues cv = new ContentValues();
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, name);
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE, partySize);
        return mDb.insert(WaitlistContract.WaitlistEntry.TABLE_NAME, null, cv);
    }


}
