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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.kunle.shoppinglistapp.R;
import com.kunle.shoppinglistapp.models.Category;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.GroceryList;
import com.kunle.shoppinglistapp.models.ShoppingViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddFoodAdapter extends RecyclerView.Adapter<AddFoodAdapter.ItemViewHolder> {
    //this is the adapter used for the temp_food_list and NOT for database entries/removals
    //this is only access when creating a new Meal from the MealsFragment

    private final Context context;
    private final List<Food> foodList;
    private boolean visible = false;
    private ArrayList<Integer> Int_Delete_list; //use to capture adapter position

    public AddFoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
        Int_Delete_list = new ArrayList<>(Collections.nCopies(foodList.size(), 0));
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

    public class ItemViewHolder extends RecyclerView.ViewHolder {

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
                    if (checkBox.isChecked()) {
                        Int_Delete_list.set(getAdapterPosition(), 1);
                    } else if (!checkBox.isChecked()) {
                        Int_Delete_list.set(getAdapterPosition(), 0);
                    }
                }
            });

            clickable_pencil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View new_view = inflater.inflate(R.layout.add_ingredients, null);

                    TextInputEditText name = new_view.findViewById(R.id.add_new_ingredient_for_meal);
                    TextInputEditText quantity = new_view.findViewById(R.id.add_new_quantity_for_meal);
                    AutoCompleteTextView dropdown = new_view.findViewById(R.id.add_new_category_for_meal);
                    LinearLayout confirm = new_view.findViewById(R.id.grocery_edit_confirm);
                    LinearLayout back = new_view.findViewById(R.id.grocery_edit_back);

                    builder.setView(new_view);
                    AlertDialog dialog = builder.create();

                    String uploaded_name = foodList.get(getAdapterPosition()).getName();
                    String uploaded_quantity = foodList.get(getAdapterPosition()).getQuantity();
                    String uploaded_category = ShoppingViewModel.temp_category_map.get(uploaded_name);
                    name.setText(uploaded_name);
                    quantity.setText(uploaded_quantity);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.category_list_items, ShoppingViewModel.getFoodCategories());
                    dropdown.setAdapter(adapter);
                    dropdown.setText(uploaded_category);

                    final String[] selectedItem = {"Uncategorized"};

                    dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedItem[0] = adapterView.getItemAtPosition(i).toString();
                        }
                    });

                    dialog.show();

                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String updated_name = String.valueOf(name.getText()).trim();
                            String updated_quantity = String.valueOf(quantity.getText()).trim();
                            String updated_category = selectedItem[0];

                            Food updatedFood = new Food(updated_name,updated_quantity);

                            ShoppingViewModel.temp_food_list.remove(foodList.get(position));
                            ShoppingViewModel.temp_category_map.remove(uploaded_name);

                            ShoppingViewModel.temp_food_list.add(updatedFood);
                            ShoppingViewModel.temp_category_map.put(updated_name,updated_category);

                            ShoppingViewModel.live_food.setValue(ShoppingViewModel.temp_food_list);

                            dialog.dismiss();
                        }
                    });

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                }
            });
        }
    }
}
