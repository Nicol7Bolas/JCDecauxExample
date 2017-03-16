package fr.esilv.jcdecaux.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import fr.esilv.jcdecaux.Constants;
import fr.esilv.jcdecaux.R;
import fr.esilv.jcdecaux.adapters.StationsAdapter;
import fr.esilv.jcdecaux.interfaces.OnStationSelectedListener;
import fr.esilv.jcdecaux.fragments.MapsFragment;
import fr.esilv.jcdecaux.models.Station;
import fr.esilv.jcdecaux.models.Stations;

public class StationsActivity extends AppCompatActivity implements OnStationSelectedListener {
	
	private static final String CONTRACT = "CONTRACT";
	private static final String STATIONS_URL = "https://api.jcdecaux.com/vls/v1/stations?contract=";
	private RecyclerView recyclerView;
	private String contractName;
	private boolean twoPane;
	
	public static void start(Context context, String contractName) {
		Intent intent = new Intent(context, StationsActivity.class);
		intent.putExtra(CONTRACT, contractName);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stations);
		contractName = getIntent().getStringExtra(CONTRACT);
		
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		
		getStations();
		//This view only exists in the tablet layout. This means that if it isn't null, we are running our app on a tablet.
		if (findViewById(R.id.station_detail_container) != null) {
			twoPane = true;
		}
	}
	
	private void getStations() {
		StringRequest stationsRequest = new StringRequest(STATIONS_URL + contractName + "&apiKey=" + Constants.API_KEY, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				//parse data from webservice to get Contracts as Java object
				Stations stations = new Gson().fromJson(response, Stations.class);
				
				setAdapter(stations);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("Contracts", "Error");
			}
		});
		
		Volley.newRequestQueue(this).add(stationsRequest);
	}
	
	private void setAdapter(Stations stations) {
		StationsAdapter adapter = new StationsAdapter(stations);
		adapter.setOnStationSelectedListener(this);
		recyclerView.setAdapter(adapter);
	}
	
	@Override
	public void onStationSelected(Station station) {
		LatLng position = new LatLng(station.getPosition().getLat(), station.getPosition().getLng());
		if (twoPane) {
			//We are running our app on a tablet. It means that we will show a fragment
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.station_detail_container, MapsFragment.newInstance(position))
					.commit();
		} else {
			//We are running our app on a phone. We will launch a new activity.
			MapsActivity.start(this, position);
		}
	}
}
