package com.kunle.shoppinglistapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.kunle.shoppinglistapp.adapters.EditFoodFromAddMealAdapter;
import com.kunle.shoppinglistapp.databinding.FragmentMealsBinding;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.adapters.ChangeMealIngredientsAdapter;
import com.kunle.shoppinglistapp.models.MealFoodMap;
import com.kunle.shoppinglistapp.models.ShoppingViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MealsFragment extends Fragment {

    private FragmentMealsBinding bind;
    private ShoppingViewModel viewModel;
    private ChangeMealIngredientsAdapter changeMealIngredientsAdapter;
    private EditFoodFromAddMealAdapter editFoodFromAddMealAdapter;
    private RecyclerView add_meal_recycler;
    private final String[] category_items = ShoppingViewModel.getFoodCategories();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentMealsBinding.inflate(inflater, container, false);
        bind.setLifecycleOwner(this);

        if (isAdded() && getActivity() != null) {
            viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())
                    .create(ShoppingViewModel.class);
            setActionListenersandObservers();
        }

        return bind.getRoot();
    }

    private void setMainAdapter(List<Meal> mealList) {
        changeMealIngredientsAdapter = new ChangeMealIngredientsAdapter(this.getContext(), mealList, requireActivity());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        bind.mealRecyclerView.setLayoutManager(manager);
        bind.mealRecyclerView.setAdapter(changeMealIngredientsAdapter);
    }

    private void setSecondaryAdapter(View view, ArrayList<Food> foodList) {
        editFoodFromAddMealAdapter = new EditFoodFromAddMealAdapter(view.getContext(), foodList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(view.getContext());
        add_meal_recycler.setLayoutManager(manager);
        add_meal_recycler.setAdapter(editFoodFromAddMealAdapter);
    }

    private void setActionListenersandObservers() {

        ShoppingViewModel.mainMealsList.observe(requireActivity(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                if (isAdded()) {
                    if (meals.size() > 0) {
                        bind.emptyNotificationMeals.setVisibility(View.GONE);
                        setMainAdapter(meals);
                    } else {
                        bind.emptyNotificationMeals.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        bind.mealAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ShoppingViewModel.temp_food_list.clear();
                ShoppingViewModel.live_food.setValue(ShoppingViewModel.temp_food_list);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View new_view = inflater.inflate(R.layout.add_new_meal, null);

                add_meal_recycler = new_view.findViewById(R.id.add_meal_recycler);
                TextInputEditText meal_name = new_view.findViewById(R.id.enter_meal_name);
                ImageView add = new_view.findViewById(R.id.add_meal_ingredient_add);
                ImageView delete = new_view.findViewById(R.id.add_meal_ingredient_delete);
                ImageView save = new_view.findViewById(R.id.add_meal_ingredient_save);
                ImageView cancel = new_view.findViewById(R.id.add_meal_ingredient_back);
                ImageView final_delete = new_view.findViewById(R.id.trash_can);
                ImageView final_cancel = new_view.findViewById(R.id.trash_can_cancel);
                LinearLayout button_bar = new_view.findViewById(R.id.add_meal_button_bar);
                LinearLayout final_delete_layout = new_view.findViewById(R.id.trash_can_layout);

                builder.setView(new_view);
                AlertDialog dialog = builder.create();
                dialog.show();

                ShoppingViewModel.live_food.observe(requireActivity(), new Observer<ArrayList<Food>>() {
                    @Override
                    public void onChanged(ArrayList<Food> foods) {
                        setSecondaryAdapter(new_view, foods);
                    }
                });


                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        LayoutInflater inflater = getLayoutInflater();
                        View new_view = inflater.inflate(R.layout.add_ingredients, null);

                        TextInputEditText ingredient = new_view.findViewById(R.id.add_new_ingredient_for_meal);
                        TextInputEditText quantity = new_view.findViewById(R.id.add_new_quantity_for_meal);
                        AutoCompleteTextView dropdown = new_view.findViewById(R.id.add_new_category_for_meal);
                        LinearLayout confirm = new_view.findViewById(R.id.grocery_edit_confirm);
                        LinearLayout back = new_view.findViewById(R.id.grocery_edit_back);


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.category_list_items, category_items);
                        dropdown.setAdapter(adapter);

                        final String[] selectedItem = {"Uncategorized"};

                        dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                selectedItem[0] = adapterView.getItemAtPosition(i).toString();
                            }
                        });

                        builder.setView(new_view);
                        AlertDialog dialog = builder.create();

                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String created_name = String.valueOf(ingredient.getText()).trim();
                                String created_quantity = String.valueOf(quantity.getText()).trim();
                                String created_category = selectedItem[0];

                                Food new_food = new Food(created_name, created_quantity,
                                        created_category, false);

                                ShoppingViewModel.temp_food_list.add(new_food);
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

                        dialog.show();
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //figure out which ingredients they want to delete
                        button_bar.setVisibility(View.GONE);
                        final_delete_layout.setVisibility(View.VISIBLE);
                        editFoodFromAddMealAdapter.setVisibility(true);
                        editFoodFromAddMealAdapter.notifyDataSetChanged();

                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String entered_meal_name = String.valueOf((meal_name).getText()).trim();
                        if (!entered_meal_name.isEmpty()) {
                            Meal new_meal = new Meal(entered_meal_name);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    long mealId = ShoppingViewModel.insertMeal(new_meal);
                                    long foodId;

                                    for (Food food : ShoppingViewModel.temp_food_list) {
                                        Food preExistingFood = ShoppingViewModel.getFood(food.getName());

                                        if (Objects.nonNull(preExistingFood)) {
                                            boolean preExisting_inGroceryList = preExistingFood.isInGroceryList();
                                            food.setInGroceryList(preExisting_inGroceryList);
                                            if (preExistingFood.getQuantity().equals(food.getQuantity())) {
                                                foodId = preExistingFood.getFoodId();
                                                food.setFoodId(foodId);
                                                ShoppingViewModel.updateFood(food);
                                            } else {
                                                foodId = ShoppingViewModel.insertFood(food);
                                            }
                                        } else {
                                            foodId = ShoppingViewModel.insertFood(food);
                                        }
                                        ShoppingViewModel.insertPair(new MealFoodMap(mealId, foodId));
                                    }
                                }
                            }).start();

                            dialog.dismiss();

                        } else {
                            AlertDialog.Builder meal_name_error = new AlertDialog.Builder(requireContext());
                            meal_name_error.setTitle("Please enter a valid meal name")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                        }
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
                        for (Food food : editFoodFromAddMealAdapter.getFoodDeleteList()) {
                            ShoppingViewModel.temp_food_list.remove(food);
                        }
                        ShoppingViewModel.live_food.setValue(ShoppingViewModel.temp_food_list);

                        button_bar.setVisibility(View.VISIBLE);
                        final_delete_layout.setVisibility(View.GONE);
                        editFoodFromAddMealAdapter.setVisibility(false);
                    }
                });

                final_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        button_bar.setVisibility(View.VISIBLE);
                        final_delete_layout.setVisibility(View.GONE);
                        editFoodFromAddMealAdapter.setVisibility(false);
                        editFoodFromAddMealAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        bind.mealDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //figure out which meals they want to delete
                bind.mealsRegularLinearLayout.setVisibility(View.GONE);
                bind.mealsDeleteLayout.setVisibility(View.VISIBLE);
                changeMealIngredientsAdapter.setVisibility(true);
                changeMealIngredientsAdapter.notifyDataSetChanged();
            }
        });

        bind.mealsTrashCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Meal meal : changeMealIngredientsAdapter.getDelete_list()) {
                    ShoppingViewModel.deleteMealWithIngredients(meal.getMealId());
                    ShoppingViewModel.deleteMeal(meal);
                }

                bind.mealsRegularLinearLayout.setVisibility(View.VISIBLE);
                bind.mealsDeleteLayout.setVisibility(View.GONE);
                changeMealIngredientsAdapter.setVisibility(false);
            }
        });

        bind.mealsDeleteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind.mealsRegularLinearLayout.setVisibility(View.VISIBLE);
                bind.mealsDeleteLayout.setVisibility(View.GONE);
                changeMealIngredientsAdapter.setVisibility(false);
                changeMealIngredientsAdapter.notifyDataSetChanged();
            }
        });
    }
}
