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
import androidx.room.PrimaryKey;

import com.example.wguscheduler.R;
import com.example.wguscheduler.activities.AssessmentsActivity;
import com.example.wguscheduler.entities.AssessmentEntity;

import java.util.ArrayList;
import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mAssessmentTextTitle;
        private final CardView mAssessmentCardItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mAssessmentTextTitle = itemView.findViewById(R.id.text_assessment_title);
            mAssessmentCardItem = itemView.findViewById(R.id.card_assessment);

            //onclick event for handling clicking course in the course activity
            itemView.setOnClickListener(v -> {

                int position = getAdapterPosition();
                final AssessmentEntity current = mAssessments.get(position);
                //TODO: set to AssessmentDetailActivity when configured
                Intent intent = new Intent(context, AssessmentsActivity.class);

                //details of the course class to the intent
                intent.putExtra("assessmentId", current.getId());
                intent.putExtra("courseId", current.getCourseId());
                intent.putExtra("title", current.getTitle());
                intent.putExtra("type", current.getType());
                intent.putExtra("description",current.getDescription());

                context.startActivity(intent);

            });
        }
    }
    // declare the inflater, an context and the terms
    private final Context context;
    private final LayoutInflater mInflater;
    private List<AssessmentEntity> mAssessments = new ArrayList<>();

    // inflate the context
    public AssessmentAdapter(Context context, List<AssessmentEntity> courses) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mAssessments = courses;
    }

    public AssessmentAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View assessmentView = inflater.inflate(R.layout.assessment_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(assessmentView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.ViewHolder holder, int position) {
        if(mAssessments!=null){
            AssessmentEntity assessment = mAssessments.get(position);
            CardView cardView = holder.mAssessmentCardItem;
            TextView textViewTitle = holder.mAssessmentTextTitle;
            textViewTitle.setText(assessment.getTitle());
        }else {

            holder.mAssessmentTextTitle.setText("No Assessments!");

        }
    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }

    //update the data in the assessment list
    public void setCourses(List<AssessmentEntity> assessments){
        mAssessments = assessments;
        notifyDataSetChanged();
    }
}
