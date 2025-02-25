package com.example.shayanths.represent;

/**
 * Created by ShayanthS on 3/2/16.
 */
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;


public class PhoneListenerService extends WearableListenerService {

    //   WearableListenerServices don't need an iBinder or an onStartCommand: they just need an onMessageReceieved.
    private static final String TOAST = "/send_toast";
    private static final String SHAKE = "/send_shake";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

        Log.d("T", "in PhoneListenerService, got: " + messageEvent.getPath());
        if( messageEvent.getPath().equalsIgnoreCase(TOAST) ) {

            String person_data = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//            String[] stringArray = candidate_id.split("\\s+");
//            int candID;
//            String dataSet = "";
//            if (stringArray.length > 1){
//                candID = new Integer(stringArray[0]);
//                dataSet = stringArray[1];
//            }
//            else{
//                candID = new Integer(stringArray[0]);
//            }
            Intent intent = new Intent(this, DetailedCandidateView.class );
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("person_data", person_data);
//            intent.putExtra("Cand_ID", candID);
//            intent.putExtra("dataSet", dataSet);
            startActivity(intent);

//            // Value contains the String we sent over in WatchToPhoneService, "good job"
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//
//            // Make a toast with the String
//            Context context = getApplicationContext();
//            int duration = Toast.LENGTH_SHORT;
//
//            Toast toast = Toast.makeText(context, value, duration);
//            toast.show();

            // so you may notice this crashes the phone because it's
            //''sending message to a Handler on a dead thread''... that's okay. but don't do this.
            // replace sending a toast with, like, starting a new activity or something.
            // who said skeleton code is untouchable? #breakCSconceptions

        }else if (messageEvent.getPath().equalsIgnoreCase(SHAKE)) {
            String zipCode = new String(messageEvent.getData(), StandardCharsets.UTF_8);

            Intent intent = new Intent(this, CandidateView.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("zipCode", zipCode);
            startActivity(intent);

        }else if (messageEvent.getPath().equalsIgnoreCase("/send-pets")){
            final PutDataMapRequest putRequest = PutDataMapRequest.create("/send-pets");
            final DataMap map = putRequest.getDataMap();
        }
        else {
            super.onMessageReceived( messageEvent );
        }

    }
}
