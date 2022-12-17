package com.kunle.shoppinglistapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kunle.shoppinglistapp.R;
import com.kunle.shoppinglistapp.models.FoodCategory;
import com.kunle.shoppinglistapp.models.ShoppingViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private final Context context;
    private final List<FoodCategory> categoryandItemList;

    public CategoryAdapter(Context context, List<FoodCategory> categoryandItemList) {
        this.context = context;
        this.categoryandItemList = categoryandItemList;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.outer_cardview,parent,false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        FoodCategory category = categoryandItemList.get(position);
        holder.category.setText(category.getCategoryName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setInitialPrefetchItemCount(category.getItemList().size());

        ItemAdapter itemAdapter = new ItemAdapter(context,category.getItemList());
        holder.innerRecycler.setHasFixedSize(true);
        holder.innerRecycler.setLayoutManager(layoutManager);
        holder.innerRecycler.setAdapter(itemAdapter);
        holder.innerRecycler.setRecycledViewPool(viewPool);
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
