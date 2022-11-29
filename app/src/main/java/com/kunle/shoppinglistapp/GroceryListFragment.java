package com.kunle.shoppinglistapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kunle.shoppinglistapp.databinding.FragmentGroceryListBinding;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.models.RecyclerCategory;
import com.kunle.shoppinglistapp.models.RecyclerItem;
import com.kunle.shoppinglistapp.adapters.CategoryAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GroceryListFragment extends Fragment {

//    private AutoCompleteTextView add_item;
//    private ImageView addButton;
//    private RecyclerView outerRecycler;
    private CategoryAdapter categoryAdapter;
    private Map<String, ArrayList<RecyclerItem>> categoryMap;
    private ArrayList<RecyclerItem> recyclerItemList;
    private ArrayList<RecyclerCategory> recyclerCategories;
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
        bind = FragmentGroceryListBinding.inflate(inflater,container,false);
        initVariables();
        setExample();
        setAdapter(setRecyclerCategoryList());
        return bind.getRoot();
    }

    private void initVariables() {
        recyclerItemList = new ArrayList<>();
        recyclerCategories = new ArrayList<>();

        categoryMap = new HashMap<>();
        categoryList = new ArrayList<>();
        init_categoryList(categoryList);
    }


    private ArrayList<RecyclerCategory> setRecyclerCategoryList() {

        for (RecyclerItem item : recyclerItemList) {
            String category = item.getCategory();
            if (categoryMap.get(category) != null) {
                ArrayList<RecyclerItem> itemList = categoryMap.get(category);
                itemList.add(item);
                categoryMap.replace(category, itemList);
            } else {
                ArrayList<RecyclerItem> itemList = new ArrayList<>();
                itemList.add(item);
                categoryMap.put(category, itemList);
            }
        }

        for (String category : categoryMap.keySet()) {
            RecyclerCategory cat = new RecyclerCategory(category, categoryMap.get(category));
            recyclerCategories.add(cat);
        }

        return recyclerCategories;
    }


    public void setAddItemListener(View view) {
        view.setBackgroundColor(Color.GREEN);
    }


    private void setAdapter(ArrayList<RecyclerCategory> categories) {
        categoryAdapter = new CategoryAdapter(this.getContext(), categories);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        bind.outerRecycler.setLayoutManager(manager);
        bind.outerRecycler.setAdapter(categoryAdapter);
    }

    private void setExample() {

        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 4, "Oranges",
                "bunch", "Fruit"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 3, "Pineapple",
                "", "Fruit"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 1, "Eggs",
                "dozen", "Dairy"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 50, "Cheese",
                "grams", "Dairy"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 2, "Pasta",
                "boxes", "Bread/Grains"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 1, "Tissues",
                "box", "For the Home"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 3, "Potatoes",
                "", "Produce"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 1, "Strawberries",
                "carton", "Fruit"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 1, "Light Bulb",
                "", "For the Home"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 5, "Oui Yogurts",
                "", "Dairy"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 1, "Ginger Ale",
                "12-pack", "Beverages"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 3, "Onions",
                "", "Produce"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 2, "Frozen Pizza",
                "carton", "Frozen Food"));
        recyclerItemList.add(new RecyclerItem(RecyclerItem.FOOD, 1, "Ketchup",
                "bottle", "Condiments"));


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
    }
}