package com.ksu.icecreamrewardsystem.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ksu.icecreamrewardsystem.R;
import com.ksu.icecreamrewardsystem.ui.managevip.VipCustomerModel;
import com.ksu.icecreamrewardsystem.ui.managevip.VipDetailsActivity;

import java.util.List;

public class OrderAdaptor extends ArrayAdapter<OrderModel> {

    private Context context;

    public OrderAdaptor(@NonNull Context context, int resource, @NonNull List<OrderModel> objects) {
        super(context, resource, objects);

        this.context = context;
    }

    public OrderAdaptor(OnCompleteListener<QuerySnapshot> context, List<OrderModel> object){
        super((Context) context,0, object);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.order_list_view_item,parent,false);
        }

        final FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView itemName = (TextView) convertView.findViewById(R.id.itemName);
        TextView totalPoints = (TextView) convertView.findViewById(R.id.price);
        TextView monthlyPoints = (TextView) convertView.findViewById(R.id.quantity);
        Button deleteButton = (Button) convertView.findViewById(R.id.deletButton);

        final OrderModel orderModel = getItem(position);

        id.setText(String.valueOf(position + 1));
        itemName.setText(orderModel.getItem());
        totalPoints.setText(String.valueOf(orderModel.getQuantity()));
        monthlyPoints.setText(String.valueOf(orderModel.getPrice()));

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final OrderModel orderModel = getItem(position);

            }
        });

        return convertView;
    }

}
