package com.kunle.shoppinglistapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.kunle.shoppinglistapp.databinding.FragmentSettingsBinding;
import com.kunle.shoppinglistapp.models.Settings;
import com.kunle.shoppinglistapp.models.ShoppingViewModel;

public class SettingsFragment extends Fragment {

    private ShoppingViewModel viewModel;

    private FragmentSettingsBinding bind;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        bind = FragmentSettingsBinding.inflate(inflater, container, false);
        viewModel = new ShoppingViewModel(this.getActivity().getApplication());

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        boolean dark_mode_flag = false;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            bind.darkModeCheck.setChecked(true);
            dark_mode_flag = true;
        }

        if (!viewModel.checkSettingsExist("dark_mode")) {
            ShoppingViewModel.insertSettings(new Settings("remove_categories", dark_mode_flag));
        }

        setSettingsOnClickListeners();
        return bind.getRoot();
    }

    private void setSettingsOnClickListeners() {
        bind.darkModeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dark_mode in Settings table
                if (bind.darkModeCheck.isChecked()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    ShoppingViewModel.updateSettings(new Settings("dark_mode",true));
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    ShoppingViewModel.updateSettings(new Settings("dark_mode",false));
                }
            }
        });

        bind.screenOnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //screen_on in Settings table
                if (bind.screenOnCheck.isChecked()) {
                    getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    ShoppingViewModel.updateSettings(new Settings("screen_on",true));
                } else {
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    ShoppingViewModel.updateSettings(new Settings("screen_on",false));
                }
            }
        });

        bind.removeCategoriesCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //remove_categories in settings table
                if (bind.removeCategoriesCheck.isChecked()) {
                    ShoppingViewModel.updateSettings(
                            new Settings("remove_categories", true));
                } else {
                    ShoppingViewModel.updateSettings(
                            new Settings("remove_categories", false));
                }
            }
        });
    }
}