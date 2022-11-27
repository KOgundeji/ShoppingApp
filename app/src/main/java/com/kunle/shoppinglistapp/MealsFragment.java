package com.kunle.shoppinglistapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kunle.shoppinglistapp.models.Food;
import com.kunle.shoppinglistapp.models.Meal;
import com.kunle.shoppinglistapp.util.MealAdapter;

import java.util.ArrayList;

public class MealsFragment extends Fragment {

    private ArrayList<Food> foodList;
    private ArrayList<Meal> mealList;
    private MealAdapter mealAdapter;
    private RecyclerView mealRecyclerView;

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
        View view = inflater.inflate(R.layout.fragment_meals, container, false);
        mealRecyclerView = view.findViewById(R.id.meal_recyclerView);
        mealList = new ArrayList<>();
        foodList = new ArrayList<>();

        foodList.add(new Food());
        foodList.add(new Food());
        foodList.add(new Food());

        mealList.add(new Meal("Pumpkin Pie",foodList));
        setAdapter(mealList);
        return view;
    }

    private void setAdapter(ArrayList<Meal> mealList) {
        mealAdapter = new MealAdapter(this.getContext(), mealList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this.getContext());
        mealRecyclerView.setLayoutManager(manager);
        mealRecyclerView.setAdapter(mealAdapter);
    }

    public void setAddItemListener(View view) {
        view.setBackgroundColor(Color.GREEN);
    }
}
