package com.example.get_vehicle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class authentication extends AppCompatActivity {

    Button signinBtn, signupBtn;
    FrameLayout authenticationFregment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        signinBtn = findViewById(R.id.signinBtn);
        signupBtn = findViewById(R.id.signupBtn);
        authenticationFregment = findViewById(R.id.authenticationFregment);

        replace(new SingInFragment());
        signinBtn.setBackgroundColor(getResources().getColor(R.color.colorSelected));


        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new SingInFragment());
                signinBtn.setBackgroundColor(getResources().getColor(R.color.colorSelected));
                signupBtn.setBackgroundColor(getResources().getColor(R.color.colorUnselected));
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replace(new SignUpFragment());
                signupBtn.setBackgroundColor(getResources().getColor(R.color.colorSelected));
                signinBtn.setBackgroundColor(getResources().getColor(R.color.colorUnselected));
            }
        });

    }
    private void replace(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.authenticationFregment, fragment);
        fragmentTransaction.commit();
    }
}