package com.kunle.shoppinglistapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import com.kunle.shoppinglistapp.databinding.FragmentGroceryListBinding;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.models.FoodCategory;
import com.kunle.shoppinglistapp.adapters.CategoryAdapter;
import com.kunle.shoppinglistapp.models.MealFoodMap;
import com.kunle.shoppinglistapp.models.Settings;
import com.kunle.shoppinglistapp.models.ShoppingViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class GroceryListFragment extends Fragment {
    private ShoppingViewModel viewModel;
    private CategoryAdapter categoryAdapter;
    private FragmentGroceryListBinding bind;
    private int noCategories = 0;

    public GroceryListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bind = FragmentGroceryListBinding.inflate(inflater, container, false);
        bind.setLifecycleOwner(this);

        setListeners();
        if (isAdded() && getActivity() != null) {
            viewModel = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
                    .create(ShoppingViewModel.class);
            setObservers();
        }

//        setExample();
        return bind.getRoot();
    }

    private List<FoodCategory> seperateFoodintoCategories(List<Food> groceryList) {
        List<FoodCategory> recyclerCategories = new ArrayList<>();
        Map<String, ArrayList<Food>> temp_categoryMap = new HashMap<>();

        for (int i = 0; i < groceryList.size(); i++) {

            Food item = groceryList.get(i);
            String category = item.getCategory();

            if (temp_categoryMap.get(category) != null) {
                ArrayList<Food> itemList = temp_categoryMap.get(category);
                itemList.add(item);
                temp_categoryMap.replace(category, itemList);
            } else {
                ArrayList<Food> itemList = new ArrayList<>();
                itemList.add(item);
                temp_categoryMap.put(category, itemList);
            }
        }

        for (String category : temp_categoryMap.keySet()) {
            FoodCategory cat = new FoodCategory(category, temp_categoryMap.get(category));
            recyclerCategories.add(cat);
        }

        return recyclerCategories;
    }

    private List<FoodCategory> oneCategoryGroceryList(List<Food> groceryList) {
        List<FoodCategory> recyclerCategories = new ArrayList<>();
        ArrayList<Food> groceryArray = new ArrayList<>(groceryList);

        FoodCategory cat = new FoodCategory("Grocery List", groceryArray);
        recyclerCategories.add(cat);

        return recyclerCategories;
    }

    private void setObservers() {
        viewModel.checkSetting(Settings.NO_CATEGORIES).observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                noCategories = integer;
            }
        });

        ShoppingViewModel.mainGroceriesList.observe(requireActivity(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> groceries) {
                if (groceries.size() > 0) {
                    bind.emptyNotificationGrocery.setVisibility(View.GONE);
                } else {
                    bind.emptyNotificationGrocery.setVisibility(View.VISIBLE);
                }

                if (noCategories == 0) {
                    categoryAdapter = new CategoryAdapter(getContext(), seperateFoodintoCategories(groceries));
                } else if (noCategories == 1) {
                    categoryAdapter = new CategoryAdapter(getContext(), oneCategoryGroceryList(groceries));
                }

                RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
                bind.outerRecycler.setHasFixedSize(true);
                bind.outerRecycler.setLayoutManager(manager);
                bind.outerRecycler.setAdapter(categoryAdapter);
            }
        });

        ShoppingViewModel.mainFoodList.observe(requireActivity(), new Observer<List<Food>>() {
            @Override
            public void onChanged(List<Food> foodList) {
                setAutoComplete(foodList);
            }
        });
    }

    private void setListeners() {
        bind.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String created_name = String.valueOf(bind.addItemMeal.getText()).trim();
                String created_quantity = String.valueOf(bind.addItemQuantityMeal.getText()).trim();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Food newGrocery;
                        Food preExistingFood = ShoppingViewModel.getFood(created_name);

                        if (Objects.nonNull(preExistingFood) && !preExistingFood.getCategory().equals("Uncategorized")) {
                            String preexisting_category = preExistingFood.getCategory();
                            newGrocery = new Food(created_name,created_quantity,preexisting_category,true);
                        } else {
                            newGrocery = new Food(created_name,created_quantity,"Uncategorized",true);
                        }

                        ShoppingViewModel.insertFood(newGrocery);
                    }
                }).start();

                bind.addItemMeal.setText("");
                bind.addItemQuantityMeal.setText("");
            }
        });
    }

    private void setAutoComplete(List<Food> food) {

        HashSet<String> filteredNameSet = new HashSet<>();
        for (Food item : food) {
            filteredNameSet.add(item.getName());
        }

        String[] filteredNameArray = filteredNameSet.toArray(new String[0]);

        if (getContext() != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>
                    (this.getContext(), android.R.layout.simple_list_item_1, filteredNameArray);
            bind.addItemMeal.setAdapter(adapter);
        }
    }

    private void setExample() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                viewModel.deleteAllFood();
                Food oranges = new Food("Oranges", "4 bunches", "Fruit", true);
                Food pineapple = new Food("Pineapple", "3", "Fruit", true);
                Food eggs = new Food("Eggs", "1 dozen", "Uncategorized", true);
                Food cheese = new Food("Cheese", "50 grams", "Uncategorized", false);
                Food pasta = new Food("Pasta", "2 boxes", "Condiments", true);
                Food tissues = new Food("Tissues", "1 box", "Uncategorized", true);
                Food potatoes = new Food("Potatoes", "3", "Uncategorized", true);


                ShoppingViewModel.insertFood(oranges);
                ShoppingViewModel.insertFood(pineapple);
                ShoppingViewModel.insertFood(eggs);
                ShoppingViewModel.insertFood(cheese);
                ShoppingViewModel.insertFood(pasta);
                ShoppingViewModel.insertFood(tissues);
                ShoppingViewModel.insertFood(potatoes);


                Meal fruit_salad = new Meal("Fruit Salad");
                Meal omelette = new Meal("Omelette");
                Meal irish_pasta = new Meal("Irish Pasta");

                viewModel.deleteAllMeals();
                ShoppingViewModel.insertMeal(fruit_salad);
                ShoppingViewModel.insertMeal(omelette);
                ShoppingViewModel.insertMeal(irish_pasta);

            }
        }).start();


    }
}