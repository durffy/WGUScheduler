package com.example.wguscheduler.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguscheduler.R;
import com.example.wguscheduler.activities.TermDetailsActivity;
import com.example.wguscheduler.entities.TermEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/*
TODO:
    issue loading multiple terms from Utilities.SampleData, likely that the viewholder is setup
    incorrectly. Look into this.
 */

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView termTextItem;
        private final CardView termCardItem;

        private ViewHolder(View itemView){
            super(itemView);

            termTextItem = itemView.findViewById(R.id.text_term_item);
            termCardItem = itemView.findViewById(R.id.card_term_item);

            //onclick event for handling clicking terms in the terms activity
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                final TermEntity current = mTerms.get(position);
                Intent intent = new Intent(context, TermDetailsActivity.class);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                //details of the term class to the intent
                intent.putExtra("termId", current.getId());
                intent.putExtra("title",current.getTitle());
                intent.putExtra("startDate",formatter.format(current.getStartDate()));
                intent.putExtra("endDate",formatter.format(current.getEndDate()));

                context.startActivity(intent);

            });

        }
    }

    // declare the inflater, an context and the terms
    private final Context context;
    private final LayoutInflater mInflater;
    private List<TermEntity> mTerms = new ArrayList<>();

    // inflate the context
    public TermAdapter(Context context, List<TermEntity> mTerms) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mTerms = mTerms;
    }

    public TermAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    //called each time a viewholder has to be created
    @NonNull
    @Override
    public TermAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View termView = inflater.inflate(R.layout.term_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(termView);

        return viewHolder;
    }

    // Bind the Textview to the viewholder, e.g. display elements.
    @Override
    public void onBindViewHolder(TermAdapter.ViewHolder termViewHolder, int position) {

        if(mTerms != null){
            TermEntity term = mTerms.get(position);
            CardView cardView = termViewHolder.termCardItem;
            TextView textView = termViewHolder.termTextItem;
            textView.setText(term.getTitle());

        }else {

            termViewHolder.termTextItem.setText("No Terms!");

        }

    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    //update the data in the terms list
    public void setTerms(List<TermEntity> terms){
        mTerms = terms;
        notifyDataSetChanged();
    }

}
