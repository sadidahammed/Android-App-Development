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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class removeVehicle extends AppCompatActivity {
    private String selectedLocation;
    private String selectedVehicle;
    private String password, Email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_vehicle);

        Intent intent = getIntent();
        Email = intent.getStringExtra("Email");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = database.getReference();

        rootRef.child("Owner_Account").orderByKey().equalTo(Email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    password = dataSnapshot.child(Email).child("password").getValue(String.class);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Spinner locationSpinner = findViewById(R.id.locationSpinner);
        Spinner vehicleSpinner = findViewById(R.id.vehicleSpinner);

        List<String> locations = new ArrayList<>();
        locations.add("Select Area");
        locations.add("Mirpur");
        locations.add("Dhanmondi");
        locations.add("Nilkhet");
        locations.add("Uttora");
        locations.add("Mutijeel");

        List<String> vehicle = new ArrayList<>();
        vehicle.add("Select Vehicle");
        vehicle.add("Car");
        vehicle.add("Truck");
        vehicle.add("Auto CNG");
        vehicle.add("Auto Rickshaw");
        vehicle.add("Mini Truck");

        // Create ArrayAdapter for locations
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(removeVehicle.this, android.R.layout.simple_spinner_item, locations);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create ArrayAdapter for vehicle
        ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<>(removeVehicle.this, android.R.layout.simple_spinner_item, vehicle);
        vehicleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        locationSpinner.setAdapter(locationAdapter);
        vehicleSpinner.setAdapter(vehicleAdapter);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLocation = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedVehicle = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button removeVehicle = findViewById(R.id.removeVehicle);
        removeVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText passEditText = findViewById(R.id.passEditTextId);
                EditText registrationNumber = findViewById(R.id.registrationNumber);
                TextView password_Waring_obj = findViewById(R.id.password_warning);

                String RegNumber = registrationNumber.getText().toString();
                String fetch_pass = passEditText.getText().toString();

                if (RegNumber.equals("")) {
                    Toast.makeText(removeVehicle.this, "Please provide registration number.", Toast.LENGTH_SHORT).show();
                }else if(selectedVehicle.equals("Select Vehicle")){
                    Toast.makeText(removeVehicle.this, "Please Select Vehicle Category.", Toast.LENGTH_SHORT).show();
                }else if(selectedLocation.equals("Select Area")){
                    Toast.makeText(removeVehicle.this, "Please Select Area.", Toast.LENGTH_SHORT).show();
                } else if (fetch_pass.length() < 8) {
                    password_Waring_obj.setText("Password should be at least 8 character.");
                }else{
                    if(fetch_pass.equals(password)){
                        password_Waring_obj.setText("");
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("Add_Vehicle");
                        DatabaseReference nodeRef = rootRef.child(selectedVehicle).child(selectedLocation).child(RegNumber);

                        nodeRef.removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Data successfully deleted
                                        Toast.makeText(removeVehicle.this, "Remove vehicle successfully.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(removeVehicle.this, profile_menu.class);
                                        intent.putExtra("Email", Email);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Failed to delete data
                                        Toast.makeText(removeVehicle.this, "Remove vehicle fail.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }else{
                        password_Waring_obj.setText("Password is incorrect.");
                    }



                }
            }
        });

        ImageView backBtn = findViewById(R.id.back_arrow);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(removeVehicle.this, profile_menu.class);
                intent.putExtra("Email", Email);
                startActivity(intent);
                finish();
            }
        });
    }


}