package com.puskin.frankenstein.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.puskin.frankenstein.R;
import com.puskin.frankenstein.models.Doctor;

import org.w3c.dom.Text;

import io.realm.RealmList;

/**
 * Created by Alexandra on 09-Apr-16.
 */
public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorHolder> {
    private RealmList<Doctor> doctorList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class DoctorHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView doctorName;
        public TextView doctorSpeciality;
        public TextView doctorPhoneno;
        public TextView doctorEmail;
        public TextView doctorClinic;

        public DoctorHolder(View v) {
            super(v);
            doctorName = (TextView) v.findViewById(R.id.tv_doctorname);
            doctorSpeciality = (TextView) v.findViewById(R.id.tv_speciality);
            doctorPhoneno = (TextView) v.findViewById(R.id.tv_phoneno);
            doctorEmail = (TextView) v.findViewById(R.id.tv_email);
            doctorClinic = (TextView) v.findViewById(R.id.tv_clinic);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DoctorAdapter(RealmList<Doctor> docList) {
        this.doctorList = docList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DoctorAdapter.DoctorHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        DoctorHolder vh = new DoctorHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(DoctorHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Doctor doctor = doctorList.get(position);
        holder.doctorName.setText(doctor.getName() + ' ' + doctor.getSurname());
        holder.doctorSpeciality.setText(doctor.getSpeciality().getSpecialityName());
        holder.doctorPhoneno.setText(doctor.getPhoneNumber());
        holder.doctorEmail.setText(doctor.getEmail());
        holder.doctorClinic.setText(doctor.getClinic().getClinicName());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public RealmList<Doctor> getDoctorList() {
        return doctorList;
    }

    public void setDoctorList(RealmList<Doctor> doctorList) {
        this.doctorList = doctorList;
        notifyDataSetChanged();
    }
}
