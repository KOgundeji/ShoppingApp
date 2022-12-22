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
import com.kunle.shoppinglistapp.models.MealFoodMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ItemViewHolder> {

    private final Context context;
    private final List<Food> foodList;
    private boolean visible = false;
    private int deleteType;
    private ArrayList<Integer> Int_Delete_list; //use to capture adapter position
    private List<Long> Class_Delete_list; //used to capture rowID

    public static final int CLASS_DELETE_LIST = 0;
    public static final int INTEGER_DELETE_LIST = 1;

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }
    public FoodAdapter(Context context, List<Food> foodList, int deleteType) {
        this(context, foodList);
        this.deleteType = deleteType;
        if (deleteType == 0) {
            Class_Delete_list = new ArrayList<>();
        } else if (deleteType == 1) {
            Int_Delete_list = new ArrayList<>(Collections.nCopies(foodList.size(), 0));
        }
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

    public ArrayList<Integer> getInt_Delete_list() {
        return Int_Delete_list;
    }

    public List<Long> getClass_Delete_list() {
        return Class_Delete_list;
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
                        if (deleteType == 0) {
                            Class_Delete_list.add(foodList.get(getAdapterPosition()).getFoodId());
                        } else {
                            Int_Delete_list.set(getAdapterPosition(), 1);
                        }
                    } else if (!checkBox.isChecked()) {
                        if (deleteType == 0) {
                            Class_Delete_list.remove(foodList.get(getAdapterPosition()).getFoodId());
                        } else {
                            Int_Delete_list.set(getAdapterPosition(), 0);
                        }
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
