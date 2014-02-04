package edu.und.cs.revenstad.cameraexample;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	ImageView imgCameraPic;
	TextView txtFileName;
	Button btnTakePic;
	Button btnTakeVideo;
	
	static final int TAKE_PICTURE = 1;
	static final int TAKE_VIDEO = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		imgCameraPic = (ImageView)findViewById(R.id.imgCameraPic);
		txtFileName = (TextView)findViewById(R.id.txtFileName);
		btnTakePic = (Button)findViewById(R.id.btnTakePic);
		btnTakeVideo = (Button)findViewById(R.id.btnTakeVideo);
		
		btnTakePic.setOnClickListener(this);
		btnTakeVideo.setOnClickListener(this);
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}*/

	@Override
	public void onClick(View arg0) {
		if (arg0.getId() == btnTakePic.getId())
		{
			NotificationManager nManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
			Notification n = new Notification();
			n.vibrate = new long[] {200, 500, 200, 500};
			nManager.notify(0, n);
			
			Intent pictureIntent = new Intent (android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(pictureIntent, TAKE_PICTURE);
		}
		else if (arg0.getId() == btnTakeVideo.getId())
		{
			Intent pictureIntent = new Intent (android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
			startActivityForResult(pictureIntent, TAKE_VIDEO);
		}
	}

	private String getLastFileName()
	{
		final String[] imageColumns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME};
		final String imageOrderBy = MediaStore.Images.Media._ID + " Desc";
		
		Cursor imageCursor = null;
		ContentResolver cr = getContentResolver();
		imageCursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, imageOrderBy);
		
		if (imageCursor.moveToFirst())
		{
			String fileName = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
			imageCursor.close();
			return fileName;
		}
		else
			return " >>unable to find<<";
	}
	
	private int getLastFileId()
	{
		final String[] imageColumns = {MediaStore.Images.Media._ID};
		final String imageOrderBy = MediaStore.Images.Media._ID + " Desc";
		
		Cursor imageCursor = null;
		ContentResolver cr = getContentResolver();
		imageCursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, imageOrderBy);
		
		if (imageCursor.moveToFirst())
		{
			return imageCursor.getInt(imageCursor.getColumnIndex(MediaStore.Images.Media._ID));
			
		}
		else
			return 0;
	}
	
	private String getLastVideoFileName()
	{
		final String[] imageColumns = {MediaStore.Video.Media.DATA};
		final String imageOrderBy = MediaStore.Images.Media._ID + " Desc";
		
		Cursor imageCursor = null;
		ContentResolver cr = getContentResolver();
		imageCursor = cr.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, imageColumns, null, null, imageOrderBy);
		
		if (imageCursor.moveToFirst())
		{
			String fileName = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Video.Media.DATA));
			imageCursor.close();
			return fileName;
		}
		else
			return " >>unable to find<<";
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == TAKE_PICTURE)
		{
			if (resultCode == Activity.RESULT_OK)
			{
				Bitmap cameraPic = (Bitmap) data.getExtras().get("data");
				imgCameraPic.setImageBitmap(cameraPic);
				imgCameraPic.setAdjustViewBounds(true);
				String fileName = getLastFileName();
				txtFileName.setText("Name of last file " + fileName);
			}
		}
		else if (requestCode == TAKE_VIDEO)
		{
			String fileName = getLastFileName();
			txtFileName.setText("Name of last file " + fileName);
		}
	}
}
