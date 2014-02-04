package edu.und.cs.tstokke.capturesmsmessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// Android saves in a bundle the current text-message under name "pdus" and type: Object[]. Later we cast to SmsMessage[]. 
		// Jargon pdu stands for "protocol data unit"
		Bundle bundle = intent.getExtras();
        boolean passMessageOn = true;
        
		Object messages[] = (Object[]) bundle.get("pdus");
		SmsMessage smsMessage[] = new SmsMessage[messages.length]; // Note: long sms are broken and transmitted into various pieces
		String msg = "";
		int smsPieces = messages.length;
		for (int n = 0; n < smsPieces; n++) {
			smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
			msg += "\nPart " + (n + 1) + " -of- " + smsPieces + "\n"
			    + smsMessage[n].getOriginatingAddress() + ": " +  smsMessage[n].getMessageBody() + "\n";
			if(smsMessage[n].getMessageBody().contains("test"))	
			{
	            passMessageOn = false;
	    		edu.und.cs.tstokke.capturesmsmessage.CaptureSms.txtCapturedMessages.append(msg);
	    		Toast.makeText(context, "dont pass on messages contain the word test", Toast.LENGTH_LONG).show();
			}
		}

		if (!passMessageOn) 
            abortBroadcast();
		else
			edu.und.cs.tstokke.capturesmsmessage.CaptureSms.txtPublicMessages.append(msg);

	}
}
