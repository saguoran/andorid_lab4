package com.example.JoannaLuKangleJiang_COMP304Sec002_Lab4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterForTest extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<Test> tests = new ArrayList<>();

    private static CustomAdapter.ClickListener clickListener;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = view.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
        public TextView getTextView() {
            return textView;
        }
    }

    // constructor
    public CustomAdapterForTest() { }

    public Test getTestAtPosition(int position) {
        return tests.get(position);
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_row_item, parent, false);

        return new CustomAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(tests.get(position).getDisplayName());
    }

    public void setTests(List<Test> tests){
        this.tests = tests;
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tests.size();
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(CustomAdapter.ClickListener clickListener) {
        CustomAdapterForTest.clickListener = clickListener;
    }
}
