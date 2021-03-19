package com.ksu.icecreamrewardsystem.ui.managevip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ksu.icecreamrewardsystem.LoginActivity;
import com.ksu.icecreamrewardsystem.MainActivity;
import com.ksu.icecreamrewardsystem.R;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class CustomAdaptor extends ArrayAdapter<VipCustomerModel> {

    private Context context;

    public CustomAdaptor(@NonNull Context context, int resource, @NonNull List<VipCustomerModel> objects) {
        super(context, resource, objects);

        this.context = context;
    }

    public CustomAdaptor(OnCompleteListener<QuerySnapshot> context, List<VipCustomerModel> object){
        super((Context) context,0, object);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView =  ((Activity)getContext()).getLayoutInflater().inflate(R.layout.list_view_item,parent,false);
        }

        final FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        TextView id = (TextView) convertView.findViewById(R.id.idNumber);
        TextView vipId = (TextView) convertView.findViewById(R.id.vipId);
        TextView fullName = (TextView) convertView.findViewById(R.id.fullName);
        TextView totalPoints = (TextView) convertView.findViewById(R.id.totalPoints);
        TextView monthlyPoints = (TextView) convertView.findViewById(R.id.monthlyPoints);
        Button deleteButton = (Button) convertView.findViewById(R.id.deletButton);
        Button editButton = (Button) convertView.findViewById(R.id.editButton);

        final VipCustomerModel vipCustomerModel = getItem(position);

        vipId.setText(vipCustomerModel.getVipId());
        id.setText(String.valueOf(vipCustomerModel.getId()));
        fullName.setText(vipCustomerModel.getFullName());
        totalPoints.setText(String.valueOf(vipCustomerModel.getTotalPoints()));
        monthlyPoints.setText(String.valueOf(vipCustomerModel.getMonthlyPoints()));

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final VipCustomerModel vipCustomerModel = getItem(position);
                String vipId = vipCustomerModel.getVipId();

                fStore.collection("vipCustomers").document(vipId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        remove(vipCustomerModel);
                        notifyDataSetChanged();
                    }
                });

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final VipCustomerModel vipCustomerModel = getItem(position);
                String vipId = vipCustomerModel.getVipId();

                Intent intent = new Intent(context, VipDetailsActivity.class);
                intent.putExtra("vipId", vipId);
                context.startActivity(intent);

            }
        });

        return convertView;
    }
}
