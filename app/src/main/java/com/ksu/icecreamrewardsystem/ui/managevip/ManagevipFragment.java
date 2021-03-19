package com.ksu.icecreamrewardsystem.ui.managevip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ksu.icecreamrewardsystem.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.annotation.Nullable;

public class ManagevipFragment extends Fragment {

    private ManagevipViewModel managevipViewModel;
    FirebaseFirestore fStore;
    public String TAG = "manage VIP fragment";
    int index = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        managevipViewModel =
                ViewModelProviders.of(this).get(ManagevipViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_managevip, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);

        fStore = FirebaseFirestore.getInstance();


        fStore.collection("vipCustomers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<VipCustomerModel> list = new ArrayList<>();
                    int index = 1;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        FirestoreVipCustomerModel fireStoreVipCustomer = document.toObject(FirestoreVipCustomerModel.class);

                        VipCustomerModel vipCustomer = new VipCustomerModel();
                        vipCustomer.setFullName(fireStoreVipCustomer.getFirstName() + " " + fireStoreVipCustomer.getLastName());
                        vipCustomer.setVipId(fireStoreVipCustomer.getVipId());
                        vipCustomer.setId(index);
                        vipCustomer.setTotalPoints(fireStoreVipCustomer.getPoints());
                        vipCustomer.setMonthlyPoints(fireStoreVipCustomer.getPoints());

                        list.add(vipCustomer);
                        index++;
                        //getVipDate(document.getId(), index);
                    }
                    Log.d(TAG, list.toString());

                    ListView listView = (ListView) root.findViewById(R.id.listView);
                    CustomAdaptor customAdaptor = new CustomAdaptor(getActivity(), R.layout.list_view_item, list);
                    listView.setAdapter(customAdaptor);


                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });


        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(getActivity(), CreatevipActivity.class));
                Intent intent = new Intent();
                intent.setClass(getActivity(), CreatevipActivity.class);
                getActivity().startActivity(intent);
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return root;
    }


    private void getVipDate(final String vipId, final int index) {
        fStore = FirebaseFirestore.getInstance();

        DocumentReference documentReference = fStore.collection("vipCustomers").document(vipId);
        documentReference.addSnapshotListener((Executor) this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String firstName = documentSnapshot.getString("firstName");
                String lastName = documentSnapshot.getString("lastName");
                int points = documentSnapshot.getDouble("points").intValue();
                String birthDate = documentSnapshot.getString("birthDate");
                String address = documentSnapshot.getString("address");

                VipCustomerModel vipCustomer = new VipCustomerModel();
                vipCustomer.setFullName(firstName + " " + lastName);
                vipCustomer.setVipId(vipId);
                vipCustomer.setId(index);
                vipCustomer.setTotalPoints(points);
                vipCustomer.setMonthlyPoints(0);
            }
        });
    }

}
