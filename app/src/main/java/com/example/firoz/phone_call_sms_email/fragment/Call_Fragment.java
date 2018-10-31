package com.example.firoz.phone_call_sms_email.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.firoz.phone_call_sms_email.R;

public class Call_Fragment extends Fragment {

    private static final int REQUEST_CALL = 1010;
    private View view;
    private ImageButton callButton;
    private EditText etNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_call, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initView();
        addListener();
    }


    private void initView() {

        callButton = view.findViewById(R.id.btn_call);
        etNumber = view.findViewById(R.id.etNumber);
    }

    private void addListener() {

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callTheNumber();
            }
        });

    }

    private void callTheNumber() {

        String number = etNumber.getText().toString();

        // --- return if text field is empty & show error message on the text field
        if (number.isEmpty()) {
            etNumber.setError("Pls enter number");
            etNumber.requestFocus();
            return;
        }

        // ----- at first check the permission
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

               /*

               If we are in activity then request permission will be
               ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);

             */

            // inside fragment, we just call requestPermissions

            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            // --- we have the permission
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + number));
            startActivity(intent);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == REQUEST_CALL) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                callTheNumber();
            } else {
                Toast.makeText(getActivity(), "You don't have permission", Toast.LENGTH_LONG).show();

            }

        }
    }
}
