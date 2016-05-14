package com.puskin.frankenstein.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.puskin.frankenstein.models.AppointmentTestSet;
import com.puskin.frankenstein.models.MedicalTestModel;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Alexandra on 07-May-16.
 */
public class LabTestAdapter extends ExpandableRecyclerAdapter<LabTestAdapter.AppointmentTestSetHolder, LabTestAdapter.MedicalTestModelHolder> {
    Context context;

    public LabTestAdapter(Context context, ArrayList<AppointmentTestSet> parentItemList) {
        super(parentItemList);
        this.context = context;
    }

    @Override
    public AppointmentTestSetHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View v = LayoutInflater.from(parentViewGroup.getContext())
                .inflate(R.layout.test_set_item, parentViewGroup, false);

        return new AppointmentTestSetHolder(v);
    }

    @Override
    public MedicalTestModelHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View v = LayoutInflater.from(childViewGroup.getContext())
                .inflate(R.layout.test_item, childViewGroup, false);

        return new MedicalTestModelHolder(v);    }

    @Override
    public void onBindParentViewHolder(AppointmentTestSetHolder parentViewHolder, int position, ParentListItem parentListItem) {
        AppointmentTestSet appointmentTestSet = (AppointmentTestSet) parentListItem;
        parentViewHolder.bind(appointmentTestSet);
    }

    @Override
    public void onBindChildViewHolder(MedicalTestModelHolder childViewHolder, int position, Object childListItem) {
        MedicalTestModel medicalTestModel = (MedicalTestModel) childListItem;
        childViewHolder.bind(medicalTestModel);
    }

    public static class AppointmentTestSetHolder extends ParentViewHolder {

        @Bind(R.id.textView_date)
        TextView dateText;
        @Bind(R.id.textView_doctorName)
        TextView doctorName;
        @Bind(R.id.textView_speciality)
        TextView specialityText;
        @Bind(R.id.textView_tsdiagnostic)
        TextView diagnosticText;

        public AppointmentTestSetHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(AppointmentTestSet appointmentTestSet){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MMM.yyyy");
            dateText.setText(dateFormat.format(appointmentTestSet.getMedicalTestDate()));
            doctorName.setText(appointmentTestSet.getDoctor());
            specialityText.setText(appointmentTestSet.getSpeciality());
            diagnosticText.setText(appointmentTestSet.getDiagnostic());
        }
    }

    public static class MedicalTestModelHolder extends ChildViewHolder {
        @Bind(R.id.textView_category)
        TextView categoryText;
        @Bind(R.id.textView_testName)
        TextView testName;
        @Bind(R.id.textView_minValue)
        TextView minValue;
        @Bind(R.id.textView_maxValue)
        TextView maxValue;
        @Bind(R.id.textView_unitMeasure)
        TextView unitMeasure;
        @Bind(R.id.textView_result)
        TextView resultText;
        @Bind(R.id.linearLayout_measurements)
        LinearLayout measurements;

        public MedicalTestModelHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(MedicalTestModel medicalTestModel){
            resultText.setTextColor(Color.BLACK);

            categoryText.setText(medicalTestModel.getCategoryName());
            testName.setText(medicalTestModel.getTestName());

            if(medicalTestModel.getMinValue() == -1 && medicalTestModel.getMaxValue() == -1){
                measurements.setVisibility(View.GONE);
                resultText.setText(medicalTestModel.getResult());

            }else{
                minValue.setText(Float.toString(medicalTestModel.getMinValue()));
                maxValue.setText(Float.toString(medicalTestModel.getMaxValue()));
                unitMeasure.setText(medicalTestModel.getUnitMeasure());

                try {
                    float resultValue = Float.parseFloat(medicalTestModel.getResult());
                    Log.d("DBG", "Resulting value: " + resultValue);
                    if(resultValue < medicalTestModel.getMinValue() || resultValue > medicalTestModel.getMaxValue()){
                        resultText.setTextColor(Color.RED);
                    }
                    resultText.setText(medicalTestModel.getResult());
                }
                catch(NumberFormatException e){
                    Log.d("DBG", "Unable to parse result into value, using text instead");
                    resultText.setText(medicalTestModel.getResult());
                }

            }
        }
    }
}
