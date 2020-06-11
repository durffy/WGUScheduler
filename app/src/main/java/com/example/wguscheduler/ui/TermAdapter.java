package com.example.wguscheduler.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguscheduler.R;
import com.example.wguscheduler.activities.TermDetailsActivity;
import com.example.wguscheduler.entities.TermEntity;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView termTextItem;

        private ViewHolder(View itemView){
            super(itemView);

            termTextItem = itemView.findViewById(R.id.text_term_item);

            //onclick event for handling clicking terms in the terms activity
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                final TermEntity current = mTerms.get(position);
                Intent intent = new Intent(context, TermDetailsActivity.class);

                //details of the term class to the intent
                intent.putExtra("id", current.getId());
                intent.putExtra("title",current.getTitle());
                intent.putExtra("startDate",current.getStartDate());
                intent.putExtra("endDate",current.getEndDate());

                context.startActivity(intent);

            });

        }
    }

    // declar the inflater, an context and the terms
    private final Context context;
    private final LayoutInflater mInflater;
    private List<TermEntity> mTerms;

    // inflate the context
    public TermAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View termView = inflater.inflate(R.layout.term_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(termView);

        return viewHolder;
    }

    // bind the view holder ot the title
    @Override
    public void onBindViewHolder(TermAdapter.ViewHolder termViewHolder, int position) {

        if(mTerms != null){
            TermEntity term = mTerms.get(position);

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
