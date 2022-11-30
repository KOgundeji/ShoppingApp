package com.kunle.shoppinglistapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kunle.shoppinglistapp.databinding.FragmentGroceryListBinding;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.GroceryList;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.models.FoodCategory;
import com.kunle.shoppinglistapp.adapters.CategoryAdapter;
import com.kunle.shoppinglistapp.models.ShoppingViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroceryListFragment extends Fragment {

    //    private AutoCompleteTextView add_item;
//    private ImageView addButton;
//    private RecyclerView outerRecycler;
    private ShoppingViewModel viewModel;
    private CategoryAdapter categoryAdapter;
    private Map<String, ArrayList<Food>> categoryMap;
    private ArrayList<Food> foodItemList;
    private LiveData<List<GroceryList>> groceryList;
    private ArrayList<FoodCategory> recyclerCategories;
    private ArrayList<String> categoryList;
    private ArrayList<Food> foodList;
    private ArrayList<Meal> mealList;
    private FragmentGroceryListBinding bind;

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
        bind = FragmentGroceryListBinding.inflate(inflater, container, false);
        initVariables();
        setExample();
        setAdapter(seperateFoodintoCategories());

        viewModel = new ViewModelProvider.AndroidViewModelFactory(this.getActivity().getApplication())
                .create(ShoppingViewModel.class);
//
//        viewModel.getAllMeals().observe(this.getViewLifecycleOwner(), new Observer<List<Meal>>() {
//            @Override
//            public void onChanged(List<Meal> meals) {
//
//            }
//        });
        Food new1 = new Food("Oranges", 4, "bunches", "Fruit");
        ShoppingViewModel.insertFood(new1);


        return bind.getRoot();
    }

    private void initVariables() {
        ShoppingViewModel viewModel = new ShoppingViewModel(this.requireActivity().getApplication());
        foodItemList = viewModel.getAllFood();
        recyclerCategories = new ArrayList<>();

        categoryMap = new HashMap<>();
        categoryList = new ArrayList<>();
        init_categoryList(categoryList);
    }


    private ArrayList<FoodCategory> seperateFoodintoCategories() {

        for (Food item : foodItemList) {
            String category = item.getCategory();
            if (categoryMap.get(category) != null) {
                ArrayList<Food> itemList = categoryMap.get(category);
                itemList.add(item);
                categoryMap.replace(category, itemList);
            } else {
                ArrayList<Food> itemList = new ArrayList<>();
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


    public void setAddItemListener(View view) {
        view.setBackgroundColor(Color.GREEN);
    }


    private void setAdapter(ArrayList<FoodCategory> categories) {
        categoryAdapter = new CategoryAdapter(this.getContext(), categories);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        bind.outerRecycler.setLayoutManager(manager);
        bind.outerRecycler.setAdapter(categoryAdapter);
    }

    private void setExample() {
        ShoppingViewModel.insertFood(new Food("Oranges", 4, "bunches", "Fruit"));
        ShoppingViewModel.insertFood(new Food("Pineapple", 3, "", "Fruit"));
        ShoppingViewModel.insertFood(new Food("Eggs", 1, "dozen", "Dairy"));
        ShoppingViewModel.insertFood(new Food("Cheese", 50, "grams", "Dairy"));
        ShoppingViewModel.insertFood(new Food("Pasta", 2, "boxes", "Bread/Grains"));
        ShoppingViewModel.insertFood(new Food("Tissues", 1, "box", "For the Home"));
        ShoppingViewModel.insertFood(new Food("Potatoes", 3, "", "Produce"));
//        foodItemList.add(new Food("Strawberries", 1, "carton", "Fruit"));
//        foodItemList.add(new Food("Light Bulb", 1, "", "For the Home"));
//        foodItemList.add(new Food("Oui Yogurts", 5, "", "Dairy"));
//        foodItemList.add(new Food("Ginger Ale", 1, "12-pack", "Beverages"));
//        foodItemList.add(new Food("Onions", 3, "", "Produce"));
//        foodItemList.add(new Food("Frozen Pizza", 2, "box", "Frozen Food"));
//        foodItemList.add(new Food("Ketchup", 1, "bottle", "Condiments"));
//        foodItemList.add(new Food("Oranges", 12, "bunches", "Fruit"));

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