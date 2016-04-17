package com.puskin.frankenstein;

import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Alexandra on 17-Apr-16.
 */
public class ClinicInfoDialog extends AppCompatDialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.clinic_info_dialog, container, false);
        int title = getArguments().getInt("title");
        getDialog().setTitle(title);
        return view;
    }
}
