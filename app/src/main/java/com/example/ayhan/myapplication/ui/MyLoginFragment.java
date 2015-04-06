package com.example.ayhan.myapplication.ui;

import android.app.Activity;
import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by ayhan on 06/04/15.
 */
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayhan.myapplication.R;

public class MyLoginFragment extends android.support.v4.app.Fragment {

    private EditText username;
    private EditText password;
    private Button login;
    private TextView loginLockedTV;
    private TextView attemptsLeftTV;
    private TextView numberOfRemainingLoginAttemptsTV;
    int numberOfRemainingLoginAttempts = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_main, container, false);

        setupVariables();
        return rootView;
    }

    public void authenticateLogin(View view) {
        if (username.getText().toString().equals("admin") &&
                password.getText().toString().equals("admin")) {
            Toast.makeText(getActivity(), "Hello admin!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Seems like you 're not admin!",
                    Toast.LENGTH_SHORT).show();
            numberOfRemainingLoginAttempts--;
            attemptsLeftTV.setVisibility(View.VISIBLE);
            numberOfRemainingLoginAttemptsTV.setVisibility(View.VISIBLE);
            numberOfRemainingLoginAttemptsTV.setText(Integer.toString(numberOfRemainingLoginAttempts));

            if (numberOfRemainingLoginAttempts == 0) {
                login.setEnabled(false);
                loginLockedTV.setVisibility(View.VISIBLE);
                loginLockedTV.setBackgroundColor(Color.RED);
                loginLockedTV.setText("LOGIN LOCKED!!!");
            }
        }
    }

    private void setupVariables() {
        username = (EditText) getActivity().findViewById(R.id.usernameET);
        password = (EditText) getActivity().findViewById(R.id.passwordET);
        login = (Button) getActivity().findViewById(R.id.loginBtn);
        loginLockedTV = (TextView) getActivity().findViewById(R.id.loginLockedTV);
        attemptsLeftTV = (TextView) getActivity().findViewById(R.id.attemptsLeftTV);
 //       numberOfRemainingLoginAttemptsTV = (TextView) getActivity().findViewById(R.id.numberOfRemainingLoginAttemptsTV);
//        numberOfRemainingLoginAttemptsTV.setText(Integer.toString(numberOfRemainingLoginAttempts));
    }

}