package com.example.st1013838.phonebook;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText FirstName, LastName, PhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirstName = findViewById(R.id.FirstName);
        LastName = findViewById(R.id.LastName);
        PhoneNumber = findViewById(R.id.PhoneNumber);
    }

    public void AddButton (View view){
        if (FirstName.getText().toString().equals("") || LastName.getText().toString().equals("") || PhoneNumber.getText().toString().equals("")){
            Toast.makeText(this, "Please make sure that all fields have a value entered in.", Toast.LENGTH_SHORT).show();
        }
        else {
            Contact contact = new Contact(FirstName.getText().toString(), LastName.getText().toString(), PhoneNumber.getText().toString());

            ContentValues values = new ContentValues();
            values.put(MyContentProvider.FIRST_NAME, contact.getFName());
            values.put(MyContentProvider.LAST_NAME, contact.getLName());
            values.put(MyContentProvider.PHONE_NUMBER, contact.getPNumber());

            Uri uri = getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
            Toast.makeText(this, "New contact " + contact.getFName() + " " + contact.getLName() + ", " + contact.getPNumber() + " added", Toast.LENGTH_SHORT).show();

            FirstName.setText("");
            LastName.setText("");
            PhoneNumber.setText("");
        }
    }

    public void DeleteButton (View view){
        String selection = "FirstName = \"" + FirstName.getText().toString() + "\"";
        //String selection2 = "LastName = \"" + LastName.getText().toString() + "\"";

        int result = getContentResolver().delete(MyContentProvider.CONTENT_URI, selection, null);
        if (result > 0){
            FirstName.setText("");
            LastName.setText("");
            PhoneNumber.setText("");
        }
        else
            Toast.makeText(this, "No Match found.", Toast.LENGTH_SHORT).show();
    }

    public void ShowAllButton (View view){
        FirstName.setText("");
        LastName.setText("");
        PhoneNumber.setText("");

        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }

    public void ClearButton (View view){
        FirstName.setText("");
        LastName.setText("");
        PhoneNumber.setText("");
    }
}
