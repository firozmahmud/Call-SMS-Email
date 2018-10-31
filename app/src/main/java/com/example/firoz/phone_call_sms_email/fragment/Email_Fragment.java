package com.example.firoz.phone_call_sms_email.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.firoz.phone_call_sms_email.R;
import com.example.firoz.phone_call_sms_email.utils.InternetChecker;

public class Email_Fragment extends Fragment {

    private View view;
    private EditText etEmailAddress, etEmailSubject, etEmailMessage;
    private ImageButton sendButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_email, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initView();
        addListener();
    }

    private void addListener() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMail();
            }
        });
    }

    private void sendMail() {

        String emailAddress = etEmailAddress.getText().toString().trim();
        String emailSubject = etEmailSubject.getText().toString().trim();
        String emailMessage = etEmailMessage.getText().toString().trim();


        if (emailAddress.isEmpty()) {
            etEmailAddress.setError("Enter email address");
            etEmailAddress.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            etEmailAddress.setError("Enter a valid email");
            etEmailAddress.requestFocus();
            return;
        }

        if (emailSubject.isEmpty()) {
            etEmailSubject.setError("Enter a subject");
            etEmailSubject.requestFocus();
            return;
        }
        if (emailMessage.isEmpty()) {
            etEmailMessage.setError("Enter message");
            etEmailMessage.requestFocus();
            return;
        }


        String[] To = new String[]{emailAddress};

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_EMAIL, To);
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        intent.putExtra(Intent.EXTRA_TEXT, emailMessage);

        if (!InternetChecker.isInternetConnected(getContext())) {

            Toast.makeText(getContext(), "You have no internet connection", Toast.LENGTH_SHORT).show();
            return;
        }

        // ---- try to send mail
        try {
            startActivity(intent);
            // finish();
            Log.i("send mail", "ok");

            Toast.makeText(getContext(), "Mail sending...", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Mail not send", Toast.LENGTH_SHORT).show();
        }

    }

    private void initView() {

        sendButton = view.findViewById(R.id.sent_btn);
        etEmailAddress = view.findViewById(R.id.etEmailId);
        etEmailSubject = view.findViewById(R.id.etEmailSubjectId);
        etEmailMessage = view.findViewById(R.id.etEmailMessage);
    }
}
