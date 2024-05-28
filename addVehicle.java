package com.example.get_vehicle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class addVehicle extends AppCompatActivity {

    private String selectedLocation;
    private String selectedVehicle;
    private String vehiclePapers , firstOwnerInfo;
    String phone, name;
    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        Intent intent = getIntent();
        Email = intent.getStringExtra("Email");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = database.getReference();
        rootRef.child("Owner_Account").orderByKey().equalTo(Email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    phone = dataSnapshot.child(Email).child("phone").getValue(String.class);
                    name = dataSnapshot.child(Email).child("name").getValue(String.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Spinner vehicleSpinner = findViewById(R.id.vehicleSpinner);
        Spinner locationSpinner = findViewById(R.id.locationSpinner);


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
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(addVehicle.this, android.R.layout.simple_spinner_item, locations);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create ArrayAdapter for vehicle
        ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<>(addVehicle.this, android.R.layout.simple_spinner_item, vehicle);
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

        RadioGroup vehiclePapersRadioGroupObj = findViewById(R.id.vehiclePapersRadioGroup);
        vehiclePapersRadioGroupObj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                vehiclePapers = radioButton.getText().toString();
            }
        });

        RadioGroup firstOwnerRadioGroupObj = findViewById(R.id.firstOwnerRadioGroup);
        firstOwnerRadioGroupObj.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                firstOwnerInfo = radioButton.getText().toString();
            }
        });

        Button postVehicleID = findViewById(R.id.postVehicleID);
        postVehicleID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText registrationNumber = findViewById(R.id.registrationNumber);
                EditText vehicleAge = findViewById(R.id.vehicleAge);
                String RegNumber = registrationNumber.getText().toString();
                String Vehicleage = vehicleAge.getText().toString();

                if(selectedVehicle.equals("Select Vehicle")){
                    Toast.makeText(addVehicle.this, "Please Select Vehicle Category.", Toast.LENGTH_SHORT).show();
                }else if(selectedLocation.equals("Select Area")){
                    Toast.makeText(addVehicle.this, "Please Select Area.", Toast.LENGTH_SHORT).show();
                } else if (RegNumber.equals("")) {
                    Toast.makeText(addVehicle.this, "Please provide registration number.", Toast.LENGTH_SHORT).show();
                } else if (Vehicleage.equals("")) {
                    Toast.makeText(addVehicle.this, "Please provide the age of your vehicle.", Toast.LENGTH_SHORT).show();
                } else{
                    Add_Vehicle_Information_Insert collect = new Add_Vehicle_Information_Insert(Email,phone,vehiclePapers,firstOwnerInfo,Vehicleage,name);
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference root = db.getReference("Add_Vehicle").child(selectedVehicle).child(selectedLocation);
                    root.child(RegNumber).setValue(collect);

                    Toast.makeText(addVehicle.this, "Vehicle add complete.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(addVehicle.this, home.class);
                    intent.putExtra("Email", Email);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    public void BackFunction(View view) {
        Intent intent = new Intent(addVehicle.this, profile_menu.class);
        intent.putExtra("Email", Email);
        startActivity(intent);
        finish();
    }
}