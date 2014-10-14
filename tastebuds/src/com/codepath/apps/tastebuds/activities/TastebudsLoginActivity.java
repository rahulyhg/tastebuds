package com.codepath.apps.tastebuds.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.tastebuds.GoogleClient;
import com.codepath.apps.tastebuds.R;

import com.codepath.oauth.OAuthLoginActivity;
import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.facebook.*;
import com.facebook.model.GraphUser;

public class TastebudsLoginActivity extends Activity {
	private static Session session;
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		    
//	}

	private Button loginButton;
	private Dialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		loginButton = (Button) findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLoginButtonClicked();
			}
		});

		// Check if there is a currently logged in user
		// and they are linked to a Facebook account.
		ParseUser currentUser = ParseUser.getCurrentUser();
		
		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			// Go to the user info activity
		//	showUserDetailsActivity();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	}

	private void onLoginButtonClicked() {
		session = Session.getActiveSession();
	    if (session !=null && session.isOpened()) {
	        Toast.makeText(this, session.getAccessToken(), Toast.LENGTH_LONG).show();
	        goToHomeActivity();
	    }else{
		TastebudsLoginActivity.this.progressDialog = ProgressDialog.show(
				TastebudsLoginActivity.this, "", "Logging in...", true);
		List<String> permissions = Arrays.asList("public_profile", "user_about_me","user_friends",
				"user_relationships", "user_birthday", "user_location");
	
		ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
			@Override
			public void done(ParseUser user, ParseException err) {
				TastebudsLoginActivity.this.progressDialog.dismiss();
				if(user != null){
					session = Session.getActiveSession();
					
					getFacebookIdInBackground();
					Intent i = new Intent(TastebudsLoginActivity.this, HomeActivity.class);
					startActivity(i);
					Toast.makeText(getApplicationContext(), "User logged in" + ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_SHORT).show();
				}
//				else{
//					Toast.makeText(getApplicationContext(), "User is null" + err.toString(), Toast.LENGTH_SHORT).show();
//				}
				
			}
		});
	    }
	}
	private static void getFacebookFriends(){
//		//session = Session.openActiveSession(TastebudsLoginActivity.this, true, new StatusCallback(){
//
//			@Override
//			public void call(Session session, SessionState state, Exception exception) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
		Request.newMyFriendsRequest(session, new Request.GraphUserListCallback() {
				
			  @Override
			  public void onCompleted(List<GraphUser> users, Response response) {
			    if (users != null) {
			      List<String> friendsList = new ArrayList<String>();
			      for (GraphUser user : users) {
			        friendsList.add(user.getId());
			      }

			   // Construct a ParseUser query that will find friends whose
	                // facebook IDs are contained in the current user's friend list.
	                ParseQuery friendQuery = ParseQuery.getUserQuery();
	                friendQuery.whereContainedIn("objectId", friendsList);

	                // findObjects will return a list of ParseUsers
	                // that are friends with the current user
	                try {
						List<ParseObject> friendUsers = friendQuery.find();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			  }
			}).executeAsync();
	}
	private static void getFacebookIdInBackground() {

		Request.newMeRequest(session, new Request.GraphUserCallback(){
			@Override
			public void onCompleted(GraphUser user, Response response) {
				if (user != null) {
					ParseUser.getCurrentUser().put("username", user.getName());
					ParseUser.getCurrentUser().put("objectId", user.getId());
					ParseUser.getCurrentUser().put("location", user.getLocation().getName());
					getFacebookFriends();
					ParseUser.getCurrentUser().saveInBackground();

				}
			}
		}).executeAsync();
	}
	private void goToHomeActivity(){
		Intent i = new Intent(TastebudsLoginActivity.this, RestaurantDetailActivity.class);
		startActivity(i);
		Toast.makeText(getApplicationContext(), "User logged in" + ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_SHORT).show();
	}
	
//	private static void getFacebookIdInBackground() {
//	
//		Request.executeMeRequestAsync(ParseFacebookUtils.getSession(), new Request.GraphUserCallback() {
//			@Override
//			public void onCompleted(GraphUser user, Response response) {
//				if (user != null) {
//					ParseUser.getCurrentUser().put("fbId", user.getId());
//					ParseUser.getCurrentUser().saveInBackground();
//				}
//			}
//		});
//	}
}
