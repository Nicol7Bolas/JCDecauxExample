package fr.esilv.jcdecaux.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.esilv.jcdecaux.R;

public class MapsFragment extends Fragment implements OnMapReadyCallback {
	
	private static final String POSITION_EXTRA = "POSITION_EXTRA";
	private LatLng position;
	private GoogleMap googleMap;
	
	public static MapsFragment newInstance(LatLng position) {
		Bundle arguments = new Bundle();
		arguments.putParcelable(POSITION_EXTRA, position);
		MapsFragment fragment = new MapsFragment();
		fragment.setArguments(arguments);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = getArguments().getParcelable(POSITION_EXTRA);
	}
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_maps, container, false);
	}
	
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		// Obtain the SupportMapFragment and get notified when the map is ready to be used.
		SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
	}
	
	@Override
	public void onMapReady(GoogleMap googleMap) {
		this.googleMap = googleMap;
		this.googleMap.addMarker(new MarkerOptions().position(position).title("station"));
		this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));
	}
}
