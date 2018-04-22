package com.example.st1013838.phonebook;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Currency;

public class DisplayActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        String projection[] = {MyContentProvider.FIRST_NAME, MyContentProvider.LAST_NAME, MyContentProvider.PHONE_NUMBER};
        Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI, projection, null, null);

        int count = cursor.getCount();
        cursor.moveToFirst();

        //For Title Row of Table
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setVerticalScrollBarEnabled(true);
        TableRow row = new TableRow(this);

        TextView FName, LName, PNumber;
        FName = new TextView(this);
        LName = new TextView(this);
        PNumber = new TextView(this);

        FName.setText("First Name");
        FName.setTextColor(Color.RED);
        FName.setTypeface(null, Typeface.BOLD);
        FName.setPadding(20,20,20,20);

        LName.setText("Last Name");
        LName.setTextColor(Color.RED);
        LName.setTypeface(null, Typeface.BOLD);
        LName.setPadding(20,20,20,20);

        PNumber.setText("Phone Number");
        PNumber.setTextColor(Color.RED);
        PNumber.setTypeface(null, Typeface.BOLD);
        PNumber.setPadding(20,20,20,20);

        row.addView(FName);
        row.addView(LName);
        row.addView(PNumber);
        tableLayout.addView(row);

        //For rest of table
        for (int i = 0; i < count; i++) {
            row = new TableRow(this);
            FName = new TextView(this);
            LName = new TextView(this);
            PNumber = new TextView(this);

            FName.setText("" + cursor.getString(0));
            FName.setTextColor(Color.BLACK);
            FName.setPadding(20,20,20,20);

            LName.setText("" + cursor.getString(1));
            LName.setTextColor(Color.BLACK);
            LName.setPadding(20,20,20,20);

            PNumber.setText("" + cursor.getString(2));
            PNumber.setTextColor(Color.BLACK);
            PNumber.setPadding(20,20,20,20);

            row.addView(FName);
            row.addView(LName);
            row.addView(PNumber);
            tableLayout.addView(row);

            cursor.moveToNext();
        }

        setContentView(tableLayout);
    }
}
