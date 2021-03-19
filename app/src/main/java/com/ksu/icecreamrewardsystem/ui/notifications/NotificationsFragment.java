package com.ksu.icecreamrewardsystem.ui.notifications;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ksu.icecreamrewardsystem.R;
import com.ksu.icecreamrewardsystem.ui.managevip.CustomAdaptor;
import com.ksu.icecreamrewardsystem.ui.managevip.VipCustomerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final List<DailyOrdersModel> dailyOrdersList = new ArrayList<>();



        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        firestore.collection("vipCustomers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());

                        Map<String, Object> map = document.getData();
                        String firstName = String.valueOf(map.get("firstName"));
                        String lastName = String.valueOf(map.get("lastName"));
                        List<Map> firestoreOrders = new ArrayList<>();

                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("orders")) {

                                firestoreOrders = (ArrayList<Map>) entry.getValue();

                                for (Map<String, Object> order : firestoreOrders) {
                                    DailyOrdersModel dailyOrder = new DailyOrdersModel();
                                        String date = String.valueOf(order.get("date"));
                                        Double price = (Double) order.get("price");
                                        dailyOrder.setDate(date);
                                        dailyOrder.setTotal(price);
                                        dailyOrder.setCustomer(firstName + "" + lastName);
                                    dailyOrdersList.add(dailyOrder);
                                }

                            }
//                            else if(entry.getKey().equals("firstName")) {
//                                firstName = String.valueOf(entry.getValue());
//                            } else if(entry.getKey().equals("lastName")) {
//                                lastName = String.valueOf(entry.getValue());
//                            }

                        }

                    }
                    Log.d("TAG", list.toString());
                    ListView listView = (ListView) root.findViewById(R.id.dailyOrdersListView);
                    final DailyOrdersAdaptor customAdaptor = new DailyOrdersAdaptor(getActivity(), R.layout.daily_orders_list_view_item, dailyOrdersList);
                    listView.setAdapter(customAdaptor);
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });








//        final TextView textView = root.findViewById(R.id.text_notifications);
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}
