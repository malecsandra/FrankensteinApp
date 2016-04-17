package com.puskin.frankenstein;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Alexandra on 17-Apr-16.
 */
public class OutRequests {
    public static void dialPhone(Context context, String phone){
        Intent i = new Intent(Intent.ACTION_DIAL);
        i.setData(Uri.parse("tel:" + phone));
        context.startActivity(i);
    }
}
