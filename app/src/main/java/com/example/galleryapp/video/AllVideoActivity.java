package com.example.galleryapp.video;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.os.Environment.MEDIA_MOUNTED;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.galleryapp.AllphotoActivity;
import com.example.galleryapp.R;
import com.example.galleryapp.adapter.GalleryAdapter;
import com.example.galleryapp.video.adapter.AllVideoAdapter;

import java.util.ArrayList;

public class AllVideoActivity extends AppCompatActivity {


    RecyclerView recycler;
    AllVideoAdapter adapter;
    ArrayList<String> image_list;
    TextView totalimages;
    GridLayoutManager grid_manager;
    TextView nodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_video);

        recycler = findViewById(R.id.gallery_recyclerview);
        image_list = new ArrayList<>();
        totalimages = findViewById(R.id.gallery_total_images);
        nodata = findViewById(R.id.nodata);



        getImage();


    }


    private void getImage() {
        final String[] columns = {MediaStore.Video.Media.DATA, MediaStore.Video.Media._ID};
        final String orderBy = MediaStore.Video.Media.DATE_TAKEN + " DESC";

        Cursor cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
        int count = cursor.getCount();

        totalimages.setText("Total items: " + count);

        for (int i = 0; i < count; i++) {
            cursor.moveToPosition(i);
            int columnindex = cursor.getColumnIndex(MediaStore.Video.Media.DATA);
            image_list.add(cursor.getString(columnindex));
        }

        if (image_list.size() == 0){
            nodata.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
        }else {
            nodata.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
            adapter = new AllVideoAdapter(AllVideoActivity.this, image_list);
            grid_manager = new GridLayoutManager(AllVideoActivity.this, 3);
            recycler.setAdapter(adapter);
            recycler.setLayoutManager(grid_manager);
        }
        cursor.close();
    }


}