package fr.esilv.jcdecaux.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.model.LatLng;

import fr.esilv.jcdecaux.R;
import fr.esilv.jcdecaux.fragments.MapsFragment;

public class MapsActivity extends FragmentActivity {
	
	public static final String POSITION = "POSITION";
	
	public static void start(Context context, LatLng latLng) {
		Intent intent = new Intent(context, MapsActivity.class);
		intent.putExtra(POSITION, latLng);
		context.startActivity(intent);
	}
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);
		
		LatLng position = getIntent().getParcelableExtra(POSITION);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.station_detail_container, MapsFragment.newInstance(position))
				.commit();
	}
}
