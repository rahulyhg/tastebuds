package com.codepath.apps.tastebuds;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class GooglePlacesApiClient {
	private String apiKey = "AIzaSyBnvxHM9wtuaF-GlCBQmvdaMRJKSomiHes";
	public void getRestaurantListfromGooglePlaces(String search, String nextPageToken,
			double latitude, double longitude, JsonHttpResponseHandler handler) {
		// change lat, long from double to String
		//Log.d("Debug", "Client Called");
		String latString = String.valueOf(latitude);
		String longString = String.valueOf(longitude);
		String baseUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";

		//String apiKey = "AIzaSyCMD74MgiALP22wpGggAAf4QXYzutiKRQg";

		String apiKey = "AIzaSyBnvxHM9wtuaF-GlCBQmvdaMRJKSomiHes";

		String location = latString+","+longString;
		//String types = "&types=bakery|bar|cafe|convenience_store|food|grocery_or_supermarket|liquor_store|meal_delivery|meal_takeaway|restaurant";
		String types = "restaurant|bar|cafe";
		RequestParams params = new RequestParams();
		params.put("key", apiKey);
		if (search != "None") {
			params.put("keyword", search);
		}
		if (nextPageToken == "None") {
		params.put("location", location);
		params.put("rankby", "distance");
		params.put("types", types);
		} else {
			params.put("pagetoken", nextPageToken);
		}
		if (!(nextPageToken.equals("QueryLimitReached")))
		{
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(baseUrl, params, handler);
		}
	}
	
	public void getRestaurantDetailfromGooglePlaces(String placeId, JsonHttpResponseHandler handler) {
		String baseUrl = "https://maps.googleapis.com/maps/api/place/details/json"; 
		RequestParams params = new RequestParams();
		params.put("key", apiKey);
		params.put("placeid", placeId);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(baseUrl, params, handler);
	}

	public void getRestaurantDisplayPhoto(String photoReference, int maxWidth,
			JsonHttpResponseHandler handler) {
		String baseUrl = "https://maps.googleapis.com/maps/api/place/photo";
		RequestParams params = new RequestParams();
		params.put("photoReference", photoReference);
		params.put("maxWidth", Integer.toString(maxWidth));
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(baseUrl, params, handler);
	}
}
