package com.example.firoz.phone_call_sms_email.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.firoz.phone_call_sms_email.R;

public class SMS_Fragment extends Fragment {

    private static final int SEND_SMS_REQUEST = 22;
    private View view;
    private ImageButton sendButton;
    private EditText etNumber, etMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sms, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initView();
        addListener();
    }


    private void initView() {

        sendButton = view.findViewById(R.id.btn_send);
        etNumber = view.findViewById(R.id.etNumber);
        etMessage = view.findViewById(R.id.etMessage);
    }

    private void addListener() {

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMesage();
            }
        });

    }

    private void sendMesage() {

        String number = etNumber.getText().toString();
        String message = etMessage.getText().toString().trim();

        // --- check number field
        if (number.isEmpty()) {
            etNumber.setError("Please enter a number");
            etNumber.requestFocus();
            return;
        }

        // --- check message field
        if (message.isEmpty()) {
            etMessage.setError("Please enter message");
            etMessage.requestFocus();
            return;
        }


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {

            // --- we have permission
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, message, null, null);
            Toast.makeText(getContext(), "SMS Sent", Toast.LENGTH_SHORT).show();
        } else {

            /*
            //--------   In activity, we use this method

            // ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_REQUEST);

            */

            // inside fragment, we just use this to show permission dialog
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_REQUEST);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == SEND_SMS_REQUEST) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // --- if user give permission to send sms
                sendMesage();
            } else {
                Toast.makeText(getContext(), "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
