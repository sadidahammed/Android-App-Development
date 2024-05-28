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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class searchActivity extends AppCompatActivity {
    private String selectedLocation;
    FrameLayout searchFregment;
    String VehicleType, Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        VehicleType = getIntent().getStringExtra("VehicleType");
        Email = getIntent().getStringExtra("Email");

        TextView titleText = findViewById(R.id.titleText);
        titleText.setText("Search "+VehicleType+" Vehicle");

        searchFregment = findViewById(R.id.searchFregment);
        Spinner locationSpinner = findViewById(R.id.locationSpinner);


        List<String> locations = new ArrayList<>();
        locations.add("Select Area");
        locations.add("Mirpur");
        locations.add("Dhanmondi");
        locations.add("Nilkhet");
        locations.add("Uttora");
        locations.add("Mutijeel");

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(searchActivity.this, android.R.layout.simple_spinner_item, locations);
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

        ImageButton showListVehicle = findViewById(R.id.showListVehicle);
        showListVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedLocation.equals("Select Area")){
                    Toast.makeText(searchActivity.this, "Please select area first.", Toast.LENGTH_SHORT).show();
                }else{
                    searchVehicleFragment fragment = new searchVehicleFragment();
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
                Intent intent = new Intent(searchActivity.this, home.class);
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