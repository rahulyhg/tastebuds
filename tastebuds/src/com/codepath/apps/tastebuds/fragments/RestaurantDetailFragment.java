package com.codepath.apps.tastebuds.fragments;


import org.json.JSONException;
import org.json.JSONObject;

import com.codepath.apps.tastebuds.GooglePlacesApiClient;
import com.codepath.apps.tastebuds.R;
import com.codepath.apps.tastebuds.models.Restaurant;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RestaurantDetailFragment extends Fragment {

	private String placeId;
	private TextView tvGoogleRating;
	private TextView tvPrice;
	private TextView tvPhone;
	private TextView tvAddress;
	private TextView tvOpenNow;
	public Restaurant restaurant;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		placeId = getArguments().getString("placeId");
		restaurantDetailFromGooglePlacesApi();	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
		tvGoogleRating = (TextView) v.findViewById(R.id.tvGoogleRating);
		tvPrice = (TextView) v.findViewById(R.id.tvPrice);
		tvPhone = (TextView) v.findViewById(R.id.tvPhone);
		tvAddress = (TextView) v.findViewById(R.id.tvAddress);
		tvOpenNow = (TextView) v.findViewById(R.id.tvOpenNow);
		
		return v;		
	}
	
	private void restaurantDetailFromGooglePlacesApi() {
		
		GooglePlacesApiClient placesApi = new GooglePlacesApiClient();		
		placesApi.getRestaurantDetailfromGooglePlaces(placeId, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
        		JSONObject restaurantDetailJson = null;
        		Log.d("Debug", "Response: " + response.toString());
        		try {
        			restaurantDetailJson = response.getJSONObject("result");
        			restaurant = Restaurant.fromJSONDetail(restaurantDetailJson);
        			Log.d("Debug", "Name Detail: " + restaurant.getName());
        			updateDetailFragmentView();
				} catch (JSONException e) {
					e.printStackTrace();
				}
        	}
			@Override
    		public void onFailure(Throwable e, JSONObject errorResponse) {
				Log.e("Error", e.toString());
    		}		
		});		
	}
	
	public void updateDetailFragmentView() {
		tvGoogleRating.setText(Double.toString(restaurant.getGoogle_rating()));	
		switch (restaurant.getPrice_level()) {
			case 0: tvPrice.setText(""); break;
			case 1: tvPrice.setText("$"); break;
			case 2: tvPrice.setText("$$"); break;
			case 3: tvPrice.setText("$$$"); break;
			case 4: tvPrice.setText("$$$$"); break;
			default: tvPrice.setText("");
		}
		tvPhone.setText(restaurant.getPhone());
		tvAddress.setText(restaurant.getAddress());
		if (restaurant.isOpen_now()) {
			tvOpenNow.setText("Open");
		} else {
			tvOpenNow.setText("Closed");
		}
	}
	
	
}
