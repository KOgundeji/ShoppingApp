package com.kunle.shoppinglistapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kunle.shoppinglistapp.R;
import com.kunle.shoppinglistapp.models.FoodCategory;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private final Context context;
    private final ArrayList<FoodCategory> categoryandItemList;

    public CategoryAdapter(Context context, ArrayList<FoodCategory> categoryandItemList) {
        this.context = context;
        this.categoryandItemList = categoryandItemList;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.outer_cardview,parent,false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        FoodCategory category = categoryandItemList.get(position);
        holder.category.setText(category.getCategoryName());

        ItemAdapter itemAdapter = new ItemAdapter(context,category.getItemList());
        holder.innerRecycler.setAdapter(itemAdapter);
        holder.innerRecycler.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public int getItemCount() {
        return categoryandItemList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        private final TextView category;
        private final RecyclerView innerRecycler;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category_header);
            innerRecycler = itemView.findViewById(R.id.innerRecycler);
        }
    }
}
