package com.puskin.frankenstein.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder;
import com.puskin.frankenstein.R;
import com.puskin.frankenstein.models.AppointmentTreatment;
import com.puskin.frankenstein.models.MedicalTestModel;
import com.puskin.frankenstein.models.TreatmentDetailModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alexandra on 15-May-16.
 */
public class TreatmentAdapter extends ExpandableRecyclerAdapter<TreatmentAdapter.TreatmentViewHolder, TreatmentAdapter.DetailsViewHolder> {
    Context context;

    public TreatmentAdapter(Context context, ArrayList<AppointmentTreatment> parentTreatments) {
        super(parentTreatments);
        this.context = context;
    }

    @Override
    public TreatmentViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View v = LayoutInflater.from(parentViewGroup.getContext())
                .inflate(R.layout.item_treatment_set, parentViewGroup, false);

        return new TreatmentViewHolder(v);
    }

    @Override
    public DetailsViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View v = LayoutInflater.from(childViewGroup.getContext())
                .inflate(R.layout.item_treatment_details, childViewGroup, false);

        return new DetailsViewHolder(v);
    }

    @Override
    public void onBindParentViewHolder(TreatmentViewHolder parentViewHolder, int position, ParentListItem parentListItem) {
        AppointmentTreatment appointmentTreatment = (AppointmentTreatment) parentListItem;
        parentViewHolder.bind(appointmentTreatment);
    }

    @Override
    public void onBindChildViewHolder(DetailsViewHolder childViewHolder, int position, Object childListItem) {
        TreatmentDetailModel treatmentDetailModel = (TreatmentDetailModel) childListItem;
        childViewHolder.bind(treatmentDetailModel);
    }

    public static class TreatmentViewHolder extends ParentViewHolder {

        @Bind(R.id.textView_date)
        TextView dateText;
        @Bind(R.id.textView_doctorName)
        TextView doctorName;
        @Bind(R.id.textView_speciality)
        TextView specialityText;
        @Bind(R.id.textView_treatmentDiagnostic)
        TextView diagnosticText;

        public TreatmentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(AppointmentTreatment appointmentTreatment) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MMM.yyyy");
            dateText.setText(dateFormat.format(appointmentTreatment.getTreatmentDate()));
            doctorName.setText(appointmentTreatment.getDoctorName());
            specialityText.setText(appointmentTreatment.getSpecialityName());
            diagnosticText.setText(appointmentTreatment.getDiagnostic());
        }
    }

    public static class DetailsViewHolder extends ChildViewHolder {
        @Bind(R.id.textView_drugName)
        TextView textViewDrugName;
        @Bind(R.id.textView_pillsPerDose)
        TextView textViewPillsPerDose;
        @Bind(R.id.textView_totalDoses)
        TextView textViewTotalDoses;
        @Bind(R.id.textView_periodicity)
        TextView textViewPeriodicity;
        @Bind(R.id.textView_periodicityType)
        TextView textViewPeriodicityType;
        @Bind(R.id.linearLayout_measurements)
        LinearLayout linearLayoutMeasurements;
        @Bind(R.id.card_view)
        CardView cardView;

        public DetailsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(TreatmentDetailModel treatmentDetailModel) {
            textViewDrugName.setText(treatmentDetailModel.getDrugName());
            textViewPillsPerDose.setText(Integer.toString(treatmentDetailModel.getDose()));
            textViewTotalDoses.setText(Integer.toString(treatmentDetailModel.getTablets()));
            textViewPeriodicity.setText(Integer.toString(treatmentDetailModel.getPeriodicity()));

            int periodicityMeasurement = treatmentDetailModel.getPeriodicityType();
            switch(periodicityMeasurement){
                case 0:
                    textViewPeriodicityType.setText("Hour(s)");
                    break;
                case 1:
                    textViewPeriodicityType.setText("Day(s)");
                    break;
                default:
                    textViewPeriodicityType.setText("N/A");
                    break;
            }
        }
    }
}
