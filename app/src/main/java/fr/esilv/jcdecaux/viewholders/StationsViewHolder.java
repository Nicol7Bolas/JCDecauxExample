package fr.esilv.jcdecaux.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fr.esilv.jcdecaux.R;
import fr.esilv.jcdecaux.interfaces.OnStationSelectedListener;
import fr.esilv.jcdecaux.models.Station;

public class StationsViewHolder extends RecyclerView.ViewHolder {
	
	private TextView name;
	private TextView address;
	private OnStationSelectedListener onStationSelectedListener;
	
	public StationsViewHolder(View itemView) {
		super(itemView);
		name = (TextView) itemView.findViewById(R.id.name);
		address = (TextView) itemView.findViewById(R.id.address);
	}
	
	public void bind(final Station station) {
		name.setText(station.getName());
		address.setText(station.getAddress());
		itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (onStationSelectedListener == null) {
					return;
				}
				onStationSelectedListener.onStationSelected(station);
			}
		});
	}
	
	public void setOnStationSelectedListener(OnStationSelectedListener onStationSelectedListener) {
		this.onStationSelectedListener = onStationSelectedListener;
	}
}
