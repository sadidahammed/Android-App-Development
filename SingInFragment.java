package com.example.get_vehicle;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SingInFragment extends Fragment {
    Button loginBtn_Obj;
    String name;
    String Email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sing_in, container, false);
        loginBtn_Obj = view.findViewById(R.id.loginBtn);

        loginBtn_Obj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText emailEditText = view.findViewById(R.id.emailEditTextId);
                EditText passEditText = view.findViewById(R.id.passEditTextId);
                TextView password_Waring_obj = view.findViewById(R.id.password_warning);
                TextView email_Waring_obj = view.findViewById(R.id.email_warning);

                String fetch_email = emailEditText.getText().toString();
                String fetch_pass = passEditText.getText().toString();



                if(fetch_email.equals("") || !fetch_email.endsWith("@gmail.com") ){
                    email_Waring_obj.setText("Please enter a valid email address.");
                } else if (fetch_pass.length() < 8) {
                    email_Waring_obj.setText("");
                    password_Waring_obj.setText("Password should be at least 8 character.");
                } else {
                    password_Waring_obj.setText("");
                    email_Waring_obj.setText("");
                    String email = fetch_email.replace(".", "_");

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference rootRef = database.getReference();

                    rootRef.child("Owner_Account").orderByKey().equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String password = dataSnapshot.child(email).child("password").getValue(String.class);
                                if (password.equals(fetch_pass)) {
                                    Toast.makeText(getContext(), "Welcome "+dataSnapshot.child(email).child("name").getValue(String.class), Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getContext(), home.class);
                                    intent.putExtra("Email", email);
                                    startActivity(intent);
                                } else {
                                    password_Waring_obj.setText("Doesn't match password.");
                                }
                            } else {
                                email_Waring_obj.setText("This email don't have any account.");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getContext(), "Error retrieving data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });


        TextView forgotten = view.findViewById(R.id.forgotten);
        forgotten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        return view;
    }

}