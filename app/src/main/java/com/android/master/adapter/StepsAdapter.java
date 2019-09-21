package com.android.master.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.master.R;
import com.android.master.interfaces.IMasterCallbacks;
import com.android.master.models.Step;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Step> steps = new ArrayList<>();
    private IMasterCallbacks iMasterCallbacks;

    public StepsAdapter(List<Step> steps, IMasterCallbacks iMasterCallbacks) {
        this.steps.addAll(steps);
        this.iMasterCallbacks = iMasterCallbacks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StepViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((StepViewHolder) holder).setData(steps.get(position));
    }

    @Override
    public int getItemCount() {
        return (!steps.isEmpty()) ? steps.size() : 0;
    }

    class StepViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_stepIndicator)
        TextView tv_stepIndicator;
        @BindView(R.id.tv_stepName)
        TextView tv_stepName;
        @BindView(R.id.cv_step)
        CardView cv_step;

        StepViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setData(Step step) {
            tv_stepIndicator.setText(String.valueOf((step.getId() + 1)));
            tv_stepName.setText(step.getShortDescription());

            cv_step.setOnClickListener(v -> iMasterCallbacks.showStep(step));
        }

    }

}

