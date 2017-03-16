package fr.esilv.jcdecaux.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import fr.esilv.jcdecaux.Constants;
import fr.esilv.jcdecaux.R;
import fr.esilv.jcdecaux.adapters.ContractsAdapter;
import fr.esilv.jcdecaux.interfaces.OnContractSelectedListener;
import fr.esilv.jcdecaux.models.Contract;
import fr.esilv.jcdecaux.models.Contracts;

public class ContractsActivity extends AppCompatActivity implements OnContractSelectedListener {
	
	private static final String CONTRACTS_URL = "https://api.jcdecaux.com/vls/v1/contracts";
	private RecyclerView recyclerView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contracts);
		
		recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		
		getContracts();
	}
	
	private void getContracts() {
		StringRequest contractsRequest = new StringRequest(CONTRACTS_URL + "?apiKey=" + Constants.API_KEY, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				//parse data from webservice to get Contracts as Java object
				Contracts contracts = new Gson().fromJson(response, Contracts.class);
				
				setAdapter(contracts);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("Contracts", "Error");
			}
		});
		
		Volley.newRequestQueue(this).add(contractsRequest);
	}
	
	private void setAdapter(Contracts contracts) {
		ContractsAdapter adapter = new ContractsAdapter(contracts);
		adapter.setOnContractSelectedListener(this);
		recyclerView.setAdapter(adapter);
	}
	
	@Override
	public void onContractSelected(Contract contract) {
		StationsActivity.start(this, contract.getName());
	}
}
