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
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.ShoppingViewModel;

import java.util.ArrayList;
import java.util.List;

public class EditFoodFromEditMealAdapter extends RecyclerView.Adapter<EditFoodFromEditMealAdapter.ItemViewHolder> {
    //this is the Adapter used for Database entries and removals
    //it is ONLY used when editing MealsWithIngredients on the MealsFragment.
    //this edit is only accessed in the MealAdapter

    private final Context context;
    private final List<Food> foodList;
    private boolean visible = false;
    private List<Long> Class_Delete_list; //used to capture rowID to remove from MealFoodMap (which needs an Id)

    public EditFoodFromEditMealAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
        Class_Delete_list = new ArrayList<>();
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


            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkBox.isChecked()) {
                        Class_Delete_list.add(foodList.get(getAdapterPosition()).getFoodId());
                    } else if (!checkBox.isChecked()) {
                        Class_Delete_list.remove(foodList.get(getAdapterPosition()).getFoodId());
                    }
                }
            });

            clickable_pencil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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
                    String uploaded_category = foodList.get(getAdapterPosition()).getCategory();
                    boolean uploaded_inGroceryList = foodList.get(getAdapterPosition()).isInGroceryList();
                    long uploaded_foodId = foodList.get(getAdapterPosition()).getFoodId();

                    name.setText(uploaded_name);
                    quantity.setText(uploaded_quantity);

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.category_list_items, ShoppingViewModel.getFoodCategories());
                    dropdown.setAdapter(adapter);
                    dropdown.setText(uploaded_category,false);

                    final String[] selectedItem = {uploaded_category};

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

                            Food updatedFood = new Food(updated_name, updated_quantity,
                                    updated_category, uploaded_inGroceryList);
                            updatedFood.setFoodId(uploaded_foodId);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    ShoppingViewModel.updateFood(updatedFood);
                                }
                            }).start();

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
