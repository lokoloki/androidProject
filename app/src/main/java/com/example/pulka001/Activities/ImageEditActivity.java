package com.example.pulka001.Activities;

import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pulka001.R;
import com.example.pulka001.TestAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ImageEditActivity extends AppCompatActivity {

    private String albumName;
    TestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_edit);

        ActionBar actionBar =  getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        albumName = bundle.getString("albumName").toString();


        File folder = new File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES ) + File.separator + "Pulka" + File.separator + albumName);

        ArrayList<File> list = new ArrayList<>(Arrays.asList(folder.listFiles()));


        adapter = new TestAdapter (
                ImageEditActivity.this,
                R.layout.listviewedit,
                list
        );

        ListView lv = findViewById(R.id.listview);
        lv.invalidateViews();
        lv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}