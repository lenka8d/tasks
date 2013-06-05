package com.example.iamhere;

import java.util.HashMap;
import java.util.Map;

import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;
import org.drools.runtime.process.WorkItem;

import android.content.Context;
import android.location.LocationManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;


public class GpsHandler implements WorkItemHandler {
	
private Context context;
	
	public GpsHandler(Context cont){
		context = cont;
	}

	@Override
	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void executeWorkItem(WorkItem i, WorkItemManager m) {
	
		String latitude = null;
		String longitude = null;

		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		LocationListener locationListener = new LocationListener() {

			public void onLocationChanged(Location location) {
			}

			public void onStatusChanged(String provider, int status, Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}

		};

		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
			String locationProvider = LocationManager.NETWORK_PROVIDER;
			Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
			double latt = lastKnownLocation.getLatitude();
			latitude = Location.convert(latt,Location.FORMAT_DEGREES);
			double longtt = lastKnownLocation.getLongitude();
			longitude = Location.convert(longtt,Location.FORMAT_DEGREES);
			
			
		} else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,locationListener);
			String locationProvider = LocationManager.GPS_PROVIDER;
			Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
			double latt = lastKnownLocation.getLatitude();
			latitude = Location.convert(latt,Location.FORMAT_DEGREES);
			double longtt = lastKnownLocation.getLongitude();
			longitude = Location.convert(longtt,Location.FORMAT_DEGREES);
									
		}
			
			
		Map<String, Object> results = new HashMap<String, Object>();
<<<<<<< HEAD
<<<<<<< HEAD
		results.put("latitude", latitude);
		results.put("longitude", longitude);
=======
		results.put("lat", latitude);
		results.put("longt", longitude);
>>>>>>> 2abc9699cdb60095169b43454cb737558f699878
=======
		results.put("lat", latitude);
		results.put("longt", longitude);
>>>>>>> 2abc9699cdb60095169b43454cb737558f699878
		
		m.completeWorkItem(i.getId(), results);
			
						
	}

}
