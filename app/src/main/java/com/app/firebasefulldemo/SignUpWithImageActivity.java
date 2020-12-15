package com.app.firebasefulldemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpWithImageActivity extends AppCompatActivity {
    CircleImageView profile_image;
    EditText rollNos,yourName,yourCourse,yourContact;
    Button signup_button;
    AlertDialog.Builder builder;
    ProgressDialog progressDialog;
    private static final int GALLERY_IMAGE_CODE=100;
    private static final int CAMERA_IMAGE_CODE=101;
    Uri image_uri=null;
     String timeStamp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_sign_up_with_image );
        profile_image=findViewById( R.id. profile_image);
        rollNos=findViewById( R.id.roll_no );
        yourName=findViewById( R.id.name );
        yourCourse=findViewById( R.id.course );
        yourContact=findViewById( R.id.contact );
        signup_button=findViewById( R.id.signup_button );
        timeStamp=String.valueOf( System.currentTimeMillis() );
        progressDialog=new ProgressDialog( this );
        progressDialog.setTitle( "File Uploader" );
        
        signup_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                String RollNo=rollNos.getText().toString().trim();
                String Name=yourName.getText().toString().trim();
                String Course=yourCourse.getText().toString().trim();
                String Contact=yourContact.getText().toString().trim();
                if (RollNo.isEmpty()){
                    rollNos.setError( "Enter Rollno" );
                }else if (Name.isEmpty()){
                    yourName.setError( "Enter Name" );
                }else if (Course.isEmpty()){
                    yourCourse.setError( "Enter Course" );
                }else if (Contact.isEmpty()){
                    yourContact.setError( "Enter Contact" );
                }else {
                    UploadSignupData(RollNo,Name,Course,Contact);
                }
            }
        } );



        builder=new AlertDialog.Builder( this );
        builder.setTitle( "Choose Source From" );
        Permissions();

        profile_image.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String[] optoin={"Camera","Gallery"};
               builder.setItems( optoin, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {
                       if (i==0){
                           CameraPick();

                       }else if (i==1){
                           GalleryPick();
                       }
                   }
               } );
               builder.create().show();
            }
        } );
    }

    private void UploadSignupData(String rollNo, String name, String course, String contact) {
        progressDialog.show();
        FirebaseStorage firebaseStorage=FirebaseStorage.getInstance();
        StorageReference storageReference=firebaseStorage.getReference("Image").child( timeStamp );
        storageReference.putFile( image_uri ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                storageReference.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        FirebaseDatabase database=FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference=database.getReference("Users");
                           String link=uri.toString();
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put( "Name",name );
                        hashMap.put( "Course",course );
                        hashMap.put( "Contact",contact );
                        hashMap.put( "ImageLink",link );
                        databaseReference.child( rollNo ).setValue( hashMap);

                        yourName.setText( "" );
                        yourCourse.setText( "" );
                        yourContact.setText( "" );
                        rollNos.setText( "" );


                    }
                } );

            }
        } ).addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                long percentage=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage( "Uploaded :"+percentage +"%");


            }
        } );
    }


    private void GalleryPick() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType( "image/*" );
        startActivityForResult(intent,GALLERY_IMAGE_CODE);
    }

    private void CameraPick() {
        ContentValues contentValues=new ContentValues();
        contentValues.put( MediaStore.Images.Media.TITLE,"Temp pick" );
        contentValues.put( MediaStore.Images.Media.TITLE,"Temp desc" );
        image_uri=getContentResolver().insert( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues );
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra( MediaStore.EXTRA_OUTPUT,image_uri );
        startActivityForResult(intent,CAMERA_IMAGE_CODE);
    }

    private void Permissions() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode==RESULT_OK){
            if (requestCode==GALLERY_IMAGE_CODE){
                image_uri=data.getData();
                profile_image.setImageURI( image_uri );
            }
            if (requestCode==CAMERA_IMAGE_CODE){
                profile_image.setImageURI( image_uri );
            }
        }
        super.onActivityResult( requestCode, resultCode, data );

    }
}