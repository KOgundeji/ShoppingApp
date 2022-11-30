package com.kunle.shoppinglistapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.kunle.shoppinglistapp.R;
import com.kunle.shoppinglistapp.data.ShoppingRepository;
import com.kunle.shoppinglistapp.models.Food;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final Context context;
    private ArrayList<Food> foodList;

    public ItemAdapter(Context context, ArrayList<Food> itemListforEachCategory) {
        this.context = context;
        this.foodList = itemListforEachCategory;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.inner_cardview,parent,false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        int textColor = ContextCompat.getColor(context,R.color.text_color);
        String name = foodList.get(position).getName();
        String quantity = String.valueOf(foodList.get(position).getQuantity());
        String measurement = foodList.get(position).getMeasurement();
        String parenthesis;
        if (measurement.equals("")) {
            parenthesis = "(" + quantity + ")";
        } else {
            parenthesis = "(" + quantity + " " + measurement + ")";
        }
        SpannableString first_part = new SpannableString(name);

        SpannableString second_part = new SpannableString(parenthesis);
        second_part.setSpan(new ForegroundColorSpan(Color.GRAY),
                0,parenthesis.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        second_part.setSpan(new AbsoluteSizeSpan(12,true),
                0,parenthesis.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        CharSequence finalText = TextUtils.concat(first_part," ",second_part);

        holder.item.setText(finalText);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private final TextView item;
        private final CheckBox checkBox;
        private final ImageView clickable_pencil;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.shoppingList_item);
            checkBox = itemView.findViewById(R.id.checkBox);
            clickable_pencil = itemView.findViewById(R.id.shoppingList_edit);


            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggle(view);
                }
            });

            clickable_pencil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edit(view);
                }
            });

        }

        private void toggle(View view) {
        }

        private void edit(View view) {
        }
    }
}
