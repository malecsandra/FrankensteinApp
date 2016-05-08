package com.puskin.frankenstein.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.puskin.frankenstein.R;
import com.puskin.frankenstein.models.AppointmentModel;
import com.puskin.frankenstein.models.Clinic;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 * Created by Alexandra on 07-May-16.
 */
public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder> {
    private ArrayList<AppointmentModel> appointmentList;
    private Context context;

    public AppointmentAdapter(ArrayList<AppointmentModel> appointmentList, Context context) {
        this.appointmentList = appointmentList;
        this.context = context;
    }

    @Override
    public AppointmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_item, parent, false);

        return new AppointmentHolder(v);
    }

    @Override
    public void onBindViewHolder(AppointmentHolder holder, int position) {
        AppointmentModel appointmentModel = appointmentList.get(position);
        holder.bind(appointmentModel, context);
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public static class AppointmentHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.textView_date)
        public TextView date;
        @Bind(R.id.textView_doctorName)
        public TextView doctorName;
        @Bind(R.id.textView_speciality)
        public TextView speciality;
        @Bind(R.id.linearLayout_appInfo)
        public LinearLayout appInfo;
        @Bind(R.id.imageView_map)
        public ImageView map;
        @Bind(R.id.imageView_clinicMap)
        public ImageView clinicMap;
        @Bind(R.id.card_view)
        CardView root;

        public AppointmentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final AppointmentModel appointmentModel, final Context context) {
            if (appointmentModel.getStatusId() == 0) {
                root.setCardBackgroundColor(context.getResources().getColor(R.color.colorAppointment));
            } else {
                root.setCardBackgroundColor(Color.parseColor("#f9f9f9"));
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MMM.yyyy");
            date.setText(dateFormat.format(appointmentModel.getAppointmentDate()));
            doctorName.setText(appointmentModel.getDoctor());
            speciality.setText(appointmentModel.getSpeciality());

            map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Geocoder geocoder = new Geocoder(context);
                        List<Address> addresses;
                        addresses = geocoder.getFromLocationName(appointmentModel.getClinicAddress(), 1);

                        double latitude = 0;
                        double longitude = 0;

                        if (addresses.size() > 0) {
                            latitude = addresses.get(0).getLatitude();
                            longitude = addresses.get(0).getLongitude();
                        }

                        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?z=16&q=" + Uri.encode(appointmentModel.getClinicAddress()));
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                            context.startActivity(mapIntent);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            clinicMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Clicked clinic map", Toast.LENGTH_SHORT).show();
                }
            });

            appInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Clicked info", Toast.LENGTH_SHORT).show();
                }
            });

            appInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(appointmentModel.getAppointmentDetailsList().size() > 0) {
                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                        dialogBuilder.setTitle("Details");
                        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                        View dialogView = inflater.inflate(R.layout.appointmet_details, null);
                        TextView diagnosticText = (TextView) dialogView.findViewById(R.id.textView_diagnostic);
                        TextView commentsText = (TextView) dialogView.findViewById(R.id.textView_comments);

                        diagnosticText.setText(appointmentModel.getAppointmentDetailsList().get(0).getDiagnostic());

                        String comments = appointmentModel.getAppointmentDetailsList().get(0).getComments();
                        if (comments != null && comments.length() > 0)
                            commentsText.setText(appointmentModel.getAppointmentDetailsList().get(0).getComments());
                        else
                            commentsText.setText("N/A");

                        dialogBuilder.setView(dialogView);

                        AlertDialog alertDialog = dialogBuilder.create();

                        alertDialog.show();
                    }
                    else{
                        Toast.makeText(context, "No Details to show", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}

