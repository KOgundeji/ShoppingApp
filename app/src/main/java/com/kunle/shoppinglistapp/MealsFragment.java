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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.kunle.shoppinglistapp.adapters.FoodAdapter;
import com.kunle.shoppinglistapp.adapters.ItemAdapter;
import com.kunle.shoppinglistapp.data.MealWithIngredients;
import com.kunle.shoppinglistapp.databinding.FragmentMealsBinding;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.adapters.MealAdapter;
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
    private String[] category_items = {"Produce", "Fruit", "Meat/Fish", "Condiments", "Beverages", "Snacks",
            "Pet Supplies", "Baking/Spices", "Bread/Grains", "Dairy", "Frozen Food", "Canned Goods", "For the Home",
            "Toiletries"};

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MealsFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static MealsFragment newInstance(String param1, String param2) {
        MealsFragment fragment = new MealsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentMealsBinding.inflate(inflater, container, false);
        setupRecyclerView();

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())
                .create(ShoppingViewModel.class);

        viewModel.getAllMeals().observe(requireActivity(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                setMainAdapter(meals);
            }
        });

        setActionListenersandObservers();
        return bind.getRoot();
    }

    private void setMainAdapter(List<Meal> mealList) {
        mealAdapter = new MealAdapter(this.getContext(), mealList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        bind.mealRecyclerView.setLayoutManager(manager);
        bind.mealRecyclerView.setAdapter(mealAdapter);
    }

    private void setSecondaryAdapter(View view, ArrayList<Food> foodList) {
        foodAdapter = new FoodAdapter(view.getContext(), foodList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(view.getContext());
        add_meal_recycler.setLayoutManager(manager);
        add_meal_recycler.setAdapter(foodAdapter);
        Log.d("AdapterTest", "Secondary Adapter: check");
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
        bind.mealAdd.setOnClickListener(new View.OnClickListener() {
            ArrayList<Food> temp_food_list = new ArrayList<>();
            MutableLiveData<ArrayList<Food>> live_food = new MutableLiveData<>(temp_food_list);
            MealWithIngredients mealWithIngredients = new MealWithIngredients();

            @Override
            public void onClick(View view) {
//                final int random_int = new Random().nextInt();

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View new_view = inflater.inflate(R.layout.add_new_meal, null);

                add_meal_recycler = new_view.findViewById(R.id.add_meal_recycler);
                TextInputEditText meal_name = new_view.findViewById(R.id.enter_meal_name);
                ImageView add = new_view.findViewById(R.id.add_meal_ingredient_add);
                ImageView delete = new_view.findViewById(R.id.add_meal_ingredient_delete);
                ImageView save = new_view.findViewById(R.id.add_meal_ingredient_save);
                ImageView cancel = new_view.findViewById(R.id.add_meal_ingredient_cancel);

                temp_food_list.add(new Food("Tacos"));
                temp_food_list.add(new Food("Cheese"));
                temp_food_list.add(new Food("Whatever"));

                live_food.observe(requireActivity(), new Observer<ArrayList<Food>>() {
                    @Override
                    public void onChanged(ArrayList<Food> foods) {
                        Log.d("AdapterTest", "LiveFood: check");
                        setSecondaryAdapter(new_view, foods);
                    }
                });

                builder.setView(new_view);
                builder.create().show();

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                        LayoutInflater inflater = getLayoutInflater();
                        View new_view = inflater.inflate(R.layout.add_ingredients, null);

                        TextInputEditText ingredient = new_view.findViewById(R.id.add_new_ingredient_for_meal);
                        TextInputEditText measurement = new_view.findViewById(R.id.add_new_measurement_for_meal);
                        TextInputEditText quantity = new_view.findViewById(R.id.add_new_quantity_for_meal);
                        AutoCompleteTextView dropdown = new_view.findViewById(R.id.add_new_category_for_meal);

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.category_list_items, category_items);
                        dropdown.setAdapter(adapter);

                        final String[] selectedItem = {"N/A"};

                        dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                selectedItem[0] = adapterView.getItemAtPosition(i).toString();
                            }
                        });

                        builder.setView(new_view);


                        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Food new_food = new Food(String.valueOf(ingredient.getText()),
                                        Integer.parseInt(String.valueOf(quantity.getText())),
                                        String.valueOf(measurement.getText()),
                                        selectedItem[0]);

                                temp_food_list.add(new_food);
                                live_food.setValue(temp_food_list);
//
//                                new_food.setTemp_group_num(random_int);
//                                ShoppingViewModel.insertFood(new_food);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        builder.create().show();
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //figure out which ingredients they want to delete

                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String entered_meal_name = String.valueOf((meal_name).getText()).trim();
                        if (!entered_meal_name.isEmpty()) {
                            Meal new_meal = new Meal(entered_meal_name);

                            mealWithIngredients.meal = new_meal; //this is actually unnecessary
//                            new_meal.setTemp_group_num(random_int);
//                            ShoppingViewModel.insertMeal(new_meal);
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
                        //cancel meal. Save nothing
                    }
                });


                ShoppingViewModel.insertMeal(new Meal("Chicken Pot Pie"));
            }
        });
    }

    public void onMealOptionSelect() {

    }
}
