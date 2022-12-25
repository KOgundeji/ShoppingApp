package com.kunle.shoppinglistapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import com.kunle.shoppinglistapp.databinding.FragmentGroceryListBinding;
import com.kunle.shoppinglistapp.models.Category;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.GroceryList;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.models.FoodCategory;
import com.kunle.shoppinglistapp.adapters.CategoryAdapter;
import com.kunle.shoppinglistapp.models.MealFoodMap;
import com.kunle.shoppinglistapp.models.Settings;
import com.kunle.shoppinglistapp.models.ShoppingViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

        viewModel = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
                .create(ShoppingViewModel.class);

        viewModel.checkSetting(Settings.NO_CATEGORIES).observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                noCategories = integer;
            }
        });

        viewModel.getAllCategories().observe(requireActivity(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                ShoppingViewModel.mainCategoryMap.clear();
                for (Category category : categories) {
                    ShoppingViewModel.mainCategoryMap.put(category.getName(), category.getCategory());
                }
            }
        });

        ShoppingViewModel.mainGroceryList.observe(requireActivity(), new Observer<List<GroceryList>>() {
            @Override
            public void onChanged(List<GroceryList> groceries) {
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

//        setExample();
        return bind.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    private List<FoodCategory> seperateFoodintoCategories(List<GroceryList> groceryList) {
        List<FoodCategory> recyclerCategories = new ArrayList<>();
        Map<String, ArrayList<GroceryList>> temp_categoryMap = new HashMap<>();

        for (int i = 0; i < groceryList.size(); i++) {

            GroceryList item = groceryList.get(i);
            String category = ShoppingViewModel.mainCategoryMap.getOrDefault(item.getName(), "Uncategorized");

            if (temp_categoryMap.get(category) != null) {
                ArrayList<GroceryList> itemList = temp_categoryMap.get(category);
                itemList.add(item);
                temp_categoryMap.replace(category, itemList);
            } else {
                ArrayList<GroceryList> itemList = new ArrayList<>();
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

    private List<FoodCategory> oneCategoryGroceryList(List<GroceryList> groceryList) {
        List<FoodCategory> recyclerCategories = new ArrayList<>();
        ArrayList<GroceryList> groceryArray = new ArrayList<>(groceryList);

        FoodCategory cat = new FoodCategory("Grocery List", groceryArray);
        recyclerCategories.add(cat);

        return recyclerCategories;
    }

    private void setListeners() {
        bind.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ShoppingViewModel.mainCategoryMap.get(String.valueOf(bind.addItemMeal.getText()).trim()) == null) {
                    ShoppingViewModel.insertCategory(new Category(
                            String.valueOf(bind.addItemMeal.getText()).trim(), "Uncategorized"));
                }

                GroceryList tempGrocery = new GroceryList(
                        String.valueOf(bind.addItemMeal.getText()).trim()
                        , String.valueOf(bind.addItemQuantityMeal.getText()).trim());

                ShoppingViewModel.insertGrocery(tempGrocery);
                ShoppingViewModel.insertFood(Food.parseFood(tempGrocery));

                bind.addItemMeal.setText("");
                bind.addItemQuantityMeal.setText("");
            }
        });
    }

    private void setAutoComplete(List<Food> food) {

        List<String> filteredNameList = new ArrayList<>();
        for (Food item : food) {
            filteredNameList.add(item.getName());
        }

        String[] filteredNameArray = ShoppingViewModel.mainCategoryMap.keySet().toArray(new String[filteredNameList.size()]);
        //filteredNameArray is an array of every single food that we have, whether it is Uncategorized or categorized.

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
                Food oranges = new Food("Oranges", "4 bunches");
                Food pineapple = new Food("Pineapple", "3");
                Food eggs = new Food("Eggs", "1 dozen");
                Food cheese = new Food("Cheese", "50 grams");
                Food pasta = new Food("Pasta", "2 boxes");
                Food tissues = new Food("Tissues", "1 box");
                Food potatoes = new Food("Potatoes", "3");

                ShoppingViewModel.insertFood(oranges);
                ShoppingViewModel.insertFood(pineapple);
                ShoppingViewModel.insertFood(eggs);
                ShoppingViewModel.insertFood(cheese);
                ShoppingViewModel.insertFood(pasta);
                ShoppingViewModel.insertFood(tissues);
                ShoppingViewModel.insertFood(potatoes);

                ShoppingViewModel.insertGrocery(GroceryList.parseGroceryList(oranges));
                ShoppingViewModel.insertGrocery(GroceryList.parseGroceryList(pineapple));
                ShoppingViewModel.insertGrocery(GroceryList.parseGroceryList(eggs));
                ShoppingViewModel.insertGrocery(GroceryList.parseGroceryList(cheese));
                ShoppingViewModel.insertGrocery(GroceryList.parseGroceryList(pasta));
                ShoppingViewModel.insertGrocery(GroceryList.parseGroceryList(tissues));
                ShoppingViewModel.insertGrocery(GroceryList.parseGroceryList(potatoes));


                Meal fruit_salad = new Meal("Fruit Salad");
                Meal omelette = new Meal("Omelette");
                Meal irish_pasta = new Meal("Irish Pasta");

                ShoppingViewModel.insertMeal(fruit_salad);
                ShoppingViewModel.insertMeal(omelette);
                ShoppingViewModel.insertMeal(irish_pasta);


//        MealFoodMap fruit_salad_map1 = new MealFoodMap(fruit_salad.getName(), oranges.getName());
//        MealFoodMap fruit_salad_map2 = new MealFoodMap(fruit_salad.getName(), pineapple.getName());
//        MealFoodMap omelette1 = new MealFoodMap(omelette.getName(), eggs.getName());
//        MealFoodMap omelette2 = new MealFoodMap(omelette.getName(), cheese.getName());
//        MealFoodMap omelette3 = new MealFoodMap(omelette.getName(), pasta.getName());
//        ShoppingViewModel.insertPair(fruit_salad_map1);
//        ShoppingViewModel.insertPair(fruit_salad_map2);
//        ShoppingViewModel.insertPair(omelette1);
//        ShoppingViewModel.insertPair(omelette2);
//        ShoppingViewModel.insertPair(omelette3);
            }
        }).start();


    }
}