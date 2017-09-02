package com.example.akhil.smstext;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText et1, et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.enter_mobile);
        et2 = (EditText)findViewById(R.id.enter_message);

        int status = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.SEND_SMS);

        if (status == PackageManager.PERMISSION_GRANTED){

        }else{
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.SEND_SMS}, 12);
        }
    }

    public void sendSms (View v){

        String nums = et1.getText().toString();
        String [] nums_array = nums.split(",");

        for(int i=0; i < nums_array.length; i++){

            Intent sIntent = new Intent(MainActivity.this, MessageIsSentActivity.class);
            Intent dIntent = new Intent(MainActivity.this, MessageDeliveredActivity.class);

            PendingIntent sPendingIntent = PendingIntent.getActivity(MainActivity.this, 0, sIntent, 0);
            PendingIntent dPendingIntent = PendingIntent.getActivity(MainActivity.this, 0, dIntent, 0);

            SmsManager sManager = SmsManager.getDefault();
            sManager.sendTextMessage(nums_array[i],
                    null, et2.getText().toString(), sPendingIntent, dPendingIntent);
        }
    }

    public void call(View v){

        Intent i = new Intent();
        i.setAction(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel: " + et1.getText().toString()));
        startActivity(i);
    }
}
