package com.app.firebasefulldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
 Button insertData,uploadImage,Signup_with_image,phoneAuth,retrieveDataToReciclerView,rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        insertData=findViewById( R.id.insert );
        uploadImage=findViewById( R.id.upload_image );
        Signup_with_image=findViewById( R.id.singup );
        phoneAuth=findViewById( R.id.phoneAuth );
        retrieveDataToReciclerView=findViewById( R.id.retrieve_to_recycle_view );
        rv=findViewById( R.id.RV );

        rv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                                              startActivity( new Intent(MainActivity.this,RVActivity.class) );
                                           }
        } );


        retrieveDataToReciclerView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(MainActivity.this,RetrieveDataActivity.class) );

            }
        } );
        phoneAuth.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(MainActivity.this,PhoneAuthenticationActivity.class) );
            }
        } );

        Signup_with_image.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 startActivity( new Intent(MainActivity.this,SignUpWithImageActivity.class) );
            }
        } );

        insertData.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(MainActivity.this,InsertActivity.class) );

            }
        } );
        uploadImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(MainActivity.this,ImageUploadActivity.class) );
            }
        } );
    }
}