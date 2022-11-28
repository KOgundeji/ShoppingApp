package com.kunle.shoppinglistapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kunle.shoppinglistapp.databinding.FragmentMealsBinding;
import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.adapters.MealAdapter;
import com.kunle.shoppinglistapp.models.ShoppingViewModel;

import java.util.ArrayList;
import java.util.List;

public class MealsFragment extends Fragment {

    private FragmentMealsBinding bind;
    private ShoppingViewModel shoppingViewModel;
    private ArrayList<Food> foodList;
    private ArrayList<Meal> mealList;
    private MealAdapter mealAdapter;

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

        //----------------------------------------------//
        shoppingViewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication())
                .create(ShoppingViewModel.class);
        shoppingViewModel.getAllMeals().observe(this.getViewLifecycleOwner(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                Log.d("TAG", "onChanged: " + meals.get(0).getName()); //there's nothing there yet
            }
        });
        //----------------------------------------------//

        mealList = new ArrayList<>();
        foodList = new ArrayList<>();

        foodList.add(new Food());
        foodList.add(new Food());
        foodList.add(new Food());

        mealList.add(new Meal("Pumpkin Pie", foodList));
        setAdapter(mealList);
        return bind.getRoot();
    }

    private void setAdapter(ArrayList<Meal> mealList) {
        mealAdapter = new MealAdapter(this.getContext(), mealList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        bind.mealRecyclerView.setLayoutManager(manager);
        bind.mealRecyclerView.setAdapter(mealAdapter);
    }

    private void setAddItemListener(View view) {
        view.setBackgroundColor(Color.GREEN);
    }

    public void onMealOptionSelect() {

    }
}
