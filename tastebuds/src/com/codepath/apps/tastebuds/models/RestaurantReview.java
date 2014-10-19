package com.codepath.apps.tastebuds.models;

import java.util.ArrayList;
import java.util.List;

import com.codepath.apps.tastebuds.adapters.DishReviewListAdapter;
import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("RestaurantReviews")
public class RestaurantReview extends ParseObject implements Review {

	// Following fields:
	//private long googlePlacesId;
	//private ParseUser user;
	//private int rating;
	//private String text;

	public RestaurantReview() {
		super();
	}

	public RestaurantReview(String googlePlacesId, int rating, String text) {
		super();
		setGooglePlacesId(googlePlacesId);
		setRating(rating);
		setText(text);
	}

	public static ParseQuery<RestaurantReview> getQuery() {
	    return ParseQuery.getQuery(RestaurantReview.class);
	}
	public static ParseQuery<RestaurantReview> getQuery(String ownerId){
		ParseQuery<RestaurantReview> restQuery = ParseQuery.getQuery(
	    		RestaurantReview.class).whereEqualTo("owner", ownerId);
		return restQuery;
	}
	public static ParseQuery<RestaurantReview> getQuery(long googlePlacesId) {
	    return ParseQuery.getQuery(RestaurantReview.class)
	    		.whereEqualTo("placesId", googlePlacesId)
	    		.orderByDescending("createdAt");
	}

	public static ParseQuery<RestaurantReview> getQuery(ParseObject owner) {
	    return ParseQuery.getQuery(RestaurantReview.class)
	    		.whereEqualTo("owner", owner)
	    		.orderByDescending("createdAt");
	}

	public static ParseQuery<RestaurantReview> getQuery(String googlePlacesId,
			List<ParseObject> owners) {
	    ParseQuery<RestaurantReview> query = ParseQuery.getQuery(
	    		RestaurantReview.class)
	    		.orderByDescending("createdAt");
	    if (owners != null) {
	    	query.whereContainedIn("owner", owners);
	    }
	    if (googlePlacesId != null) {
	    	query.whereEqualTo("placesId", googlePlacesId);
	    }
	    return query;
	}

	public static ParseQuery<RestaurantReview> getQuery(List<String> googlePlacesIds,
			List<ParseObject> owners) {
	    return ParseQuery.getQuery(RestaurantReview.class)
	    		//.whereContainedIn("owner", owners)
	    		//.whereContainedIn("placesId", googlePlacesIds)
	    		.orderByDescending("createdAt");
	}

	public String getGooglePlacesId() {
		return getString("placesId");
	}

	public void setGooglePlacesId(String googlePlacesId) {
		put("placesId", googlePlacesId);
	}

	public ParseUser getUser() {
		return getParseUser("owner");
	}
	
	public void setUser(ParseUser user) {
		put("owner", user);
	}

	public long getCreatedTimestamp() {
		 return getLong("createdAt");
	}

	public int getRating() {
		return getInt("rating");
	}

	public void setRating(float f) {
		put("rating", f);
	}

	public String getText() {
		return getString("comment");
	}

	public void setText(String text) {
		put("comment", text);
	}

	public String getTags() {
		List<Tag> tags = getList("tags");
		StringBuffer buffer = new StringBuffer();
		for (Tag tag : tags) {
			try {
				buffer.append(tag.fetchIfNeeded().getString("tag"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			buffer.append(" ");
		}
		return buffer.toString();
	}

	public void setTags(String googlePlacesId, String tagString) {
		String[] values = tagString.replaceAll("^[,\\s]+", "").split("[,\\s]+");
		ArrayList<Tag> tags = new ArrayList<Tag>();
		for (String value : values) {
			tags.add(new Tag(googlePlacesId, value));
		}
		put("tags", tags);
	}
}
