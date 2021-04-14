package com.example.JoannaLuKangleJiang_COMP304Sec002_Lab4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<StockInfo> stockInfos = new ArrayList<>();
    private static ClickListener clickListener;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final RadioButton radioButton;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            radioButton = view.findViewById(R.id.radioButton);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                }
//            });
        }
        public RadioButton getRadioButton() {
            return radioButton;
        }
    }

    public CustomAdapter() {
    }

    public StockInfo getStockInfoAtPosition(int position) {
        return stockInfos.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_row_item, parent, false);

        return new ViewHolder(view);
    }

    public int selectedPosition = -1;
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        RadioButton radioButton = viewHolder.getRadioButton();
        radioButton.setText(stockInfos.get(position).getStockSymbol());
        radioButton.setChecked(position == selectedPosition);
        radioButton.setTag(position);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = (Integer)view.getTag();
                notifyDataSetChanged();

                clickListener.onItemClick(view, stockInfos.get(selectedPosition));
            }
        });
    }



    // set patients
    public void setStockInfos(List<StockInfo> patients) {
        this.stockInfos = patients;
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return stockInfos.size();
    }

    // interface for ClickListener
    public interface ClickListener {
        void onItemClick(View v, StockInfo stockInfo);
    }

    // setOnItemClickListener
    public void setOnItemClickListener(ClickListener clickListener) {
        CustomAdapter.clickListener = clickListener;
    }
}
