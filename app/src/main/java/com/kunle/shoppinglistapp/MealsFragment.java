package com.kunle.shoppinglistapp;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.kunle.shoppinglistapp.adapters.FoodAdapter;
import com.kunle.shoppinglistapp.data.MealWithIngredients;
import com.kunle.shoppinglistapp.databinding.FragmentMealsBinding;
import com.kunle.shoppinglistapp.models.Category;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.adapters.MealAdapter;
import com.kunle.shoppinglistapp.models.MealFoodMap;
import com.kunle.shoppinglistapp.models.ShoppingViewModel;
import com.kunle.shoppinglistapp.util.SwipeController;
import com.kunle.shoppinglistapp.util.SwipeControllerActions;

import java.util.ArrayList;
import java.util.List;

public class MealsFragment extends Fragment {

    private FragmentMealsBinding bind;
    private ShoppingViewModel viewModel;
    private ArrayList<Food> foodList;
    private ArrayList<Meal> mealList;
    private MealAdapter mealAdapter;
    private FoodAdapter foodAdapter;
    private RecyclerView add_meal_recycler;
    private final String[] category_items = ShoppingViewModel.getCategoryItems();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentMealsBinding.inflate(inflater, container, false);
//        setupRecyclerView();

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())
                .create(ShoppingViewModel.class);

        setActionListenersandObservers();
        return bind.getRoot();
    }

    private void setMainAdapter(List<Meal> mealList) {
        mealAdapter = new MealAdapter(this.getContext(), mealList, requireActivity());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        bind.mealRecyclerView.setLayoutManager(manager);
        bind.mealRecyclerView.setAdapter(mealAdapter);
    }

    private void setSecondaryAdapter(View view, ArrayList<Food> foodList) {
        foodAdapter = new FoodAdapter(view.getContext(), foodList, FoodAdapter.INTEGER_DELETE_LIST);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(view.getContext());
        add_meal_recycler.setLayoutManager(manager);
        add_meal_recycler.setAdapter(foodAdapter);
    }

    private void setupRecyclerView() {
        SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {

                super.onRightClicked(position);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(bind.mealRecyclerView);

        bind.mealRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    private void setActionListenersandObservers() {

        viewModel.getAllMeals().observe(requireActivity(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                if (meals.size() > 0) {
                    bind.emptyNotificationMeals.setVisibility(View.GONE);
                } else {
                    bind.emptyNotificationMeals.setVisibility(View.VISIBLE);
                }
                setMainAdapter(meals);
            }
        });

        bind.mealAdd.setOnClickListener(new View.OnClickListener() {
            ArrayList<Food> temp_food_list = new ArrayList<>();
            ArrayList<Category> temp_category_list = new ArrayList<>();
            MutableLiveData<ArrayList<Food>> live_food = new MutableLiveData<>(temp_food_list);
            MealWithIngredients mealWithIngredients = new MealWithIngredients();

            @Override
            public void onClick(View view) {

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

                temp_food_list.add(new Food("Tacos", "2"));
                temp_food_list.add(new Food("Cheese", "2"));
                temp_food_list.add(new Food("Whatever", "3"));

                builder.setView(new_view);
                AlertDialog dialog = builder.create();
                dialog.show();

                live_food.observe(requireActivity(), new Observer<ArrayList<Food>>() {
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
                                Food new_food = new Food(String.valueOf(ingredient.getText()).trim(),
                                        String.valueOf(quantity.getText()).trim());

                                Category category = new Category(new_food.getName(), selectedItem[0]);

                                //might need to add a temp category?
                                temp_category_list.add(category);
                                temp_food_list.add(new_food);
                                live_food.setValue(temp_food_list);
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
                        foodAdapter.setVisibility(true);
                        foodAdapter.notifyDataSetChanged();

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
                                    for (Food food : temp_food_list) {
                                        long foodId = ShoppingViewModel.insertFood(food);
                                        food.setFoodId(foodId);
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
                        //deletes from the back to the front.
                        //Prevents shift to the left when early items are deleted
                        for (int count = (foodAdapter.getInt_Delete_list().size() - 1); count >= 0; count--) {
                            if (foodAdapter.getInt_Delete_list().get(count) == 1) {
                                temp_food_list.remove(count);
                            }
                        }
                        live_food.setValue(temp_food_list);

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

        bind.mealDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //figure out which meals they want to delete
                bind.mealsRegularLinearLayout.setVisibility(View.GONE);
                bind.mealsDeleteLayout.setVisibility(View.VISIBLE);
                mealAdapter.setVisibility(true);
                mealAdapter.notifyDataSetChanged();
            }
        });

        bind.mealsTrashCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Long mealId : mealAdapter.getDelete_list()) {
                    ShoppingViewModel.deleteMealWithIngredients(mealId);
                    Meal temp_meal = new Meal("");
                    temp_meal.setMealId(mealId);
                    ShoppingViewModel.deleteMeal(temp_meal);
                }

                bind.mealsRegularLinearLayout.setVisibility(View.VISIBLE);
                bind.mealsDeleteLayout.setVisibility(View.GONE);
                mealAdapter.setVisibility(false);
            }
        });

        bind.mealsDeleteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind.mealsRegularLinearLayout.setVisibility(View.VISIBLE);
                bind.mealsDeleteLayout.setVisibility(View.GONE);
                mealAdapter.setVisibility(false);
                mealAdapter.notifyDataSetChanged();
            }
        });
    }

}
