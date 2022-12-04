package com.kunle.shoppinglistapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.kunle.shoppinglistapp.adapters.ItemAdapter;
import com.kunle.shoppinglistapp.databinding.FragmentGroceryListBinding;
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
    //    private AutoCompleteTextView add_item;
    private ShoppingViewModel viewModel;
    private CategoryAdapter categoryAdapter;
    private Map<String, ArrayList<GroceryList>> categoryMap;
    private LiveData<List<Food>> foodItemList;
    private ArrayList<FoodCategory> recyclerCategories;
    private ArrayList<String> categoryList;
    private ArrayList<Food> foodList;
    private ArrayList<Meal> mealList;
    private FragmentGroceryListBinding bind;
    private int noCategories = 0;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public GroceryListFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static GroceryListFragment newInstance(String param1, String param2) {
        GroceryListFragment fragment = new GroceryListFragment();
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
//        bind = DataBindingUtil.inflate(inflater,R.layout.fragment_grocery_list,container,false);
        bind = FragmentGroceryListBinding.inflate(inflater, container, false);
//        bind.setLifecycleOwner(this);
        recyclerCategories = new ArrayList<>();
        categoryMap = new HashMap<>();
        categoryList = new ArrayList<>();
        init_categoryList(categoryList);

        setListeners();
        viewModel = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
                .create(ShoppingViewModel.class);
//        setExample();

        viewModel.checkSetting(Settings.NO_CATEGORIES).observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                noCategories = integer;
            }
        });

        viewModel.getAllGroceries().observe(requireActivity(), new Observer<List<GroceryList>>() {
            @Override
            public void onChanged(List<GroceryList> groceries) {
                if (noCategories == 1) {
                    setAdapter(oneCategoryGroceryList(groceries));
                } else {
                    setAdapter(seperateFoodintoCategories(groceries));
                }
            }
        });

        return bind.getRoot();
    }

    private ArrayList<FoodCategory> seperateFoodintoCategories(List<GroceryList> groceryList) {

        for (int i = 0; i < groceryList.size(); i++) {
            GroceryList item = groceryList.get(i);
            String category = item.getCategory();
            if (categoryMap.get(category) != null) {
                ArrayList<GroceryList> itemList = categoryMap.get(category);
                itemList.add(item);
                categoryMap.replace(category, itemList);
            } else {
                ArrayList<GroceryList> itemList = new ArrayList<>();
                itemList.add(item);
                categoryMap.put(category, itemList);
            }
        }

        for (String category : categoryMap.keySet()) {
            FoodCategory cat = new FoodCategory(category, categoryMap.get(category));
            recyclerCategories.add(cat);
        }

        return recyclerCategories;
    }

    private ArrayList<FoodCategory> oneCategoryGroceryList(List<GroceryList> groceryList) {
        ArrayList<GroceryList> groceryArray = new ArrayList<>(groceryList);
        FoodCategory cat = new FoodCategory("Grocery List", groceryArray);
        recyclerCategories.add(cat);

        return recyclerCategories;
    }


    public void setListeners() {
        bind.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroceryList temp = new GroceryList("Banana", 3, "", "Fruit");
                ShoppingViewModel.insertGrocery(temp);
            }
        });
    }


    private void setAdapter(ArrayList<FoodCategory> categories) {
//        resetAdapter();
        categoryAdapter = new CategoryAdapter(this.getContext(), categories);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        bind.outerRecycler.setLayoutManager(manager);
        bind.outerRecycler.swapAdapter(categoryAdapter, false);
    }

//    private void resetAdapter() {
//        FoodCategory foodCategory = new FoodCategory(null,null);
//        ArrayList<FoodCategory> list = new ArrayList<>();
//        list.add(foodCategory);
//        categoryAdapter = new CategoryAdapter(this.getContext(),list);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
//        bind.outerRecycler.setLayoutManager(manager);
//        bind.outerRecycler.setAdapter(categoryAdapter);
//    }


    private void setExample() {
        viewModel.deleteAllFood();

        Food oranges = new Food("Oranges", 4, "bunches", "Fruit");
        Food pineapple = new Food("Pineapple", 3, "", "Fruit");
        Food eggs = new Food("Eggs", 1, "dozen", "Dairy");
        Food cheese = new Food("Cheese", 50, "grams", "Dairy");
        Food pasta = new Food("Pasta", 2, "boxes", "Bread/Grains");
        Food tissues = new Food("Tissues", 1, "box", "For the Home");
        Food potatoes = new Food("Potatoes", 3, "", "Produce");
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
        ShoppingViewModel.insertMeal(fruit_salad);
        ShoppingViewModel.insertMeal(omelette);
        ShoppingViewModel.insertMeal(irish_pasta);

        MealFoodMap fruit_salad_map1 = new MealFoodMap(fruit_salad.getMealId(), oranges.getFoodId());
        MealFoodMap fruit_salad_map2 = new MealFoodMap(fruit_salad.getMealId(), pineapple.getFoodId());
        MealFoodMap omelette1 = new MealFoodMap(omelette.getMealId(), eggs.getFoodId());
        MealFoodMap omelette2 = new MealFoodMap(omelette.getMealId(), cheese.getFoodId());
        MealFoodMap omelette3 = new MealFoodMap(omelette.getMealId(), pasta.getFoodId());
        ShoppingViewModel.insertPair(fruit_salad_map1);
        ShoppingViewModel.insertPair(fruit_salad_map2);
        ShoppingViewModel.insertPair(omelette1);
        ShoppingViewModel.insertPair(omelette2);
        ShoppingViewModel.insertPair(omelette3);



    }

    private void init_categoryList(ArrayList<String> categoryList) {
        categoryList.add("Produce");
        categoryList.add("Fruit");
        categoryList.add("Meat/Fish");
        categoryList.add("Condiments");
        categoryList.add("Beverages");
        categoryList.add("Snacks");
        categoryList.add("Pet Supplies");
        categoryList.add("Baking/Spices");
        categoryList.add("Bread/Grains");
        categoryList.add("Dairy");
        categoryList.add("Frozen Food");
        categoryList.add("Canned Goods");
        categoryList.add("For the Home");
        categoryList.add("Toiletries");
        categoryList.add("Meals included above");
    }

}