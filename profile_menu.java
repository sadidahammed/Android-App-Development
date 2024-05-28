package com.example.get_vehicle;

import static androidx.appcompat.app.AlertDialog.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Random;

public class profile_menu extends AppCompatActivity {
    private String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_menu);

        TextView account_name_show_ID = findViewById(R.id.account_name_show_ID);
        TextView account_email_show_ID = findViewById(R.id.account_email_show_ID);

        Intent intent = getIntent();
        Email = intent.getStringExtra("Email");
        String email = Email.replace("_", ".");
        account_email_show_ID.setText(email);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference rootRef = database.getReference();

        rootRef.child("Owner_Account").orderByKey().equalTo(Email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child(Email).child("name").getValue(String.class);
                    account_name_show_ID.setText(name);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void OwnerFunction(View view) {

        if(view.getId()==R.id.add_vehicle_icon_ID){
            Intent intent = new Intent(profile_menu.this, addVehicle.class);
            intent.putExtra("Email", Email);
            startActivity(intent);
        }else if(view.getId() == R.id.remove_vehicle_icon_ID){
            Intent intent = new Intent(profile_menu.this, removeVehicle.class);
            intent.putExtra("Email", Email);
            startActivity(intent);
        } else if (view.getId()==R.id.updateDriverProfile) {
            Intent intent = new Intent(profile_menu.this, updateDriverProfile.class);
            intent.putExtra("Email", Email);
            startActivity(intent);
        }else if(view.getId()==R.id.owner_update_profile){
            Intent intent = new Intent(profile_menu.this, update_profile_owner.class);
            intent.putExtra("Email", Email);
            startActivity(intent);
        } else if (view.getId() == R.id.searchVehicle) {
            Intent intent = new Intent(profile_menu.this, searchFromMenu.class);
            intent.putExtra("Email", Email);
            startActivity(intent);
        } else if (view.getId() == R.id.SearchDriver) {
            Intent intent = new Intent(profile_menu.this, searchDriver.class);
            intent.putExtra("Email", Email);
            startActivity(intent);
        }  else if(view.getId() == R.id.logOut){
            showLogoutConfirmationDialog();
        }
    }

    public void Functionality(View view) {
        if(view.getId()==R.id.backBtn){
            Intent intent = new Intent(profile_menu.this, home.class);
            intent.putExtra("Email", Email);
            startActivity(intent);
            finish();
        }
    }
    private void showLogoutConfirmationDialog() {
        Builder builder = new Builder(this);
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform logout operation here
                Intent intent = new Intent(profile_menu.this, authentication.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void TearmsAndConfitionFunction(View view) {

        if(view.getId() == R.id.feedbackBtn){
            showFeedbackDialog();
        } else if (view.getId() == R.id.privacyPolicy) {
            showPrivacyPolicyPopup("PrivacyPolicy");
        }else{
            showPrivacyPolicyPopup("Contact");
        }


    }

    private void showFeedbackDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.feedback_popup, null);
        builder.setView(dialogView);

        EditText feedbackEditText = dialogView.findViewById(R.id.feedbackEditText);
        Button submitButton = dialogView.findViewById(R.id.submitButton);

        android.app.AlertDialog alertDialog = builder.create();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback = feedbackEditText.getText().toString();
                if (feedback.isEmpty()) {
                    Toast.makeText(profile_menu.this, "Please enter your feedback", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle feedback submission here
                    // For example, send it to a server or save locally
                    Random random = new Random();
                    int randomNumber = 1000 + random.nextInt(9000);

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference root = db.getReference("Feedback").child(Email);
                    root.child(String.valueOf(randomNumber)).setValue(feedback);
                    Toast.makeText(profile_menu.this, "Feedback submitted", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });

        alertDialog.show();
    }

    private void showPrivacyPolicyPopup(String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.privacy_policy_popup, null);
        builder.setView(dialogView);

        if(key.equals("PrivacyPolicy")){
            TextView privacyPolicyText = dialogView.findViewById(R.id.privacyPolicyText);
            privacyPolicyText.setText(getString(R.string.privacy_policy));
        }else{
            TextView privacyPolicyText = dialogView.findViewById(R.id.privacyPolicyText);
            privacyPolicyText.setText(getString(R.string.Contact));
        }
        Button closeButton = dialogView.findViewById(R.id.closeButton);
        AlertDialog alertDialog = builder.create();

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
}