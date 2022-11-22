//package com.kunle.shoppinglistapp.util;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import com.kunle.shoppinglistapp.R;
//
//import java.util.ArrayList;
//
//public class NavDrawerListAdapter extends BaseAdapter {
//
//    private Context context;
//    private ArrayList<NavDrawerItem> navDrawerItems;
//
//    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems) {
//        this.context = context;
//        this.navDrawerItems = navDrawerItems;
//    }
//
//    @Override
//    public int getCount() {
//        return 0;
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        if(view == null) {
//            LayoutInflater mInflater = (LayoutInflater)
//                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//            view = mInflater.inflate(R.layout.activity_main, null);
//        }
//        return null;
//    }
//}
