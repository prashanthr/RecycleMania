package com.kanaprom.recycle;

import com.kanaprom.recycle.R;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;

public class Story extends Activity
{
	Intent menuIntent;
//------------------------------------------------------------------------
///		On Create - Called when the activity is first created
//------------------------------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.story);
        
		menuIntent = new Intent(Story.this, Menu.class);
    }// onCreate
    
//------------------------------------------------------------------------
///		onBackPressed - Called when the Back Button is pressed
//------------------------------------------------------------------------
	@Override
	public void onBackPressed()
	{
  	  System.runFinalizersOnExit(true); 
	  android.os.Process.killProcess(android.os.Process.myPid());
	}
}//Story class
