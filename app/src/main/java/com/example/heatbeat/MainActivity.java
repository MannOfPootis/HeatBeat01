package com.example.heatbeat;

import static android.bluetooth.BluetoothGattDescriptor.PERMISSION_READ;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.heatbeat.ui.main.SectionsPagerAdapter;
import com.example.heatbeat.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<ModelAudio> audioArrayList =new ArrayList<ModelAudio>();;
    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;

        if (checkPermission())
        {
            setAudio();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //bazinga
                Snackbar.make(view, "empt lisz", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                /*for (ModelAudio song:audioArrayList
                     ) {

                    Snackbar.make(view, "sdssddsdn", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }*/

            }
        });
    }
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case  PERMISSION_READ: {
                if (grantResults.length > 0 && permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        // Toast.makeText(getApplicationContext(), "Please allow storage permission", Toast.LENGTH_LONG).show();
                    } else
                    {
                        setAudio();
                    }
                }
            }
        }
    }

    public void setAudio() {
       // Toast.makeText(this, "was executed", Toast.LENGTH_SHORT).show();
        getAudioFiles();



    }
    //ArrayList<ModelAudio> audioArrayList2 ;
    public void getAudioFiles() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        //looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            do {

                //List<String> list
                //Adding elements in the List


                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));

                ModelAudio modelAudio = new ModelAudio();
                modelAudio.setaudioTitle(title);
                modelAudio.setaudioArtist(artist);
                modelAudio.setaudioUri(Uri.parse(url));
                modelAudio.setaudioDuration(duration);

                modelAudio.setAvgSpeed( 12.0/*dataBasing.getSpeedByName(title)*/);
                Toast.makeText(this, modelAudio.audioTitle, Toast.LENGTH_SHORT).show();
                audioArrayList.add(modelAudio);





            } while (cursor.moveToNext());
        }

       /* AudioAdapter adapter = new AudioAdapter(this, audioArrayList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AudioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, View v) {
                playAudio(pos);
            }
        });*/
    }
}