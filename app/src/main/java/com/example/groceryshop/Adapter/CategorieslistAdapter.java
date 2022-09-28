package com.example.groceryshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.groceryshop.CategoryItemActivity;
import com.example.groceryshop.Modal.Categorieslistdata;
import com.example.groceryshop.R;
import com.smarteist.autoimageslider.SliderLayout;

import java.util.ArrayList;

public class CategorieslistAdapter extends RecyclerView.Adapter<CategorieslistAdapter.ViewHodler> {
    Context context;
    ArrayList<Categorieslistdata> categorylists;


    public CategorieslistAdapter(Context context, ArrayList<Categorieslistdata> categorylist) {
        this.context = context;
        this.categorylists=categorylist;

    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.categories_listview,parent,false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {

        Glide.with(context).load(categorylists.get(position).getDownload_url()).into(holder.imageView);
        holder.btn.setText(categorylists.get(position).getAuthor());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, CategoryItemActivity.class);
                intent.putExtra("name",categorylists.get(position).getAuthor());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categorylists.size();
    }

    public class ViewHodler  extends RecyclerView.ViewHolder{
         ImageView imageView;
         Button btn;
         CardView cardView;

         public ViewHodler(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageviewid);
            btn=itemView.findViewById(R.id.buttonid);
            cardView=itemView.findViewById(R.id.cardid);
        }
    }
}
