package com.example.groceryshop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryshop.Modal.TopProductdata;
import com.example.groceryshop.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
     Context context;
     ArrayList<TopProductdata> topProductdata;

    public OrderAdapter(Context context, ArrayList<TopProductdata> topProductdata) {
        this.context = context;
        this.topProductdata = topProductdata;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.myorder_listview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return topProductdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ordernumber,orderdate,deliverdate,details;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ordernumber=itemView.findViewById(R.id.orderid);
            orderdate=itemView.findViewById(R.id.orderdateid);
            deliverdate=itemView.findViewById(R.id.deliverid);
            details=itemView.findViewById(R.id.detailsid);
        }
    }
}
