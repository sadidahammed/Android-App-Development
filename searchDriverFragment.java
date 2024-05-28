package com.example.get_vehicle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class searchDriverFragment extends Fragment {
    private String selectedLocation;
    private String VehicleType;
    List<String> VehicleInfoList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search_driver, container, false);
        Bundle args = getArguments();
        if (args != null) {
            selectedLocation = args.getString("selectedLocation");
            VehicleType = args.getString("VehicleType");
        }

        TextView ViewinfoFragment = view.findViewById(R.id.ViewinfoFragment);
        ViewinfoFragment.setText("Vehicle: " + VehicleType + "\nArea: " + selectedLocation);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Driver_Account").child(selectedLocation).child(VehicleType);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                VehicleInfoList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String name = snapshot.child("name").getValue(String.class);
                    String phone = snapshot.child("phone").getValue(String.class);
                    String experience = snapshot.child("experience").getValue(String.class);
                    String income = snapshot.child("income").getValue(String.class);
                    String licence = snapshot.child("licence").getValue(String.class);

                    VehicleInfoList.add(  "Name                         : " + name +
                                        "\nDriving Experience   : " + experience +
                                        "\nDriving Licence         : " + licence +
                                        "\nLast month income  : " + income +
                                        "\nPhone number          : " + phone );
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, VehicleInfoList);
        ListView listView = view.findViewById(R.id.listView);
        listView.setAdapter(adapter);


        return view;
    }
}