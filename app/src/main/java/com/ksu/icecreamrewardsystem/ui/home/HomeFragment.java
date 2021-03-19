package com.ksu.icecreamrewardsystem.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.ksu.icecreamrewardsystem.LoginActivity;
import com.ksu.icecreamrewardsystem.MainActivity;
import com.ksu.icecreamrewardsystem.R;
import com.ksu.icecreamrewardsystem.ui.managevip.CustomAdaptor;
import com.ksu.icecreamrewardsystem.ui.managevip.VipCustomerModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    Button icecreamButton, yogurtButton, doneButton, placeOrderButton;
    ListView ordersListView;
    EditText vipIdEditText;
    FirebaseFirestore fStore;
    TextView fullName, status, totalPoints;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final List<OrderModel> ordersList = new ArrayList<>();

        icecreamButton = root.findViewById(R.id.icecreamButton);
        yogurtButton = root.findViewById(R.id.frozenYogurtButton);
        ordersListView = root.findViewById(R.id.ordersListView);
        vipIdEditText = root.findViewById(R.id.vipIdHome);
        doneButton = root.findViewById(R.id.doneButtonHome);
        fullName = root.findViewById(R.id.fullNameHome);
        status = root.findViewById(R.id.statusHome);
        totalPoints = root.findViewById(R.id.totalPointsHome);
        placeOrderButton = root.findViewById(R.id.placeOrderButton);

        fStore = FirebaseFirestore.getInstance();

        final OrderAdaptor orderAdaptor = new OrderAdaptor(getActivity(), R.layout.order_list_view_item, ordersList);
        ordersListView.setAdapter(orderAdaptor);

        icecreamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isPresent = false;
                for (OrderModel order : ordersList) {
                    if(order.getItem().equals("Ice Cream")) {
                        order.setQuantity(order.getQuantity()+1);
                        order.setPrice(order.getPrice() + 15.00);
                        isPresent = true;
                    }
                }

                if(isPresent == false) {
                    OrderModel order = new OrderModel();
                    order.setItem("Ice Cream");
                    order.setQuantity(1);
                    order.setPrice(15.00);
                    order.setId(1);

                    ordersList.add(order);
                }

                orderAdaptor.notifyDataSetChanged();
            }
        });

        yogurtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isPresent = false;
                for (OrderModel order : ordersList) {
                    if(order.getItem().equals("Frozen Yogurt")) {
                        order.setQuantity(order.getQuantity()+1);
                        order.setPrice(order.getPrice() + 5.00);
                        isPresent = true;
                    }
                }

                if(isPresent == false) {
                    OrderModel order = new OrderModel();
                    order.setItem("Frozen Yogurt");
                    order.setQuantity(1);
                    order.setPrice(5.00);
                    order.setId(1);

                    ordersList.add(order);
                }

                orderAdaptor.notifyDataSetChanged();
            }
        });
        final List<Map> newList = new ArrayList<>();
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference = fStore.collection("vipCustomers").document(vipIdEditText.getText().toString());
                //documentReference.
                documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        String firstNameString = documentSnapshot.getString("firstName");
                        String lastNameString = documentSnapshot.getString("lastName");
                        Long pointsLong = (Long)documentSnapshot.get("points");
                        int points = pointsLong.intValue();

                        fullName.setText(firstNameString + " " + lastNameString);
                        status.setText(documentSnapshot.getString("status"));
                        totalPoints.setText(String.valueOf(points));
                        //monthlypoints.setText(String.valueOf(points));
                        if(points > 1000) {
                            status.setText("Gold");
                            //goldStar.setVisibility(View.VISIBLE);
                        } else {
                            status.setText("VIP");
                            //goldStar.setVisibility(View.GONE);
                        }
                        //vipId.setText(documentSnapshot.getString("vipId"));

                        List<Map> firestoreOrders = new ArrayList<>();
                        Map<String, Object> map = documentSnapshot.getData();
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("orders")) {

                                firestoreOrders = (ArrayList<Map>) entry.getValue();
                                newList.addAll(firestoreOrders);

                            }
                        }
                    }
                });

            }
        });

        //vipIdEditText.setText("202004271018");

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DocumentReference documentReference = fStore.collection("vipCustomers").document(vipIdEditText.getText().toString());
                String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());

                Double totalPrice = 0.00;
                for (OrderModel order : ordersList) {
                    Map<String, Object> ordersMap = new HashMap<String, Object>();
                    ordersMap.put("item", order.getItem());
                    ordersMap.put("quantity", order.getQuantity());
                    ordersMap.put("price", order.getPrice());
                    ordersMap.put("date", date);
                    newList.add(ordersMap);
                    totalPrice = totalPrice + order.getPrice();
                }

                int currentPoints = Integer.parseInt(totalPoints.getText().toString());
                int totalPointsInt = (int)Math.floor(totalPrice);

                documentReference.update("orders", newList);
                documentReference.update("points", currentPoints+totalPointsInt);

                newList.clear();
                orderAdaptor.notifyDataSetChanged();
                Toast.makeText(getActivity(),"Order placed. Thank you.",Toast.LENGTH_LONG);


            }
        });

        return root;
    }
}
