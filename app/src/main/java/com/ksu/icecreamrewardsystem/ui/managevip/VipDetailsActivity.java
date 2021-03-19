package com.ksu.icecreamrewardsystem.ui.managevip;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.ksu.icecreamrewardsystem.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class VipDetailsActivity extends AppCompatActivity {

    EditText firstName, lastName, birthdate, address;
    TextView totalPoints, monthlypoints, vipStatus;
    Button vipCardButton, goldStar, firstNameEditButton, lastNameEditButton, birthdateEditButton, addressEditButton;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_details);

        firstName = findViewById(R.id.firstNameEditText);
        lastName = findViewById(R.id.lastNameEditText);
        birthdate = findViewById(R.id.birthdate);
        address = findViewById(R.id.address);
        totalPoints = findViewById(R.id.totalPoints);
        monthlypoints = findViewById(R.id.monthlyPoints);
        vipStatus = findViewById(R.id.vipStatus);

        vipCardButton = findViewById(R.id.vipCardButton);
        goldStar = findViewById(R.id.VIPStar2);
        firstNameEditButton = findViewById(R.id.firstNameEditButton);
        lastNameEditButton = findViewById(R.id.lastnameEditButton);
        birthdateEditButton = findViewById(R.id.birthdateEditButton);
        addressEditButton = findViewById(R.id.addressEditButton);

        fStore = FirebaseFirestore.getInstance();

        firstName.setEnabled(false);
        lastName.setEnabled(false);
        birthdate.setEnabled(false);
        address.setEnabled(false);

        Bundle bundle = getIntent().getExtras();
        final String vipIdString = bundle.getString("vipId");


        DocumentReference documentReference = fStore.collection("vipCustomers").document(vipIdString);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String firstNameString = documentSnapshot.getString("firstName");
                String lastNameString = documentSnapshot.getString("lastName");
                Long pointsLong = (Long)documentSnapshot.get("points");
                int points = pointsLong.intValue();

                firstName.setText(documentSnapshot.getString("firstName"));
                lastName.setText(documentSnapshot.getString("lastName"));
                birthdate.setText(documentSnapshot.getString("birthDate"));
                address.setText(documentSnapshot.getString("address"));
                totalPoints.setText(String.valueOf(points));
                monthlypoints.setText(String.valueOf(points));
                if(points > 1000) {
                    vipStatus.setText("Gold");
                    goldStar.setVisibility(View.VISIBLE);
                } else {
                    vipStatus.setText("VIP");
                    goldStar.setVisibility(View.GONE);
                }
                //vipId.setText(documentSnapshot.getString("vipId"));
            }
        });


        vipCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VipCardActivity.class);
                intent.putExtra("vipId", vipIdString);
                startActivity(intent);
            }
        });

        firstNameEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstName.isEnabled()) {
                    firstName.setEnabled(false);
                    firstNameEditButton.setBackgroundResource(R.drawable.ic_edit_black);
                    Map<String, Object> vipCustomer = new HashMap<>();

                    vipCustomer.put("vipId", vipIdString);
                    vipCustomer.put("firstName", firstName.getText().toString());
                    vipCustomer.put("lastName", lastName.getText().toString());
                    vipCustomer.put("birthDate", birthdate.getText().toString());
                    vipCustomer.put("address", address.getText().toString());
                    vipCustomer.put("points", Integer.valueOf(totalPoints.getText().toString()));
                    fStore.collection("vipCustomers").document(vipIdString).update(vipCustomer);
                } else {
                    firstName.setEnabled(true);
                    firstNameEditButton.setBackgroundResource(R.drawable.ic_check);
                }
            }
        });

        lastNameEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastName.isEnabled()) {
                    lastName.setEnabled(false);
                    lastNameEditButton.setBackgroundResource(R.drawable.ic_edit_black);

                    Map<String, Object> vipCustomer = new HashMap<>();

                    vipCustomer.put("vipId", vipIdString);
                    vipCustomer.put("firstName", firstName.getText().toString());
                    vipCustomer.put("lastName", lastName.getText().toString());
                    vipCustomer.put("birthDate", birthdate.getText().toString());
                    vipCustomer.put("address", address.getText().toString());
                    vipCustomer.put("points", Integer.valueOf(totalPoints.getText().toString()));
                    fStore.collection("vipCustomers").document(vipIdString).update(vipCustomer);
                } else {
                    lastName.setEnabled(true);
                    lastNameEditButton.setBackgroundResource(R.drawable.ic_check);
                }
            }
        });

        birthdateEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(birthdate.isEnabled()) {
                    birthdate.setEnabled(false);
                    birthdateEditButton.setBackgroundResource(R.drawable.ic_edit_black);

                    Map<String, Object> vipCustomer = new HashMap<>();

                    vipCustomer.put("vipId", vipIdString);
                    vipCustomer.put("firstName", firstName.getText().toString());
                    vipCustomer.put("lastName", lastName.getText().toString());
                    vipCustomer.put("birthDate", birthdate.getText().toString());
                    vipCustomer.put("address", address.getText().toString());
                    vipCustomer.put("points", Integer.valueOf(totalPoints.getText().toString()));
                    fStore.collection("vipCustomers").document(vipIdString).update(vipCustomer);
                } else {
                    birthdate.setEnabled(true);
                    birthdateEditButton.setBackgroundResource(R.drawable.ic_check);
                }
            }
        });

        addressEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(address.isEnabled()) {
                    address.setEnabled(false);
                    addressEditButton.setBackgroundResource(R.drawable.ic_edit_black);

                    Map<String, Object> vipCustomer = new HashMap<>();

                    vipCustomer.put("vipId", vipIdString);
                    vipCustomer.put("firstName", firstName.getText().toString());
                    vipCustomer.put("lastName", lastName.getText().toString());
                    vipCustomer.put("birthDate", birthdate.getText().toString());
                    vipCustomer.put("address", address.getText().toString());
                    vipCustomer.put("points", Integer.valueOf(totalPoints.getText().toString()));
                    fStore.collection("vipCustomers").document(vipIdString).update(vipCustomer);
                } else {
                    address.setEnabled(true);
                    addressEditButton.setBackgroundResource(R.drawable.ic_check);
                }
            }
        });



    }
}
