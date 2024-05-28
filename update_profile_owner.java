package com.example.get_vehicle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class update_profile_owner extends AppCompatActivity {

    String update_name, selectedLocation;
    String Update_phone;
    String update_pass;
    String old_pass, Email, gender, location, name, password, phone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_owner);

        Intent intent = getIntent();
        Email = intent.getStringExtra("Email");

        Spinner locationSpinner = findViewById(R.id.locationSpinner);
        List<String> locations = new ArrayList<>();
        locations.add("Select Area");
        locations.add("Mirpur");
        locations.add("Dhanmondi");
        locations.add("Nilkhet");
        locations.add("Uttora");
        locations.add("Mutijeel");

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(update_profile_owner.this, android.R.layout.simple_spinner_item, locations);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(locationAdapter);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLocation = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button saveUpdate = findViewById(R.id.saveUpdate);
        saveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText update_name_ID = findViewById(R.id.update_name_ID);
                update_name = update_name_ID.getText().toString();

                EditText update_phone_ID = findViewById(R.id.update_phone_ID);
                Update_phone = update_phone_ID.getText().toString();

                EditText passEditText = findViewById(R.id.passEditTextId);
                old_pass = passEditText.getText().toString();



                EditText create_pass_obj = findViewById(R.id.create_pass_ID);
                TextView password_Waring_obj = findViewById(R.id.create_pass_warning_ID);

                update_pass = create_pass_obj.getText().toString();

                if (old_pass.length() > 7) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference rootRef = database.getReference("Owner_Account").child(Email);

                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                password = dataSnapshot.child("password").getValue(String.class);
                                gender = dataSnapshot.child("gender").getValue(String.class);
                                location = dataSnapshot.child("location").getValue(String.class);
                                name = dataSnapshot.child("name").getValue(String.class);
                                phone = dataSnapshot.child("phone").getValue(String.class);

                                if (old_pass.equals(password)) {
                                    if (!update_name.isEmpty()) {
                                        rootRef.child("name").setValue(update_name);
                                        update_name_ID.setText("");
                                    }
                                    if (!Update_phone.isEmpty()) {
                                        rootRef.child("phone").setValue(Update_phone);
                                        update_phone_ID.setText("");
                                    }
                                    if (!selectedLocation.equals("Select Area")) {
                                        rootRef.child("location").setValue(selectedLocation);
                                    }
                                    if (!update_pass.isEmpty()) {
                                        if(update_pass.length() <8){
                                            password_Waring_obj.setText("Password should be at least 8 character.");
                                        }else{
                                            rootRef.child("password").setValue(update_pass);
                                            create_pass_obj.setText("");
                                        }
                                    }
                                    Toast.makeText(update_profile_owner.this, "Update complete.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(update_profile_owner.this, home.class);
                                    intent.putExtra("Email", Email);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(update_profile_owner.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle errors here
                        }
                    });
                } else {
                    TextView password_Waring_obj2 = findViewById(R.id.password_warning);
                    password_Waring_obj2.setText("Password should be at least 8 character.");
                }
            }
        });

        ImageButton account_Btn = findViewById(R.id.account_Btn);
        account_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(update_profile_owner.this, profile_menu.class);
                intent.putExtra("Email", Email);
                startActivity(intent);
                finish();
            }
        });
    }
}