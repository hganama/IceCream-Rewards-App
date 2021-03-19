package com.ksu.icecreamrewardsystem.ui.notifications;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ksu.icecreamrewardsystem.R;
import com.ksu.icecreamrewardsystem.ui.home.OrderModel;

import java.util.List;

public class DailyOrdersAdaptor extends ArrayAdapter<DailyOrdersModel> {
    private Context context;

    public DailyOrdersAdaptor(@NonNull Context context, int resource, @NonNull List<DailyOrdersModel> objects) {
        super(context, resource, objects);

        this.context = context;
    }

    public DailyOrdersAdaptor(OnCompleteListener<QuerySnapshot> context, List<DailyOrdersModel> object){
        super((Context) context,0, object);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.daily_orders_list_view_item,parent,false);
        }

        final FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        TextView id = (TextView) convertView.findViewById(R.id.idDailyOrders);
        TextView date = (TextView) convertView.findViewById(R.id.dateTextViewDailyOrders);
        TextView customer = (TextView) convertView.findViewById(R.id.customerDailyOrders);
        TextView price = (TextView) convertView.findViewById(R.id.totalPriceDailyOrders);

        final DailyOrdersModel dailyOrdersModel = getItem(position);

        id.setText(String.valueOf(position + 1));
        date.setText(dailyOrdersModel.getDate());
        customer.setText(dailyOrdersModel.getCustomer());
        price.setText(String.valueOf(dailyOrdersModel.getTotal()));


        return convertView;
    }
}
