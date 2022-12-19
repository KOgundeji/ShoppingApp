package com.kunle.shoppinglistapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kunle.shoppinglistapp.R;
import com.kunle.shoppinglistapp.models.GroceryList;
import com.kunle.shoppinglistapp.models.ShoppingViewModel;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private final Context context;
    private final List<GroceryList> foodPerCategory;

    public ItemAdapter(Context context, List<GroceryList> foodPerCategory) {
        this.context = context;
        this.foodPerCategory = foodPerCategory;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inner_cardview, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        int textColor = ContextCompat.getColor(context, R.color.text_color);
        String name = foodPerCategory.get(position).getName();
        String quantity = foodPerCategory.get(position).getQuantity();
        String parenthesis = "(" + quantity + ")";

        SpannableString first_part = new SpannableString(name);

        SpannableString second_part = new SpannableString(parenthesis);
        second_part.setSpan(new ForegroundColorSpan(Color.GRAY),
                0, parenthesis.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        second_part.setSpan(new AbsoluteSizeSpan(14, true),
                0, parenthesis.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        CharSequence finalText = TextUtils.concat(first_part, " ", second_part);

        holder.item.setText(finalText);
    }

    @Override
    public int getItemCount() {
        return foodPerCategory.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView item;
        private final CheckBox checkBox;
        private final ImageView clickable_pencil;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.shoppingList_item);
            checkBox = itemView.findViewById(R.id.checkBox);
            clickable_pencil = itemView.findViewById(R.id.shoppingList_edit);


//            item.setOnLongClickListener();


            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShoppingViewModel.deleteGrocery(foodPerCategory.get(getAdapterPosition()));
                }
            });

            clickable_pencil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GroceryList currentGrocery = foodPerCategory.get(getAdapterPosition());

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View new_view = inflater.inflate(R.layout.add_new_meal, null);

                    LinearLayout confirm = new_view.findViewById(R.id.grocery_edit_confirm);
                    LinearLayout back = new_view.findViewById(R.id.grocery_edit_back);

                    builder.setView(new_view);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });



                }
            });

        }


    }
}
