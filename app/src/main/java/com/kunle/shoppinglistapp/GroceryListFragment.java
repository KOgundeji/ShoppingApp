package com.kunle.shoppinglistapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
    private ShoppingViewModel viewModel;
    private CategoryAdapter categoryAdapter;
    private Map<String, ArrayList<GroceryList>> categoryMap;
    private LiveData<List<Food>> foodItemList;
    private List<GroceryList> temp_groceryList;
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
//        bind = DataBindingUtil.inflate(inflater,R.layout.fragment_grocery_list,container,false);
        bind = FragmentGroceryListBinding.inflate(inflater, container, false);
        bind.setLifecycleOwner(this);
        recyclerCategories = new ArrayList<>();
        categoryMap = new HashMap<>();
        categoryList = new ArrayList<>();
        init_categoryList(categoryList);
        setListeners();
        viewModel = new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
                .create(ShoppingViewModel.class);
                setExample();


        viewModel.getAllGroceries().observe(getActivity(), new Observer<List<GroceryList>>() {
            @Override
            public void onChanged(List<GroceryList> groceries) {
//                groceryList = viewModel.getAllGroceries();
                setAdapter(seperateFoodintoCategories(groceries));
            }
        });

        return bind.getRoot();
    }


    private ArrayList<FoodCategory> seperateFoodintoCategories(List<GroceryList> temp_groceryList) {

        for (int i = 0; i < temp_groceryList.size(); i++) {
            GroceryList item = temp_groceryList.get(i);
            String category = item.getCategory();
            Log.d("Category", "cat: " + category);
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


    public void setListeners() {
        bind.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GroceryList temp = new GroceryList("Banana",3,"","Fruit");
                ShoppingViewModel.insertGrocery(temp);
            }
        });
    }


    private void setAdapter(ArrayList<FoodCategory> categories) {
        categoryAdapter = new CategoryAdapter(this.getContext(), categories);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        bind.outerRecycler.setLayoutManager(manager);
        bind.outerRecycler.setAdapter(categoryAdapter);
    }

    private void setExample() {
//        viewModel.deleteAllFood();
//        GroceryList gL = new GroceryList("Oranges", 4, "bunches", "Fruit");
//        gL.setFoodId(2);
//        ShoppingViewModel.deleteGrocery(gL);
//        ShoppingViewModel.insertGrocery(new GroceryList("Oranges", 4, "bunches", "Fruit"));
//        ShoppingViewModel.insertGrocery(new GroceryList("Pineapple", 3, "", "Fruit"));
//        ShoppingViewModel.insertGroceries(new GroceryList("Eggs", 1, "dozen", "Dairy"));
//        ShoppingViewModel.insertGroceries(new GroceryList("Cheese", 50, "grams", "Dairy"));
//        ShoppingViewModel.insertGroceries(new GroceryList("Pasta", 2, "boxes", "Bread/Grains"));
//        ShoppingViewModel.insertGroceries(new GroceryList("Tissues", 1, "box", "For the Home"));
//        ShoppingViewModel.insertGroceries(new GroceryList("Potatoes", 3, "", "Produce"));
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