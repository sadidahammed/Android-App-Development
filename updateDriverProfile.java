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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class updateDriverProfile extends AppCompatActivity {
    private String selectedOldVehicle;
    private String experience ;
    private String licence, income="0", password, Email, Location, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_driver_profile);

        Intent intent = getIntent();
        Email = intent.getStringExtra("Email");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = database.getReference("Owner_Account");

        rootRef.orderByKey().equalTo(Email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Location = snapshot.child("location").getValue(String.class);
                        Password = snapshot.child("password").getValue(String.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });

        Spinner vehicleOldSpinner = findViewById(R.id.vehicleOldSpinner);


        List<String> vehicle = new ArrayList<>();
        vehicle.add("Select Vehicle");
        vehicle.add("Car");
        vehicle.add("Truck");
        vehicle.add("Auto CNG");
        vehicle.add("Auto Rickshaw");
        vehicle.add("Mini Truck");

        //Old Vehicle Spinner
        ArrayAdapter<String> vehicleOldAdapter = new ArrayAdapter<>(updateDriverProfile.this, android.R.layout.simple_spinner_item, vehicle);
        vehicleOldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        vehicleOldSpinner.setAdapter(vehicleOldAdapter);

        vehicleOldSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOldVehicle = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        RadioGroup experienceRadioGroup = findViewById(R.id.experience_radiogrp_ID);
        RadioGroup licenceRadioGroup = findViewById(R.id.licenses_radiogrp_ID);

        experienceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                experience = radioButton.getText().toString();
            }
        });
        licenceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                licence = radioButton.getText().toString();
            }
        });
        EditText incomeID = findViewById(R.id.incomeID);
        income = incomeID.getText().toString();

        Button saveUpdate = findViewById(R.id.saveUpdate);
        saveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText passEditText = findViewById(R.id.password);
                password = passEditText.getText().toString();

                EditText create_pass_obj = findViewById(R.id.create_pass_ID);
                TextView password_Waring_obj = findViewById(R.id.create_pass_warning_ID);

                if (password.length() > 7) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference rootRef = database.getReference("Driver_Account").child(Location).child(selectedOldVehicle).child(Email);

                    rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                if (Password.equals(password)) {
                                    if (!experience.isEmpty()) {
                                        rootRef.child("experience").setValue(experience);
                                    }
                                    if (!licence.isEmpty()) {
                                        rootRef.child("licence").setValue(licence);
                                    }
                                    // Retrieve income value dynamically
                                    EditText incomeID = findViewById(R.id.incomeID);
                                    income = incomeID.getText().toString();
                                    if (!income.equals("0")) {
                                        rootRef.child("income").setValue(income);
                                    }
                                    Toast.makeText(updateDriverProfile.this, "Update complete.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(updateDriverProfile.this, home.class);
                                    intent.putExtra("Email", Email);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(updateDriverProfile.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
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
                    password_Waring_obj2.setText("Password should be at least 8 characters.");
                }
                // Handle the case where none of the radio buttons for experience or license are selected
                if (experience == null || licence == null) {
                    Toast.makeText(updateDriverProfile.this, "Please select experience and license.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(updateDriverProfile.this, profile_menu.class);
                intent.putExtra("Email", Email);
                startActivity(intent);
                finish();
            }
        });


    }
}