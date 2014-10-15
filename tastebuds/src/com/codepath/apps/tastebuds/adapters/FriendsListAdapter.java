package com.codepath.apps.tastebuds.adapters;

import java.util.List;


import com.codepath.apps.tastebuds.R;
import com.codepath.apps.tastebuds.models.Restaurant;
import com.parse.ParseUser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendsListAdapter extends ArrayAdapter<Object> {
	private TextView tvFriendName;
	private ImageView ivFriendProfImg;
	public FriendsListAdapter(Context context, List<Object> friends) {
		super(context, 0, friends);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ParseUser friend = (ParseUser)getItem(position);	
		View v;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.item_friend, parent, false);
		} else {
			v = convertView;
		}
		tvFriendName = (TextView) v.findViewById(R.id.tvFriendName);
		tvFriendName.setText(friend.getUsername());
		
		return v;
	}
}
