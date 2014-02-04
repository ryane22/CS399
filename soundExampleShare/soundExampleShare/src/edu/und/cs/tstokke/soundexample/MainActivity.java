package edu.und.cs.tstokke.soundexample;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SurfaceHolder.Callback{

	MediaPlayer mediaPlayer;
	SurfaceView surfaceView;
	SurfaceHolder surfaceHolder;
	boolean pausing = false;
	TextView txtVolume;
	Button btnMute;
	int previousVolume;
	boolean muted = false;
	AudioManager audioManager;
	int videoWidth;
    int videoHeight;
    SeekBar volControl;
	
	//String stringPath = "/sdcard/samplevideo.3gp";
	String stringPath = "/sdcard/Scratch-speed.mp4";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        volControl = (SeekBar)findViewById(R.id.volbar);
        txtVolume = (TextView) findViewById(R.id.txtVolume);
        btnMute = (Button) findViewById (R.id.btnMute);
        volControl.setMax(maxVolume);
        volControl.setProgress(curVolume);
        volControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				//here is where we pick up the changes in the slider
				//arg1 will contain the "position" of the slider
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, arg1, 0);
				txtVolume.setText("" + audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
			}
		});
        
        
        Button buttonPlayVideo = (Button)findViewById(R.id.playvideoplayer);
        final Button buttonPauseVideo = (Button)findViewById(R.id.pausevideoplayer);
        
        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = (SurfaceView)findViewById(R.id.surfaceview);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        //surfaceHolder.setFixedSize(176, 144);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mediaPlayer = new MediaPlayer();

        btnMute.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!muted)
				{
					previousVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
					audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
					txtVolume.setText("0");
					btnMute.setText("Unmute");
					volControl.setProgress(0);
					muted = true;
				}
				else
				{
					audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, previousVolume, 0);
					txtVolume.setText("" + previousVolume);
					volControl.setProgress(previousVolume);
					btnMute.setText("Mute");
					muted = false;
				}
			}
        	
        }); 
        buttonPlayVideo.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				pausing = false;
				//play button pressed - if already playing reset the video
				if (mediaPlayer.isPlaying())
					mediaPlayer.reset();
				
				//set up what to do with the audio and video
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mediaPlayer.setDisplay(surfaceHolder);
				try {
					mediaPlayer.setDataSource(stringPath);
					mediaPlayer.prepare();
					
					videoWidth = mediaPlayer.getVideoWidth();
					videoHeight = mediaPlayer.getVideoHeight();
					
					Toast.makeText(getApplicationContext(), "Video width " + videoWidth, Toast.LENGTH_SHORT).show();
					Toast.makeText(getApplicationContext(), "Video height " + videoHeight, Toast.LENGTH_SHORT).show();
					DisplayMetrics displayMetrics = new DisplayMetrics();
					getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
					int screenHeight = displayMetrics.heightPixels;
					int screenWidth = displayMetrics.widthPixels;
					Toast.makeText(getApplicationContext(), "Screen width " + screenWidth, Toast.LENGTH_SHORT).show();
					Toast.makeText(getApplicationContext(), "Screen height " + screenHeight, Toast.LENGTH_SHORT).show();
					
					int newSurfaceHeight = (int)((float) screenWidth / (float) videoWidth * videoHeight);
					Toast.makeText(getApplicationContext(), "New Surface height " + newSurfaceHeight, Toast.LENGTH_SHORT).show();
					surfaceHolder.setFixedSize(screenWidth, newSurfaceHeight);

				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mediaPlayer.start();
			}});
        
        buttonPauseVideo.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(pausing){
					pausing = false;
					mediaPlayer.start();
					buttonPauseVideo.setText("pause video");
				}
				else{	
					pausing = true;
					mediaPlayer.pause();
					buttonPauseVideo.setText("resume video");
				}
			}});
    }
    
    

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
        int surfaceWidth = surfaceHolder.getSurfaceFrame().width();
        int surfaceHeight = surfaceHolder.getSurfaceFrame().height();
		Toast.makeText(getApplicationContext(), " svf width and height " + surfaceWidth + ", " + surfaceHeight, Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
}