package com.example.adity.bluetoothmusic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;


public class RcdMusicFrag extends Fragment {
    private static final int UPDATE_FREQUENCY = 500;
    private static final int STEP_VALUE = 4000;

//    private RcdMediaCursorAdapter mediaAdapter = null;
    private TextView selectedFile = null;
    private SeekBar seekbar = null;
    private MediaPlayer player = null;
    private ImageButton playButton = null;
    private ImageButton prevButton = null;
    private ImageButton nextButton = null;
    private ListView listView;
    private CharSequence songname=null;

    private boolean isStarted = true;
    private String currentFile = "";
    private boolean isMovingSeekBar = false;

    private final Handler handler = new Handler();
    public final int  MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=10;


    private final Runnable updatePositionRunnable = new Runnable() {
        public void run() {
            updatePosition();
        }
    };

    private Intent intent;
    String[] items;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_rcdmusic, container, false);
        selectedFile = (TextView) view.findViewById(R.id.selectedfile1);
        seekbar = (SeekBar) view.findViewById(R.id.seekBar1);
        seekbar.getProgressDrawable().setColorFilter(Color.rgb(255, 110, 7), PorterDuff.Mode.SRC_IN);
        seekbar.getThumb().setColorFilter(Color.rgb(255, 110, 7), PorterDuff.Mode.SRC_IN);
        playButton = (ImageButton) view.findViewById(R.id.play1);
        prevButton = (ImageButton) view.findViewById(R.id.previous1);
        nextButton = (ImageButton) view.findViewById(R.id.next1);
        listView= (ListView) view.findViewById(R.id.musiclist1);
        player = new MediaPlayer();
        handleViewIntent();
        player.setOnCompletionListener(onCompletion);
        player.setOnErrorListener(onError);
        seekbar.setOnSeekBarChangeListener(seekBarChanged);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
//        if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED) {
//            execute();
//
//        }

        return view;

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("Hello","Paused");
        if (getFragmentManager().findFragmentByTag("RcdMusicFrag") != null)
            getFragmentManager().findFragmentByTag("RcdMusicFrag").setRetainInstance(true);
    }
    //    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
////        if(savedInstanceState!=null){
////            //TODO
////        }
//    }
//

    @Override
    public void onResume() {
        super.onResume();
        Log.d("Hello","Resume");
        if (getFragmentManager().findFragmentByTag("RcdMusicFrag") != null)
            getFragmentManager().findFragmentByTag("RcdMusicFrag").getRetainInstance();
    }

    //    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    execute();
//
//                }
//                return;
//            }
//        }
//    }id
    private void handleViewIntent(){
        intent=getActivity().getIntent();
        Log.d("Hello","Received");
        String action=intent.getAction();
        if(TextUtils.equals(action,Intent.ACTION_VIEW)){
            Uri beamUri=intent.getData();
            if (TextUtils.equals(beamUri.getScheme(), "file")) {
                Log.d("Hello","File");
                handleFileUri(beamUri);
            }
//            else if (TextUtils.equals(beamUri.getScheme(), "content")) {
//                Log.d("Hello","Content");
//               handleContentUri(beamUri);
//            }
        }

    }
    public void handleFileUri(Uri beamUri) {
        final String filepath = beamUri.getPath();
        Log.d("Hello",filepath);
        final File copiedFile = new File(filepath);
        Log.d("Hello",copiedFile.getName());
        items=new String[1];
        items[0]=copiedFile.getName();
        playButton.setOnClickListener(onButtonClick1);
        nextButton.setOnClickListener(onButtonClick1);
        prevButton.setOnClickListener(onButtonClick1);
        ArrayAdapter<String> adp=new ArrayAdapter<>(getContext(),R.layout.receive_layout,R.id.musicname,items);
        listView.setAdapter(adp);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startPlay(copiedFile.getName(),filepath);
            }
        });

    }
//    public void handleContentUri(Uri beamUri) {
//        // Position of the filename in the query Cursor
////        int filenameIndex;
////        // File object for the filename
////        File copiedFile;
////        // The filename stored in MediaStore
////        String fileName;
//        if (TextUtils.equals(beamUri.getAuthority(), MediaStore.AUTHORITY)) {
//
//            String[] projection = { MediaStore.MediaColumns.DATA };
//            Cursor pathCursor = getContext().getContentResolver().query(beamUri, projection,null, null, null);
//            // Check for a valid cursor
//            if (pathCursor != null && pathCursor.moveToFirst()) {
//                mediaAdapter = new RcdMediaCursorAdapter(getActivity(), R.layout.listitem, pathCursor);
//
//                listView.setAdapter(mediaAdapter);
//
//                playButton.setOnClickListener(onButtonClick1);
//                nextButton.setOnClickListener(onButtonClick1);
//                prevButton.setOnClickListener(onButtonClick1);
////                // Get the column index in the Cursor
////                filenameIndex = pathCursor.getColumnIndex(MediaStore.MediaColumns.DATA);
////                // Get the full file name including path
////                fileName = pathCursor.getString(filenameIndex);
////                // Create a File object for the filename
////                copiedFile = new File(fileName);
////                // Return the parent directory of the file
////                return copiedFile.getParent();
//            }
//
//        }
//    }



//    private View.OnClickListener onListItemClick =new View.OnClickListener() {
//
//        @Override
//        public void onClick(View view) {
//            currentFile = (String) view.getTag();
//            TextView filename= (TextView) view.findViewById(R.id.songtitle);
//            songname = filename.getText();
//            startPlay(songname,currentFile);
//
//        }
//    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updatePositionRunnable);
        player.stop();
        player.reset();
        player.release();

        player = null;
    }
    private void startPlay( CharSequence songname,String file) {
        selectedFile.setText(songname);
        seekbar.setProgress(0);
        player.stop();
        player.reset();

        try {
            player.setDataSource(file);
            player.prepare();
            player.start();
        } catch (IllegalArgumentException e) {
        } catch (IllegalStateException e) {
        } catch (IOException e) {
        }

        seekbar.setMax(player.getDuration());
        playButton.setImageResource(android.R.drawable.ic_media_pause);

        updatePosition();

        isStarted = true;
    }

    private void stopPlay() {
        player.stop();
        player.reset();
        playButton.setImageResource(android.R.drawable.ic_media_play);
        handler.removeCallbacks(updatePositionRunnable);
        seekbar.setProgress(0);

        isStarted = false;
    }

    private void updatePosition() {
        handler.removeCallbacks(updatePositionRunnable);

        seekbar.setProgress(player.getCurrentPosition());

        handler.postDelayed(updatePositionRunnable, UPDATE_FREQUENCY);
    }


    private View.OnClickListener onButtonClick1 = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.play1: {
                    if (player.isPlaying()) {
                        handler.removeCallbacks(updatePositionRunnable);
                        player.pause();
                        playButton.setImageResource(android.R.drawable.ic_media_play);
                    } else {
                        if (isStarted) {
                            player.start();
                            playButton.setImageResource(android.R.drawable.ic_media_pause);

                            updatePosition();
                        } else {
                            startPlay(songname,currentFile);
                        }
                    }

                    break;
                }
                case R.id.next1: {
                    int seekto = player.getCurrentPosition() + STEP_VALUE;

                    if (seekto > player.getDuration())
                        seekto = player.getDuration();

                    if (!(player.isPlaying())){
                        player.seekTo(seekto);
                        updatePosition();
                    }
                    else {

                        player.pause();
                        player.seekTo(seekto);
                        player.start();
                    }
                    break;
                }
                case R.id.previous1: {
                    int seekto = player.getCurrentPosition() - STEP_VALUE;

                    if (seekto < 0)
                        seekto = 0;
                    if (!(player.isPlaying())){
                        player.seekTo(seekto);
                        updatePosition();
                    }
                    else {
                        player.pause();
                        player.seekTo(seekto);
                        player.start();
                    }
                    break;
                }
            }
        }
    };

//    private class RcdMediaCursorAdapter extends SimpleCursorAdapter {
//
//        public RcdMediaCursorAdapter(Context context, int layout, Cursor c) {
//            super(context, layout, c, new String[]{MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.TITLE, MediaStore.Audio.AudioColumns.DURATION},
//                    new int[]{R.id.displayname, R.id.songtitle, R.id.duration});
//        }
//
//        @Override
//        public void bindView(View view, Context context, Cursor cursor) {
//            TextView title= (TextView) view.findViewById(R.id.songtitle);
//            TextView name = (TextView) view.findViewById(R.id.displayname);
//            TextView duration = (TextView) view.findViewById(R.id.duration);
//
//            name.setText(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME)));
//
//            title.setText(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.TITLE)));
//
//            long durationInMs = Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)));
//
//            double durationInMin = ((double) durationInMs / 1000.0) / 60.0;
//
//            durationInMin = new BigDecimal(Double.toString(durationInMin)).setScale(2, BigDecimal.ROUND_UP).doubleValue();
//
//            duration.setText("" + durationInMin);
//
//            view.setTag(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA)));
//        }
//
//        @Override
//        public View newView(Context context, Cursor cursor, ViewGroup parent) {
//            LayoutInflater inflater = LayoutInflater.from(context);
//            v = inflater.inflate(R.layout.listitem, parent, false);
//
//            bindView(v, context, cursor);
//
//            v.setOnClickListener(onListItemClick);
//
//            return v;
//        }
//
//    }


    private MediaPlayer.OnCompletionListener onCompletion = new MediaPlayer.OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            stopPlay();
        }
    };

    private MediaPlayer.OnErrorListener onError = new MediaPlayer.OnErrorListener() {

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            // returning false will call the OnCompletionListener
            return false;
        }
    };

    private SeekBar.OnSeekBarChangeListener seekBarChanged = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            isMovingSeekBar = false;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            isMovingSeekBar = true;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (isMovingSeekBar) {
                player.seekTo(progress);
            }
        }
    };
}
