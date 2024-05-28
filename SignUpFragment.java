package com.example.get_vehicle;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SignUpFragment extends Fragment {



    private String gender;
    private String selectedLocation;
    private String selectedVehicle;
    private String experience = "Empty";
    private String licence = "Empty";
    private String  income = "0";
    private int count = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);

        Button createAccount_Obj = view.findViewById(R.id.createAccountBtn);
        RadioGroup genderradioGroup = view.findViewById(R.id.gender_radiogrp_ID);
        RadioGroup experienceRadioGroup = view.findViewById(R.id.experience_radiogrp_ID);
        RadioGroup licenceRadioGroup = view.findViewById(R.id.licenses_radiogrp_ID);

        Spinner vehicleSpinner = view.findViewById(R.id.vehicleSpinner);
        Spinner locationSpinner = view.findViewById(R.id.locationSpinner);


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
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, locations);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Create ArrayAdapter for vehicle
        ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, vehicle);
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
        genderradioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = view.findViewById(checkedId);
                gender = radioButton.getText().toString();
            }
        });

        experienceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = view.findViewById(checkedId);
                experience = radioButton.getText().toString();
            }
        });
        licenceRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = view.findViewById(checkedId);
                licence = radioButton.getText().toString();
            }
        });


        createAccount_Obj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText create_first_name_obj = view.findViewById(R.id.create_name1Id);
                EditText create_last_name_obj = view.findViewById(R.id.create_name2Id);
                EditText create_email_obj = view.findViewById(R.id.create_emailId);
                EditText create_pass_obj = view.findViewById(R.id.create_pass_ID);
                EditText create_pass_conf_obj = view.findViewById(R.id.create_conf_pass_ID);
                EditText create_phone_Obj = view.findViewById(R.id.create_phone_ID);
                EditText incomeID = view.findViewById(R.id.incomeID);

                TextView phone_Waring_obj = view.findViewById(R.id.create_phone_warning_ID);
                TextView password_Waring_obj = view.findViewById(R.id.create_pass_warning_ID);
                TextView conf_password_Waring_obj = view.findViewById(R.id.create_conf_pass_warning_ID);

                String fetch_email = create_email_obj.getText().toString();
                String fetch_pass1 = create_pass_obj.getText().toString();
                String fetch_pass2 = create_pass_conf_obj.getText().toString();

                if (create_first_name_obj.getText().toString().equals("") || create_last_name_obj.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Please provide your name.", Toast.LENGTH_SHORT).show();
                } else if (fetch_email.equals("") || !fetch_email.endsWith("@gmail.com")) {
                    Toast.makeText(getContext(), "Please provide valid email.", Toast.LENGTH_SHORT).show();
                } else if (create_phone_Obj.getText().toString().length() > 15 || create_phone_Obj.getText().toString().length() < 15) {
                    phone_Waring_obj.setText("Provided phone number is not valid.");
                } else if (fetch_pass1.length() < 8 || fetch_pass2.length() < 8) {
                    phone_Waring_obj.setText("");
                    password_Waring_obj.setText("Password should be at least 8 character.");
                    conf_password_Waring_obj.setText("Password should be at least 8 character.");
                } else if (selectedLocation.equals("Select Area")) {
                    Toast.makeText(getContext(), "Please select area.", Toast.LENGTH_SHORT).show();
                } else if (!fetch_pass1.equals(fetch_pass2)) {
                    password_Waring_obj.setText("");
                    conf_password_Waring_obj.setText("Confirm password doesn't match.");
                } else if (fetch_pass1.equals(fetch_pass2)) {

                    String name = create_first_name_obj.getText().toString() + " "+ create_last_name_obj.getText().toString();
                    String phone = create_phone_Obj.getText().toString();
                    String password = fetch_pass1;
                    income = incomeID.getText().toString();
                    String email = fetch_email.replace(".", "_");



                    if(!experience.equals("Empty") && !licence.equals("Empty") && !income.equals("0")){
                        driver_data_Insert collect = new driver_data_Insert(name,password, gender, phone,email,experience,licence,income);
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference root = db.getReference("Driver_Account").child(selectedLocation).child(selectedVehicle);
                        root.child(email).setValue(collect);

                        Owner_Data_Insert collect1 = new Owner_Data_Insert(gender,selectedLocation,name,password,phone);
                        FirebaseDatabase db1 = FirebaseDatabase.getInstance();
                        DatabaseReference root1 = db1.getReference("Owner_Account");
                        root1.child(email).setValue(collect1);

                        Toast.makeText(getContext(), name+" log in as a driver & owner.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), home.class);
                        startActivity(intent);
                    }
                    else {
                        Owner_Data_Insert collect = new Owner_Data_Insert(gender,selectedLocation,name,password,phone);
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference root = db.getReference("Owner_Account");
                        root.child(email).setValue(collect);

                        Toast.makeText(getContext(), name+" log in as a owner", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), home.class);
                        startActivity(intent);
                    }



                }
            }
        });

        return view;
    }

}