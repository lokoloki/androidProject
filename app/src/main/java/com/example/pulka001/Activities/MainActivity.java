package com.example.pulka001.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.pulka001.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout cameraBtn;
    private RelativeLayout albumsBtn;
    private RelativeLayout newAlbums;
    private RelativeLayout notes;
    private RelativeLayout collage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cameraBtn = findViewById(R.id.buttonCamera);
        albumsBtn = findViewById(R.id.albumsBtn);
        newAlbums = findViewById(R.id.newAlbums);
        notes = findViewById(R.id.notes);
        collage = findViewById(R.id.collage);
        collage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CollageActivity.class);
                startActivity(intent);
            }
        });
        newAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Albums2Activity.class);
                startActivity(intent);
            }
        });
        albumsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this , AlbumsActivity.class);
                startActivity(intent);
            }
        });
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this , NotesActivity.class);
                startActivity(intent);
            }
        });
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Wybirz źródło zdjęcia!");
                String[] opcje = {"Aparat" , "Galeria"};
                alert.setItems(opcje, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(opcje[i] == "Aparat"){
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if(intent.resolveActivity(getPackageManager()) != null){
                                startActivityForResult(intent,200);
                            }
                        }else{
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent, 100);
                        }
                    }
                });
                alert.show();

            }
        });
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 100);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 100);

        checkPermission(Manifest.permission.CAMERA, 100);




    }

    public void checkPermission(String permission, int requestCode){
        if(ContextCompat.checkSelfPermission(MainActivity.this,permission) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, requestCode);

        }else{
            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File dir = new File(pic, "Pulka");
        String[] opcje = new String[dir.listFiles().length];
        for(int i=0; i< dir.listFiles().length; i++){
            opcje[i] = dir.listFiles()[i].getName();
        }

        if(requestCode == 200){
            if(resultCode == RESULT_OK){
                Bundle extras = data.getExtras();
                Bitmap b = (Bitmap) extras.get("data");
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Wybierz folder do zapisu?");
                alert.setItems(opcje, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        b.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
                        String d = df.format(new Date());
                        FileOutputStream fs = null;
                        try {
                            fs = new FileOutputStream(dir.listFiles()[i].getPath() + "/" + d + ".jpg");
                            fs.write(byteArray);
                            fs.close();

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                alert.show();

            }
        }else if(requestCode == 100){
            if(resultCode == RESULT_OK){
                Uri imgData = data.getData();
                try {
                    InputStream stream = getContentResolver().openInputStream(imgData);
                    Bitmap b = BitmapFactory.decodeStream(stream);
                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    alert.setTitle("Wybierz folder do zapisu?");
                    alert.setItems(opcje, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            b.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            byte[] byteArray = stream.toByteArray();
                            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
                            String d = df.format(new Date());
                            FileOutputStream fs = null;
                            try {
                                fs = new FileOutputStream(dir.listFiles()[i].getPath() + "/" + d + ".jpg");
                                fs.write(byteArray);
                                fs.close();

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    alert.show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}