package com.example.get_vehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class home extends AppCompatActivity {

    String Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
         Email = intent.getStringExtra("Email");

        ImageButton settingBtn = findViewById(R.id.setting_Btn);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, profile_menu.class);
                intent.putExtra("Email", Email);
                startActivity(intent);
                finish();
            }
        });
    }

    public void SearchFunction(View view) {
        if(view.getId() == R.id.CarVehicle){
            Intent intent = new Intent(home.this, searchActivity.class);
            intent.putExtra("VehicleType", "Car");
            intent.putExtra("Email", Email);
            startActivity(intent);
        } else if(view.getId() == R.id.TruckVehicle){
            Intent intent = new Intent(home.this, searchActivity.class);
            intent.putExtra("VehicleType", "Truck");
            intent.putExtra("Email", Email);
            startActivity(intent);
        } else if(view.getId() == R.id.AutoCNGVehicle){
            Intent intent = new Intent(home.this, searchActivity.class);
            intent.putExtra("VehicleType", "Auto CNG");
            intent.putExtra("Email", Email);
            startActivity(intent);
        } else if(view.getId() == R.id.autoRiskshawVehicle){
            Intent intent = new Intent(home.this, searchActivity.class);
            intent.putExtra("VehicleType", "Auto Rickshaw");
            intent.putExtra("Email", Email);
            startActivity(intent);
        } else {
            Intent intent = new Intent(home.this, searchActivity.class);
            intent.putExtra("VehicleType", "Mini Truck");
            intent.putExtra("Email", Email);
            startActivity(intent);
        }
    }

}