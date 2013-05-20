package com.example.addevent;

import java.util.Calendar;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Events;

public class AddEventHandler implements WorkItemHandler {

	private Context context;

	public AddEventHandler(Context cont) {
		context = cont;
	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager m) {
	}

	public void executeWorkItem(WorkItem i, WorkItemManager m) {
		String event = (String) i.getParameter("event");
		String description = (String) i.getParameter("description");
		String place = (String) i.getParameter("place");
		String date = (String) i.getParameter("date");
		String time = (String) i.getParameter("time");

		Calendar begin = Calendar.getInstance();
		begin.set(Integer.parseInt(date.split("/")[0]),
				Integer.parseInt(date.split("/")[1]) - 1,
				Integer.parseInt(date.split("/")[2]),
				Integer.parseInt(time.split(":")[0]),
				Integer.parseInt(time.split(":")[1]));

		Intent intent = new Intent(Intent.ACTION_INSERT)
				.setData(Events.CONTENT_URI)
				.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
						begin.getTimeInMillis()).putExtra(Events.TITLE, event)
				.putExtra(Events.DESCRIPTION, description)
				.putExtra(Events.EVENT_LOCATION, place)
				.putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);
		context.startActivity(intent);
		m.completeWorkItem(i.getId(), null);
	}
}
