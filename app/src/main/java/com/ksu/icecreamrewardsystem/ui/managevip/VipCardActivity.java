package com.ksu.icecreamrewardsystem.ui.managevip;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.ksu.icecreamrewardsystem.LoginActivity;
import com.ksu.icecreamrewardsystem.MainActivity;
import com.ksu.icecreamrewardsystem.R;
import com.ksu.icecreamrewardsystem.ui.home.HomeFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import javax.annotation.Nullable;

public class VipCardActivity extends AppCompatActivity {

    TextView customerName, vipId;
    Button goBack;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_card);

        customerName = findViewById(R.id.customerName);
        vipId = findViewById(R.id.vipId);
        goBack = findViewById(R.id.goBack);
        fStore = FirebaseFirestore.getInstance();

        customerName.setEnabled(false);

        Bundle bundle = getIntent().getExtras();
        final String vipIdString = bundle.getString("vipId");

        DocumentReference documentReference = fStore.collection("vipCustomers").document(vipIdString);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String firstName = documentSnapshot.getString("firstName");
                String lastName = documentSnapshot.getString("lastName");

                customerName.setText(firstName + " " + lastName);
                vipId.setText(documentSnapshot.getString("vipId"));
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(VipCardActivity.this, HomeFragment.class);
                startActivity(i);
            }
        });



    }
}
