package com.codepath.apps.tastebuds.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codepath.apps.tastebuds.GooglePlacesApiClient;
import com.codepath.apps.tastebuds.R;
import com.codepath.apps.tastebuds.activities.RestaurantDetailActivity;
import com.codepath.apps.tastebuds.adapters.FriendsListAdapter;
import com.codepath.apps.tastebuds.adapters.RestaurantAdapter;
import com.codepath.apps.tastebuds.models.Restaurant;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class FriendsListFragment extends Fragment {

	ArrayList<Object> friends;
	ArrayAdapter<Object> friendsListAdapter;
	ListView lvFriends;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		friends = new ArrayList<Object>();
		friendsListAdapter = new FriendsListAdapter(getActivity(), friends);
		friends.addAll(ParseUser.getCurrentUser().getList("userFriends"));
		friendsListAdapter.notifyDataSetChanged();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_friends_list, container, false);
		lvFriends = (ListView) v.findViewById(R.id.lvFriends);
		lvFriends.setAdapter(friendsListAdapter);
		lvFriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
			   public void onItemClick(AdapterView parentView, View childView, int position, long id) 
			   {  
				   // Add logic to open friends detail actiivty
			   } 
			});		
		return v;		
	}
	


}