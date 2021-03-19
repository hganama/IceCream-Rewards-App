package com.ksu.icecreamrewardsystem.ui.managevip;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ksu.icecreamrewardsystem.R;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreatevipActivity extends AppCompatActivity {

    EditText firstName, lastName, birthdate, address;
    Button createVIP;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createvip);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        firstName = findViewById(R.id.firstNameEditText);
        lastName = findViewById(R.id.lastNameEditText);
        birthdate = findViewById(R.id.birthdate);
        address = findViewById(R.id.address);
        createVIP = findViewById(R.id.button2);
        fStore = FirebaseFirestore.getInstance();

        createVIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstNameString = firstName.getText().toString();
                String lastNameString = lastName.getText().toString();
                String bithdateString = birthdate.getText().toString();
                String addressString = address.getText().toString();

                Boolean validationErrorFound = false;
                if(TextUtils.isEmpty(firstNameString)) {
                    firstName.setError("First name is required!");
                    validationErrorFound = true;
                }

                if(TextUtils.isEmpty(lastNameString)) {
                    lastName.setError("Last name is required!");
                    validationErrorFound = true;
                }

                if(TextUtils.isEmpty(bithdateString)) {
                    birthdate.setError("Birth date is required!");
                    validationErrorFound = true;
                }

                if(TextUtils.isEmpty(addressString)) {
                    address.setError("Address is required!");
                    validationErrorFound = true;
                }

                if(validationErrorFound == false) {
                    final String vipId = generateVipId();

                    DocumentReference documentReference = fStore.collection("vipCustomers").document(vipId);
                    Map<String, Object> vipCustomer = new HashMap<>();
                    //int array[] = {};
                    vipCustomer.put("vipId", vipId);
                    vipCustomer.put("firstName", firstNameString);
                    vipCustomer.put("lastName", lastNameString);
                    vipCustomer.put("birthDate", bithdateString);
                    vipCustomer.put("address", addressString);
                    vipCustomer.put("points", 0);
                    vipCustomer.put("orders", Arrays.asList());

                    documentReference.set(vipCustomer).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("create customer", "Vip customer created");
                            Intent intent = new Intent(getApplicationContext(), VipCardActivity.class);
                            intent.putExtra("vipId", vipId);
                            startActivity(intent);
                        }
                    });
                }
            }
        });

    }

    private String generateVipId() {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddHHmm");

        String id = ft.format(date);
        return id;
    }

}
