package com.example.iamhere;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

import java.util.ArrayList ;

import android.telephony.SmsManager;

public class SmsHandler implements WorkItemHandler {
	
	public void abortWorkItem(WorkItem workItem,
			WorkItemManager m) {
	}

	public void executeWorkItem(final WorkItem i,
			final WorkItemManager m) {
		
		// use results from previous Service Task
		String latitude = (String) i.getParameter("latitude");
		String longitude = (String) i.getParameter("longitude");
		
		final String message = latitude + " " + longitude;
		
		// number 5556 is used just for testing on emulator
		SmsManager sms = SmsManager.getDefault();
		ArrayList <String> parts = sms.divideMessage(message);
	    sms.sendMultipartTextMessage("5556", null, parts, null, null);
		m.completeWorkItem(i.getId(), null);
		}	
}
