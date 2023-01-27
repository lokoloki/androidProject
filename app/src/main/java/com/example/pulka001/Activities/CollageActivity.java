package com.example.pulka001.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pulka001.R;
import com.example.pulka001.ImageData;

import java.util.ArrayList;

public class CollageActivity extends AppCompatActivity {

    private ArrayList<ImageData> imageDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);

    }
}