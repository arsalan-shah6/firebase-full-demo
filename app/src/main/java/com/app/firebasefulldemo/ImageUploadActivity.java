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
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.List;

public class ImageUploadActivity extends AppCompatActivity {
 ImageView image;
 Button upload_image;
 Uri image_uri=null;
 ProgressDialog progressDialog;
 private static final int GALLERY_IMAGE_CODE=100;
 private static final int CAMERA_IMAGE_CODE=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_image_upload );
        Permission();
        image=findViewById( R.id.image );
        upload_image=findViewById( R.id.upload );
        progressDialog=new ProgressDialog( this );

        upload_image.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
            }
        } );


        image.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImageDialog();

            }
        } );

    }

    private void UploadImage() {
       progressDialog.setMessage( "Uploading" );
       progressDialog.show();
        final String timeStamp=String.valueOf( System.currentTimeMillis() );
        String filePath="Posts/"+"post_"+timeStamp;
        if (image.getDrawable()!=null){
            Bitmap bitmap= ((BitmapDrawable)image.getDrawable()).getBitmap();
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bitmap.compress( Bitmap.CompressFormat.PNG,100,baos );
            byte[] data=baos.toByteArray();
            StorageReference reference=FirebaseStorage.getInstance().getReference().child(  filePath);
            reference.putBytes(data).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());
                    String downloadUri=uriTask.getResult().toString();
                    if (uriTask.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText( ImageUploadActivity.this, "Uploaded", Toast.LENGTH_SHORT ).show();
                        image.setImageURI( null );
                        image_uri=null;

                    }

                }
            } ).addOnFailureListener( new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( ImageUploadActivity.this, ""+e, Toast.LENGTH_SHORT ).show();
                    progressDialog.dismiss();
                }
            } );

        }
    }


    private void PickImageDialog() {
        String  option []= {"Camera", "Gallery"};
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder( this );
        dialogBuilder.setTitle( "Choose Image From" );
        dialogBuilder.setItems( option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0)
                {
                    CameraPickImage();
                }
                if(i==1){
                    GalleryPickImage();
                }
            }
        } );
        dialogBuilder.create().show();


    }

    private void GalleryPickImage() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType( "image/*" );
        startActivityForResult(intent,GALLERY_IMAGE_CODE);
    }

    private void CameraPickImage() {
        ContentValues contentValues=new ContentValues();
        contentValues.put( MediaStore.Images.Media.TITLE,"Temp pick" );
        contentValues.put( MediaStore.Images.Media.TITLE,"Temp desc" );
        image_uri=getContentResolver().insert( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues );
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra( MediaStore.EXTRA_OUTPUT,image_uri );
        startActivityForResult(intent,CAMERA_IMAGE_CODE);
    }
    private void Permission(){
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode==RESULT_OK){
            if (requestCode==GALLERY_IMAGE_CODE){
                image_uri=data.getData();
                image.setImageURI( image_uri );
            }
            if (requestCode==CAMERA_IMAGE_CODE){
                image.setImageURI( image_uri );
            }
        }
        super.onActivityResult( requestCode, resultCode, data );

    }
}