package com.kanaprom.recycle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.kanaprom.recycle.R;
import com.kanaprom.recycle.Gameplay;
import com.kanaprom.recycle.Story;

//------------------------------------------------------------------------
///		Menu Class
//------------------------------------------------------------------------
public class Menu extends Activity implements OnClickListener
{
	Intent storyIntent;
	Intent gameIntent;
	Button buttonPlay;
	Button buttonExit;
	Button buttonStory;	

//------------------------------------------------------------------------
///		On Create - Called when the activity is first created
//------------------------------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        this.setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        this.buttonExit = (Button) findViewById(R.id.buttonExit);
        this.buttonPlay = (Button)findViewById(R.id.buttonPlay);
        this.buttonStory = (Button) findViewById(R.id.buttonStory);
        
        buttonPlay.setOnClickListener(this); 
        buttonExit.setOnClickListener(this);
        buttonStory.setOnClickListener(this);
        
		gameIntent = new Intent(Menu.this, Gameplay.class);
		storyIntent = new Intent(Menu.this, Story.class);
}// onCreate
    
//------------------------------------------------------------------------
///		onBackPressed - Called when the Back Button is pressed
//------------------------------------------------------------------------
    @Override
    public void onBackPressed()
    {
    	AlertDialog d = onCreateDialog();
    	d.show();
    }

//------------------------------------------------------------------------
///		onClick - Listener
//------------------------------------------------------------------------
	public void onClick(View v) 
	{
		switch (v.getId())
		{
			case R.id.buttonPlay:
	            this.startActivity(gameIntent);
	            break;
			
			case R.id.buttonExit:
	        	AlertDialog d = onCreateDialog();
	        	d.show();
	        	break;
	
			case R.id.buttonStory:
	            this.startActivity(storyIntent);
	        	break;
		}//Switch
	}//onClick

//------------------------------------------------------------------------
///		onCreateDialog - Creates Dialog Box
//------------------------------------------------------------------------	    
	    
    protected AlertDialog onCreateDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("Quit");
            dialog.setMessage("Quit Recycle Mania?");
            
            dialog.setButton(-2,"Return", new DialogInterface.OnClickListener() 
            {
            	public void onClick(DialogInterface dialog, int which) 
              	{
            		return;
              	} }); 
            
            dialog.setButton(-1, "Exit", new DialogInterface.OnClickListener() 
            {
                public void onClick(DialogInterface dialog, int which) 
                {
                	  System.runFinalizersOnExit(true); 
                	  android.os.Process.killProcess(android.os.Process.myPid());
                } }); 
        return dialog;
    }//onCreateDialog
}//Menu Class
