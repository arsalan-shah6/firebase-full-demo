package com.app.firebasefulldemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.firebasefulldemo.Adapter.DatraModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class InsertActivity extends AppCompatActivity {
 EditText names,subjects,snos;
 Button insert;
 FirebaseDatabase firebaseDatabase;
 DatabaseReference databaseReference;
 ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_insert );
        names=findViewById( R.id.name );
        subjects=findViewById( R.id.subject );
        insert=findViewById( R.id.insert );
        snos=findViewById( R.id.sno );
        progressDialog=new ProgressDialog( this );
        progressDialog.setMessage( "Please Wait" );
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("User");



        insert.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Sno=snos.getText().toString().trim();
                String Name=names.getText().toString().trim();
                String Subject=subjects.getText().toString();
                if (Sno.isEmpty()){
                    snos.setError( "Enter SNo" );
                }else if(Name.isEmpty()){
                    names.setError( "Enter Name" );
                }else if (Subject.isEmpty()){
                    subjects.setError( "Enter Subject" );
                }else {
                    Insert(Sno,Name,Subject);
                }

    }

    private void Insert(String sno, String name, String subject) {
        progressDialog.show();

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put( "Name",name );
        hashMap.put( "Subject",subject );




        databaseReference.child( sno ).setValue(hashMap  ).addOnCompleteListener( new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    snos.setText("");
                    names.setText("");
                    subjects.setText("");
                    Toast.makeText( InsertActivity.this, "Data Inserted", Toast.LENGTH_SHORT ).show();


                }

            }
        } ).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText( InsertActivity.this, ""+e, Toast.LENGTH_SHORT ).show();

            }
        } );


    }
        } );
    }

    }
