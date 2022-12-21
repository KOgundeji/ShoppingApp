package com.kunle.shoppinglistapp.adapters;

import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.kunle.shoppinglistapp.R;
import com.kunle.shoppinglistapp.data.MealWithIngredients;
import com.kunle.shoppinglistapp.models.Category;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.MealFoodMap;
import com.kunle.shoppinglistapp.models.ShoppingViewModel;

import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    private final Context context;
    private final List<MealWithIngredients> mealList;
    private boolean visible = false;
    private List<MealWithIngredients> delete_list;

    public MealAdapter(Context context, List<MealWithIngredients> mealList) {
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

        String name = mealList.get(position).getMeal().getName();
        holder.item.setText(name);
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public void setVisibility(boolean visible) {
        this.visible = visible;
    }

    public List<MealWithIngredients> getDelete_list() {
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
                    List<Food> currentFoodList = mealList.get(getAdapterPosition()).foodList;
                    long mealId = mealList.get(getAdapterPosition()).getMeal().getMealId();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View new_view = inflater.inflate(R.layout.edit_meal_ingredients, null);

                    RecyclerView mealIngredientsRecycler = new_view.findViewById(R.id.modify_meal_recycler);
                    TextView mealName = new_view.findViewById(R.id.mealName);
                    ImageView add = new_view.findViewById(R.id.modify_meal_ingredient_add);
                    ImageView delete = new_view.findViewById(R.id.modify_meal_ingredient_delete);
                    ImageView cancel = new_view.findViewById(R.id.modify_meal_ingredient_back);
                    ImageView final_delete = new_view.findViewById(R.id.modify_trash_can);
                    ImageView final_cancel = new_view.findViewById(R.id.modify_trash_can_cancel);
                    LinearLayout button_bar = new_view.findViewById(R.id.modify_meal_button_bar);
                    LinearLayout final_delete_layout = new_view.findViewById(R.id.modify_trash_can_layout);

                    mealName.setText(mealList.get(getAdapterPosition()).getMeal().getName());
                    builder.setView(new_view);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                    FoodAdapter foodAdapter = new FoodAdapter(context, currentFoodList, FoodAdapter.CLASS_DELETE_LIST);
                    mealIngredientsRecycler.setHasFixedSize(true);
                    mealIngredientsRecycler.setLayoutManager(layoutManager);
                    mealIngredientsRecycler.setAdapter(foodAdapter);

                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builderAdd = new AlertDialog.Builder(context);
                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View new_view = inflater.inflate(R.layout.add_ingredients, null);

                            TextInputEditText ingredient = new_view.findViewById(R.id.add_new_ingredient_for_meal);
                            TextInputEditText quantity = new_view.findViewById(R.id.add_new_quantity_for_meal);
                            AutoCompleteTextView dropdown = new_view.findViewById(R.id.add_new_category_for_meal);
                            LinearLayout confirm = new_view.findViewById(R.id.grocery_edit_confirm);
                            LinearLayout back = new_view.findViewById(R.id.grocery_edit_back);

                            ArrayAdapter<String> adapter =
                                    new ArrayAdapter<String>(context, R.layout.category_list_items,
                                            ShoppingViewModel.getCategoryItems());
                            dropdown.setAdapter(adapter);

                            final String[] selectedItem = {"Uncategorized"};

                            dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    selectedItem[0] = adapterView.getItemAtPosition(i).toString();
                                }
                            });

                            builderAdd.setView(new_view);
                            AlertDialog dialog = builderAdd.create();

                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Food new_food = new Food(String.valueOf(ingredient.getText()).trim(),
                                                    String.valueOf(quantity.getText()).trim());

                                            Category category = new Category(new_food.getName(), selectedItem[0]);

                                            ShoppingViewModel.insertCategory(category);
                                            long foodId = ShoppingViewModel.insertFood(new_food);
                                            ShoppingViewModel.insertPair(new MealFoodMap(mealId, foodId));
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

                            dialog.show();
                        }
                    });

                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //shows new linear layout and lets that choose what to delete
                            button_bar.setVisibility(View.GONE);
                            final_delete_layout.setVisibility(View.VISIBLE);
                            foodAdapter.setVisibility(true);
                            foodAdapter.notifyDataSetChanged();

                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    final_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            for (long foodId : foodAdapter.getClass_Delete_list()) {
                                ShoppingViewModel.deletePair(new MealFoodMap(mealId, foodId));
                            }

                            button_bar.setVisibility(View.VISIBLE);
                            final_delete_layout.setVisibility(View.GONE);
                            foodAdapter.setVisibility(false);
                        }
                    });

                    final_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            button_bar.setVisibility(View.VISIBLE);
                            final_delete_layout.setVisibility(View.GONE);
                            foodAdapter.setVisibility(false);
                            foodAdapter.notifyDataSetChanged();
                        }
                    });
                }
            });
        }
    }
}

