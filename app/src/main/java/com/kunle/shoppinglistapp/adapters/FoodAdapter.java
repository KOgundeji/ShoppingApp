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
import androidx.recyclerview.widget.RecyclerView;

import com.kunle.shoppinglistapp.R;
import com.kunle.shoppinglistapp.models.Food;

import java.util.ArrayList;
import java.util.Collections;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ItemViewHolder> {

    private final Context context;
    private final ArrayList<Food> foodList;
    private boolean visible = false;
    private ArrayList<Integer> delete_list;

    public FoodAdapter(Context context, ArrayList<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
        delete_list = new ArrayList<>(Collections.nCopies(foodList.size(),0));
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.inner_cardview, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        int textColor = ContextCompat.getColor(context, R.color.text_color);

        if (visible) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }

        String name = foodList.get(position).getName();
        String quantity = foodList.get(position).getQuantity();
        String parenthesis = "(" + quantity + ")";

        SpannableString first_part = new SpannableString(name);

        SpannableString second_part = new SpannableString(parenthesis);
        second_part.setSpan(new ForegroundColorSpan(Color.GRAY),
                0, parenthesis.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        second_part.setSpan(new AbsoluteSizeSpan(12, true),
                0, parenthesis.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        CharSequence finalText = TextUtils.concat(first_part, " ", second_part);

        holder.item.setText(finalText);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public void setVisibility(boolean visible) {
        this.visible = visible;
    }

    public ArrayList<Integer> getDelete_list() {
        return delete_list;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView item;
        private final CheckBox checkBox;
        private final ImageView clickable_pencil;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.shoppingList_item);
            checkBox = itemView.findViewById(R.id.checkBox);
            clickable_pencil = itemView.findViewById(R.id.shoppingList_edit);



//            item.setOnLongClickListener();


            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox.isChecked()) {
                        delete_list.set(getAdapterPosition(), 1);
                    } else if (!checkBox.isChecked()) {
                        delete_list.set(getAdapterPosition(), 0);
                    }
//                    notifyDataSetChanged();
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
