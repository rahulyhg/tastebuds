package com.codepath.apps.tastebuds.models;

import java.util.List;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Dish")
public class Dish extends ParseObject {

	// Fields
	//private String name;
	//private long googlePlacesId;

	public Dish() {
		super();
	}

	public Dish(String name, String googlePlacesId) {
		super();
		setName(name);
		setGooglePlacesId(googlePlacesId);
	}

	public static ParseQuery<Dish> getQuery() {
		return ParseQuery.getQuery(Dish.class);
	}

	public static ParseQuery<Dish> getQuery(ParseObject user) {
		return ParseQuery.getQuery(Dish.class)
				.whereEqualTo("owner", user);
	}

	public static ParseQuery<Dish> getQuery(String placesId) {
		return ParseQuery.getQuery(Dish.class)
				.whereEqualTo("placesId", placesId);
	}

	public static ParseQuery<Dish> getQuery(String placesId,
			List<ParseObject> friends) {
		return ParseQuery.getQuery(Dish.class);
//				/.whereEqualTo("placesId", placesId);
				//.whereContainedIn("owner", friends);
	}

	public ParseUser getUser() {
		return getParseUser("owner");
	}

	public void setUser(ParseUser user) {
		put("owner", user);
	}

	public String getName() {
		return getString("name");
	}

	public void setName(String name) {
		put("name", name);
	}

	public String getGooglePlacesId() {
		return getString("placesId");
	}
	public void setGooglePlacesId(String googlePlacesId) {
		put("placesId", googlePlacesId);
	}
}
