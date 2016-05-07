package com.puskin.frankenstein.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puskin.frankenstein.OutRequests;
import com.puskin.frankenstein.R;
import com.puskin.frankenstein.models.Clinic;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by Alexandra on 17-Apr-16.
 */
public class ClinicAdapter extends RecyclerView.Adapter<ClinicAdapter.ClinicHolder> {
    private RealmList<Clinic> clinicList;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ClinicHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView clinicName;
        public TextView clinicPhoneNo;
        public TextView clinicAddress;
        public ImageView infoButton;
        public ImageView mapButton;
        public LinearLayout callPhoneLayout;

        public ClinicHolder(View v) {
            super(v);
            clinicName = (TextView) v.findViewById(R.id.tv_clinicname);
            clinicPhoneNo = (TextView) v.findViewById(R.id.tv_clinicphoneno);
            clinicAddress = (TextView) v.findViewById(R.id.tv_clinicaddress);
            infoButton = (ImageView) v.findViewById(R.id.imageView_info);
            mapButton = (ImageView) v.findViewById(R.id.imageView_map);
            callPhoneLayout = (LinearLayout) v.findViewById(R.id.linearLayout_callPhone);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ClinicAdapter(RealmList<Clinic> clList, Context context) {
        this.clinicList = clList;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ClinicAdapter.ClinicHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clinic_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ClinicHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ClinicHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final int finalPosition = position;
        Clinic clinic = clinicList.get(position);
        holder.clinicName.setText(clinic.getClinicName());
        holder.clinicPhoneNo.setText(clinic.getPhoneNumber());
        holder.clinicAddress.setText(clinic.getClinicAddress());

        holder.infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                dialogBuilder.setTitle(clinicList.get(finalPosition).getClinicName());
                dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.clinic_info_dialog, null);
                TextView clinicAddress = (TextView) dialogView.findViewById(R.id.textView_clinicAddress);
                TextView clinicPhones = (TextView) dialogView.findViewById(R.id.textView_clinicPhoneNo);
                TextView clinicInstructions = (TextView) dialogView.findViewById(R.id.textView_clinicInstructions);

                clinicAddress.setText(clinicList.get(finalPosition).getClinicAddress());
                clinicPhones.setText(clinicList.get(finalPosition).getPhoneNumber());
                clinicInstructions.setText(clinicList.get(finalPosition).getDetails());

                dialogBuilder.setView(dialogView);

                AlertDialog alertDialog = dialogBuilder.create();


                alertDialog.show();
            }
        });

        holder.mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Geocoder geocoder = new Geocoder(context);
                    List<Address> addresses;
                    addresses = geocoder.getFromLocationName(clinicList.get(finalPosition).getClinicAddress(), 1);

                    double latitude = 0;
                    double longitude = 0;

                    if (addresses.size() > 0) {
                        latitude = addresses.get(0).getLatitude();
                        longitude = addresses.get(0).getLongitude();
                    }

                    Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?z=16&q=" + Uri.encode(clinicList.get(finalPosition).getClinicAddress()));
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

        holder.callPhoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OutRequests.dialPhone(context, clinicList.get(finalPosition).getPhoneNumber());
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return clinicList.size();
    }

    public RealmList<Clinic> getClinicList() {
        return clinicList;
    }

    public void setClinicList(RealmList<Clinic> clinicList) {
        this.clinicList = clinicList;
        notifyDataSetChanged();
    }
}
