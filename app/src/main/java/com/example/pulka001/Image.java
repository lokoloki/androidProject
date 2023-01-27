package com.example.pulka001;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Image extends AppCompatActivity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        img = findViewById(R.id.image);

        Bundle bundle = getIntent().getExtras();
        String img1 = bundle.getString("img").toString();
        Bitmap bmp = betterImageDecode(img1);

        img.setImageBitmap(bmp);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Image.this);
                View editView = View.inflate(Image.this, R.layout.note_input_xml, null);
                alert.setView(editView);
                Button bt1 = (Button) editView.findViewById(R.id.btnOK);
                EditText et1 = (EditText) editView.findViewById(R.id.note1);
                EditText et2 = (EditText) editView.findViewById(R.id.note2);

                bt1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("aba" , et1.getText().toString());
                        Log.d("aba" , et2.getText().toString());

                    }
                });
                alert.show();
            }
        });
    }

    private Bitmap betterImageDecode(String filePath){
        Bitmap myBitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        myBitmap = BitmapFactory.decodeFile(filePath, options);
        return myBitmap;
    }
}