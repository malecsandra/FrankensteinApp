package com.puskin.frankenstein.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.puskin.frankenstein.R;
import com.puskin.frankenstein.models.AlarmModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 * Created by rakatan on 11.05.2016.
 */
public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {
    // TODO Calculate the date so it looks nice.
    // TODO Deletions must be also sent to the Realm.
    // TODO Don't bother to commit expanded state to the Realm.
    // TODO Surely something else needs doing...

    Context context;
    RealmList<AlarmModel> alarmModels;

    public AlarmAdapter(Context context, RealmList<AlarmModel> alarmModels) {
        this.context = context;
        this.alarmModels = alarmModels;
    }

    @Override
    public AlarmAdapter.AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pill_alarm, parent, false);
        // set the view's size, margins, paddings and layout parameters
        AlarmViewHolder vh = new AlarmViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AlarmAdapter.AlarmViewHolder holder, int position) {
        holder.bind(alarmModels.get(position), position);
    }

    @Override
    public int getItemCount() {
        return alarmModels.size();
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.textView_alarmName)
        TextView textAlarmName;
        @Bind(R.id.imageView_deleteAlarm)
        ImageView buttonDeleteAlarm;
        @Bind(R.id.textView_nextAlarm)
        TextView textNextAlarm;
        @Bind(R.id.imageView_expandDetails)
        ImageView buttonExpandDetails;
        @Bind(R.id.linearLayout_alarmDetails)
        LinearLayout layoutAlarmDetails;
        @Bind(R.id.textView_currentDose)
        TextView textCurrentDose;
        @Bind(R.id.textView_totalDoses)
        TextView textTotalDoses;
        @Bind(R.id.textView_periodicity)
        TextView textPeriodicity;

        public AlarmViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final AlarmModel alarmModel, final int position) {
            toggleDetails(alarmModel.isShowDetails());

            textAlarmName.setText(alarmModel.getDrugName());
            buttonDeleteAlarm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "BOOM!", Toast.LENGTH_SHORT).show();
                }
            });

            textNextAlarm.setText(alarmModel.calculateNextAlarm().toString());
            buttonExpandDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (layoutAlarmDetails.getVisibility() == View.GONE) {
                        alarmModels.get(position).setShowDetails(true);
                        notifyItemChanged(position);
                    }
                }
            });

            layoutAlarmDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(layoutAlarmDetails.getVisibility() == View.VISIBLE){
                        alarmModels.get(position).setShowDetails(false);
                        notifyItemChanged(position);
                    }
                }
            });

            textCurrentDose.setText(alarmModel.getCurrentDose());
            textTotalDoses.setText(alarmModel.getDoses());
            textPeriodicity.setText(alarmModel.getPeriodicity());
        }

        private void toggleDetails(boolean showDetails) {
            if (!showDetails) {
                layoutAlarmDetails.setVisibility(View.GONE);
            } else {
                layoutAlarmDetails.setVisibility(View.VISIBLE);
            }
        }
    }
}
