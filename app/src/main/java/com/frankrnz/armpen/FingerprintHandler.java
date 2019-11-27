package com.frankrnz.armpen;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;


public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
    Verify activity = new Verify();
    private CancellationSignal cancellationSignal;
    private Context context;
    EditText val1,val2,val3,val4,val5,currentText;
    TextView mID;



    public FingerprintHandler(Context mContext) {
        context = mContext;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }



    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        onCheckFocus();
        Toast.makeText(context,
                "Authentication error\n" + errString,
                Toast.LENGTH_LONG).show();
        this.update("Fingerprint Authentication error\n" + errString);

    }


    public void onCheckFocus(){
        val1  = ((Verify)context).findViewById(R.id.valueOne);
        val1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if (val1.getText().toString().length() == 1)
                {
                    Toast.makeText(context,
                            "my value is\n" + val1,
                            Toast.LENGTH_LONG).show();
                    val1.clearFocus();
                    val2.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
    }


    @Override
    public void onAuthenticationFailed() {
        onCheckFocus();
        Toast.makeText(context,
                "Authentication failed",
                Toast.LENGTH_LONG).show();

        this.update("Fingerprint Authentication failed\n" );
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId,
                                     CharSequence helpString) {
        onCheckFocus();
        Toast.makeText(context,
                "Authentication help\n" + helpString,
                Toast.LENGTH_LONG).show();
    }


    @Override
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {
        this.update(" " );
        militaryId();
    }

    /*  Method to update Error text message on Auth failed  */
    public void update(String e) {
        TextView txtView =  ((Verify)context).findViewById(R.id.errorText);
        txtView.setText(e);
    }

    public void renderUserDetails(String details) {
        mID  = ((Verify) this.context).findViewById(R.id.militaryID);
        mID.setText(details);
    }

    public void militaryId(){


        val1  = ((Verify)context).findViewById(R.id.valueOne);
        val2  = ((Verify)context).findViewById(R.id.valueTwo);
        val3  = ((Verify)context).findViewById(R.id.valueThree);
        val4  = ((Verify)context).findViewById(R.id.valueFour);
        val5  = ((Verify)context).findViewById(R.id.valueFive);

        String value = val1.getText().toString() + val2.getText().toString()
                + val3.getText().toString() + val4.getText().toString() +
                val5.getText().toString();





        if(value.contains("XWUPZ")){

            Intent myIntent = new Intent(context, MainActivity.class);
            context.startActivity(myIntent);

//            this.renderUserDetails("Military ID : " + value );

        }
        else if (value.contains("2BNEX")){

            Intent myIntent = new Intent(context, MainActivity.class);
            context.startActivity(myIntent);

        }
        else{
            this.update("Invalid Military ID" );
        }

    }





}







