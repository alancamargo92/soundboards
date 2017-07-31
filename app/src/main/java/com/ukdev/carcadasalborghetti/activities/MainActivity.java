package com.ukdev.carcadasalborghetti.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.app.AlertDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;
import com.ukdev.carcadasalborghetti.R;
import com.ukdev.carcadasalborghetti.model.Carcada;
import com.ukdev.carcadasalborghetti.adapters.CarcadaAdapter;
import com.ukdev.carcadasalborghetti.database.Database;

import java.io.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class MainActivity extends AppCompatActivity implements CarcadaAdapter.OnItemClickListener {

    private final int REQUEST_CODE = 123;
    private MediaPlayer player;
    private int[] sounds;
    private File exportedFile;
    private File tmpDir = new File(Environment.getExternalStorageDirectory() +
                                           "/tmp_carcadas/");
    private Database db;
    private int selectedItem;
    private CarcadaAdapter.OnItemClickListener onItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        onItemClickListener = this;
        sounds = getIntegerArray(R.array.sounds);
        setSupportActionBar(toolbar);
        setPlayPauseButton();
        setRecyclerView();
        showAds();
        db = new Database(this, null);
        if (db.getRowCount() == 0)
            showTip();
        deleteTempFiles();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                showAppInfo();
                break;
            case R.id.menu_videos:
                openYouTube();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    copyToExternalStorage(getIntegerArray(R.array.sounds)[selectedItem]);
                    share(getExportedFile(), "Alborghetti - " +
                            getResources().getStringArray(R.array.titles)[selectedItem]);
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null && player.isPlaying())
            player.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteTempFiles();
    }

    private void openYouTube() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getString(R.string.url)));
        startActivity(intent);
    }

    private void showAppInfo() {
        try {
            PackageManager manager = getPackageManager();
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            String version = info.versionName;
            String title = String.format("%1$s %2$s", getString(R.string.app_name), version);
            AlertDialog.Builder dialogue = new AlertDialog.Builder(this);
            dialogue.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Do nothing
                }
            });
            dialogue.setTitle(title);
            dialogue.setMessage(R.string.about);
            dialogue.show();
        } catch (PackageManager.NameNotFoundException e) {
            // Damn! Something really wrong happened here
            e.printStackTrace();
        }
    }

    private void showAds() {
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    /**
     * Sets actions to playPauseButton
     */
    private void setPlayPauseButton() {
        FloatingActionButton playPauseButton =
                findViewById(R.id.playPauseButton);
        assert playPauseButton != null;
        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player != null && player.isPlaying()) {
                    Toast.makeText(getBaseContext(), R.string.pause,
                                   Toast.LENGTH_SHORT).show();
                    player.pause();
                } else if (player != null && !player.isPlaying()) {
                    Toast.makeText(getBaseContext(), R.string.playing,
                                   Toast.LENGTH_SHORT).show();
                    player.start();
                }
            }
        });
    }

    /**
     * Shows a tip
     */
    private void showTip() {
        AlertDialog.Builder popup = new AlertDialog.Builder(this);
        popup.setTitle(R.string.tip);
        popup.setMessage(R.string.tipText);
        popup.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing
            }
        });
        popup.setNegativeButton(R.string.doNotShowAgain,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        try {
                                            db.add();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
        popup.show();
    }

    /**
     * Deletes all temporary files
     */
    private void deleteTempFiles() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                                                  Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                delete();
            }
        } else
            delete();
    }

    /**
     * Deletes the shared file
     */
    private void delete() {
        if (tmpDir.isDirectory()) {
            String[] children = tmpDir.list();
            for (String child : children)
                new File(tmpDir, child).delete();
        }
    }

    /**
     * When the back button is pressed
     */
    @Override
    public void onBackPressed() {
        if (player != null && player.isPlaying())
            player.stop();
        deleteTempFiles();
        super.onBackPressed();
    }

    /**
     * Sets the recycler view contents
     */
    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        String[] titles = new String[getIntegerArray(R.array.titles).length];
        String[] lengths = getResources().getStringArray(R.array.lengths);
        Carcada[] carcadas = new Carcada[lengths.length];
        for (int i = 0; i < getResources().getStringArray(R.array.titles).length; i++) {
            titles[i] = (i + 1) + ". " +
                    getResources().getStringArray(R.array.titles)[i];
            carcadas[i] = new Carcada(titles[i], lengths[i]);
        }
        CarcadaAdapter adapter = new CarcadaAdapter(this, carcadas, onItemClickListener);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Gets resources as an integer array
     * @param id - int
     * @return values
     */
    private int[] getIntegerArray(int id) {
        TypedArray typedArray = getResources().obtainTypedArray(id);
        int[] values = new int[typedArray.length()];
        for (int i = 0; i < values.length; i++)
            values[i] = typedArray.getResourceId(i, 0);
        typedArray.recycle();
        return values;
    }

    /**
     * Plays a given audio track
     * @param position - int
     */
    private void playSound(int position) {
        player = MediaPlayer.create(this, sounds[position]);
        player.start();
    }

    /**
     * Shares a given audio track
     * @param file - File
     * @param text - String
     */
    private void share(File file, String text) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("audio/*");
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            uri = FileProvider.getUriForFile(this, getPackageName(), file);
        else
            uri = Uri.fromFile(file);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Carcada - Alborghetti");
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(shareIntent, "Compartilhar carcada"));
    }

    /**
     * Gets the exported file
     * @return exported file
     */
    private File getExportedFile() {
        return exportedFile;
    }

    /**
     * Copies a file from app storage to external storage
     * @param id - int
     */
    private void copyToExternalStorage(int id) {
        String baseDir =
                Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/tmp_carcadas/";
        File dir = new File(baseDir);
        if (!dir.exists())
            dir.mkdir();
        File soundFile = new File(baseDir, id + ".mp3");
        exportedFile = soundFile;
        try {
            byte[] readData = new byte[1024 * 500];
            InputStream in = getResources().openRawResource(id);
            FileOutputStream out = new FileOutputStream(soundFile);
            int j = in.read(readData);
            while (j != -1) {
                out.write(readData, 0, j);
                j = in.read(readData);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int position) {
        if (player != null)
            player.release();
        AudioManager manager = (AudioManager)
                getSystemService(Context.AUDIO_SERVICE);
        int volume = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (volume == 0)
            Toast.makeText(getBaseContext(), R.string.volume_0,
                           Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getBaseContext(),
                           R.string.playing, Toast.LENGTH_SHORT).show();
        playSound(position);
    }

    @Override
    public void onItemLongClick(int position) {
        selectedItem = position;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                                                  Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                                                  new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                  REQUEST_CODE
                );
            } else {
                copyToExternalStorage(getIntegerArray(R.array.sounds)[position]);
                share(getExportedFile(), "Alborghetti - " +
                        getResources().getStringArray(R.array.titles)[position]);
            }
        } else {
            copyToExternalStorage(getIntegerArray(R.array.sounds)[position]);
            share(getExportedFile(), "Alborghetti - " +
                    getResources().getStringArray(R.array.titles)[position]);
        }
    }

}