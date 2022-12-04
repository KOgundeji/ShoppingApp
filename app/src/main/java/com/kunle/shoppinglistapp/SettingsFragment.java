package com.kunle.shoppinglistapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

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
        bind.setLifecycleOwner(this);
        viewModel = new ShoppingViewModel(this.getActivity().getApplication());

        final int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;

        viewModel.checkSettingsExist(Settings.DARK_MODE).observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
                    bind.darkModeCheck.setChecked(true);
                } else if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
                    bind.darkModeCheck.setChecked(false);
                } else {
                }
                bind.darkModeCheck.jumpDrawablesToCurrentState();
            }
        });

        setSettingsOnClickListenersandObservers();
        return bind.getRoot();
    }

    private void setSettingsOnClickListenersandObservers() {

        bind.darkModeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dark_mode in Settings table
                if (bind.darkModeCheck.isChecked()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    ShoppingViewModel.updateSettings(new Settings(Settings.DARK_MODE, 1));
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    ShoppingViewModel.updateSettings(new Settings(Settings.DARK_MODE, 0));
                }
            }
        });

        viewModel.checkSetting(Settings.DARK_MODE).observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 0) {
                    bind.darkModeCheck.setChecked(false);
                } else if (integer == 1) {
                    bind.darkModeCheck.setChecked(true);
                } else {
                }
                bind.darkModeCheck.jumpDrawablesToCurrentState();
            }
        });

        bind.screenOnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //screen_on in Settings table
                if (bind.screenOnCheck.isChecked()) {
                    getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    ShoppingViewModel.updateSettings(new Settings(Settings.SCREEN_ON, 1));
                } else {
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    ShoppingViewModel.updateSettings(new Settings(Settings.SCREEN_ON, 0));
                }
            }
        });

        viewModel.checkSetting(Settings.SCREEN_ON).observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 0) {
                    bind.screenOnCheck.setChecked(false);
                } else if (integer == 1) {
                    bind.screenOnCheck.setChecked(true);
                } else {
                }
                bind.screenOnCheck.jumpDrawablesToCurrentState();
            }
        });

        bind.removeCategoriesCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //remove_categories in settings table
                if (bind.removeCategoriesCheck.isChecked()) {
                    ShoppingViewModel.updateSettings(
                            new Settings(Settings.NO_CATEGORIES, 1));
                } else {
                    ShoppingViewModel.updateSettings(
                            new Settings(Settings.NO_CATEGORIES, 0));
                }
            }
        });

        viewModel.checkSetting(Settings.NO_CATEGORIES).observe(requireActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 0) {
                    bind.removeCategoriesCheck.setChecked(false);
                } else if (integer == 1) {
                    bind.removeCategoriesCheck.setChecked(true);
                } else {
                }
                bind.removeCategoriesCheck.jumpDrawablesToCurrentState();
            }
        });
    }
}