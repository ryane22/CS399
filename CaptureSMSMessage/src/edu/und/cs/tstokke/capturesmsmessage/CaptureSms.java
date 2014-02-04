package edu.und.cs.tstokke.capturesmsmessage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CaptureSms extends Activity {
	//main screen - displays intercepted SMS 
    static TextView txtCapturedMessages;
    static TextView txtPublicMessages;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        txtCapturedMessages = (TextView)findViewById(R.id.txtCapturedMessages);
        txtPublicMessages = (TextView)findViewById(R.id.txtPublicMessages); 
    }
}