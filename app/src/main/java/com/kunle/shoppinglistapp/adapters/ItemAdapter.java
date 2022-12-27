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

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final Context context;
    private final List<Food> foodPerCategory;

    public ItemAdapter(Context context, List<Food> foodPerCategory) {
        this.context = context;
        this.foodPerCategory = foodPerCategory;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inner_cardview, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        int textColor = ContextCompat.getColor(context, R.color.text_color);
        String name = foodPerCategory.get(position).getName();
        String quantity = foodPerCategory.get(position).getQuantity();

        String parenthesis = "(" + quantity + ")";

        SpannableString first_part = new SpannableString(name);

        SpannableString second_part = new SpannableString(parenthesis);
        second_part.setSpan(new ForegroundColorSpan(Color.GRAY),
                0, parenthesis.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        second_part.setSpan(new AbsoluteSizeSpan(14, true),
                0, parenthesis.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        CharSequence finalText = TextUtils.concat(first_part, " ", second_part);

        holder.item.setText(finalText);
    }

    @Override
    public int getItemCount() {
        return foodPerCategory.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView item;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.shoppingList_item);
            CheckBox checkBox = itemView.findViewById(R.id.checkBox);
            ImageView clickable_pencil = itemView.findViewById(R.id.shoppingList_edit);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Food updatedGrocery = foodPerCategory.get(getAdapterPosition());
                    updatedGrocery.setInGroceryList(false);
                    ShoppingViewModel.updateFood(updatedGrocery);
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

                    String uploaded_name = foodPerCategory.get(getAdapterPosition()).getName();
                    String uploaded_quantity = foodPerCategory.get(getAdapterPosition()).getQuantity();
                    String uploaded_category = foodPerCategory.get(getAdapterPosition()).getCategory();
                    boolean uploaded_inGroceryList = foodPerCategory.get(getAdapterPosition()).isInGroceryList();
                    long uploaded_groceryId = foodPerCategory.get(getAdapterPosition()).getFoodId();

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

                            Food updatedGrocery = new Food(updated_name, updated_quantity,
                                    updated_category,uploaded_inGroceryList);
                            updatedGrocery.setFoodId(uploaded_groceryId);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    ShoppingViewModel.updateFood(updatedGrocery);
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
