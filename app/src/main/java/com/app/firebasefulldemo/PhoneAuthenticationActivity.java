package com.app.firebasefulldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class PhoneAuthenticationActivity extends AppCompatActivity {
    EditText getPhone;
 Button getOtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_phone_authentication );
        getOtp=findViewById( R.id.get_otp );
        getPhone=findViewById( R.id.input_phone );



        getOtp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String GetPhone=getPhone.getText().toString().trim();
                if (GetPhone.isEmpty()){
                    getPhone.setError( "Enter Phone No" );

                }

                startActivity( new Intent(PhoneAuthenticationActivity.this,ManageOtpActivity.class) );
                GetOTP(GetPhone);
                SendOTP();
            }
        } );

       
    }

    private void SendOTP() {
      // PhoneAuthProvider.getInstance().verifyPhoneNumber( "+92"+getPhone.getText().toString().trim() );
    }

    private void GetOTP(String getPhone) {
        Intent intent=new Intent(PhoneAuthenticationActivity.this,ManageOtpActivity.class);
        intent.putExtra( "key",getPhone );
        startActivity( intent );
    }


}