package com.kunle.shoppinglistapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kunle.shoppinglistapp.R;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.models.ShoppingViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private final Context context;
    private final List<Meal> mealList;
    private boolean visible = false;
    private List<Meal> delete_list;

    public MealAdapter(Context context, List<Meal> mealList) {
        this.context = context;
        this.mealList = mealList;
        delete_list = new ArrayList<>();
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.inner_cardview, parent, false);
        return new MealViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        if (visible) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }

        String name = mealList.get(position).getName();
        holder.item.setText(name);
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public void setVisibility(boolean visible) {
        this.visible = visible;
    }

    public List<Meal> getDelete_list() {
        return delete_list;
    }

    public class MealViewHolder extends RecyclerView.ViewHolder {

        private final TextView item;
        private final CheckBox checkBox;
        private final ImageView clickable_pencil;


        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.shoppingList_item);
            checkBox = itemView.findViewById(R.id.checkBox);
            clickable_pencil = itemView.findViewById(R.id.shoppingList_edit);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox.isChecked()) {
                        delete_list.add(mealList.get(getAdapterPosition()));
                    } else if (!checkBox.isChecked()) {
                        delete_list.remove(mealList.get(getAdapterPosition()));
                    }
                }
            });

            clickable_pencil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }
    }
}

