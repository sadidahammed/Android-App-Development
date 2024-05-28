package com.example.get_vehicle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class searchDriver extends AppCompatActivity {
    private String selectedLocation;
    FrameLayout searchFregment;
    String VehicleType, Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_driver);

        Email = getIntent().getStringExtra("Email");

        searchFregment = findViewById(R.id.searchFregment);
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

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(searchDriver.this, android.R.layout.simple_spinner_item, locations);
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

        ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<>(searchDriver.this, android.R.layout.simple_spinner_item, vehicle);
        vehicleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleSpinner.setAdapter(vehicleAdapter);

        vehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                VehicleType = (String) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        ImageButton showListVehicle = findViewById(R.id.showListVehicle);
        showListVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedLocation.equals("Select Area")){
                    Toast.makeText(searchDriver.this, "Please select area first.", Toast.LENGTH_SHORT).show();
                }else{
                    searchDriverFragment fragment = new searchDriverFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("selectedLocation", selectedLocation);
                    bundle.putString("VehicleType", VehicleType);
                    fragment.setArguments(bundle);
                    replace(fragment);
                }

            }
        });

        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(searchDriver.this, profile_menu.class);
                intent.putExtra("Email", Email);
                startActivity(intent);
                finish();
            }
        });
    }

    private void replace(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.searchFregment, fragment);
        fragmentTransaction.commit();
    }
}