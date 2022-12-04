package com.kunle.shoppinglistapp;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.kunle.shoppinglistapp.databinding.FragmentMealsBinding;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.adapters.MealAdapter;
import com.kunle.shoppinglistapp.models.ShoppingViewModel;

import java.util.ArrayList;
import java.util.List;

public class MealsFragment extends Fragment {

    private FragmentMealsBinding bind;
    private ShoppingViewModel viewModel;
    private ArrayList<Food> foodList;
    private ArrayList<Meal> mealList;
    private MealAdapter mealAdapter;
    private String[] category_items = {"Produce","Fruit","Meat/Fish","Condiments","Beverages","Snacks",
    "Pet Supplies","Baking/Spices","Bread/Grains","Dairy","Frozen Food","Canned Goods","For the Home",
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

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())
                .create(ShoppingViewModel.class);


        viewModel.getAllMeals().observe(requireActivity(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                setAdapter(meals);
            }
        });

        setActionListenersandObservers();
        return bind.getRoot();
    }

    private void setAdapter(List<Meal> mealList) {
        mealAdapter = new MealAdapter(this.getContext(), mealList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        bind.mealRecyclerView.setLayoutManager(manager);
        bind.mealRecyclerView.setAdapter(mealAdapter);
    }

    private void setActionListenersandObservers() {
        bind.mealAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                LayoutInflater inflater = getLayoutInflater();
                View new_view = inflater.inflate(R.layout.add_new_meal, null);

                ImageView add = new_view.findViewById(R.id.add_meal_ingredient_add);
                ImageView delete = new_view.findViewById(R.id.add_meal_ingredient_delete);
                ImageView save = new_view.findViewById(R.id.add_meal_ingredient_save);

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

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.category_list_items,category_items);
                        dropdown.setAdapter(adapter);

                        dropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String item = adapterView.getItemAtPosition(i).toString();
                            }
                        });

                        builder.setView(new_view);


                        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Food new_food = new Food(String.valueOf(ingredient.getText()),
                                                Integer.parseInt(String.valueOf(quantity.getText())),
                                                String.valueOf(measurement.getText()),
                                                dropdown.getTransitionName());

                                        ShoppingViewModel.insertFood(new_food);
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

                    }
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });


                ShoppingViewModel.insertMeal(new Meal("Chicken Pot Pie"));
            }
        });
    }

    public void onMealOptionSelect() {

    }
}
