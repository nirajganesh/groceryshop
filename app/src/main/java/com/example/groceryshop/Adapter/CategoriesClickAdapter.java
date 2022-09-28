package com.example.groceryshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceryshop.CategoryItemActivity;
import com.example.groceryshop.Modal.Categorieslistdata;
import com.example.groceryshop.R;

import java.util.ArrayList;

public class CategoriesClickAdapter  extends RecyclerView.Adapter<CategoriesClickAdapter.ViewHolder> {
    Context context;
    ArrayList<Categorieslistdata> categorieslistdata;
    String categoriesname;

    public CategoriesClickAdapter(Context context, ArrayList<Categorieslistdata> categorieslistdata,String categoriesname) {
        this.context = context;
        this.categorieslistdata = categorieslistdata;
        this.categoriesname=categoriesname;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.categories_clickview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(categoriesname.equals(categorieslistdata.get(position).getAuthor()))
        {
            Toast.makeText(context,categoriesname,Toast.LENGTH_LONG).show();
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#DD5E98"));
            holder.textView.setTextColor(Color.WHITE);

        }
       holder.textView.setText(categorieslistdata.get(position).getAuthor());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, CategoryItemActivity.class);
                intent.putExtra("name",categorieslistdata.get(position).getAuthor());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return categorieslistdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView textView;
         CardView cardView;
         RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textid);
            cardView=itemView.findViewById(R.id.cardid);
            relativeLayout=itemView.findViewById(R.id.relativeid);
        }
    }
}
