package com.kanaprom.recycle;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.kanaprom.recycle.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;


import android.view.Menu;
import android.widget.ImageView;
import android.widget.Toast;


public class Gameplay extends Activity implements OnClickListener, OnLongClickListener
{
	// Variables
	private
	DragController mDragController;   // Object that sends out drag-drop events while a view is being moved.
	DragLayer mDragLayer;             // The ViewGroup that supports drag-drop.
	DropSpot mSpot2;                  // The DropSpot that can be turned on and off via the menu.
	DropSpot mSpot3;				  //The DropSpot that can be turned on
	Timer refreshTimer;
	Intent menuIntent;
	Intent gameIntent;
	ImageView i21;
	int delay;
    int PAUSE = 0;
    int GAME_OVER = 1;
    ArrayList <ImageView> images;
	boolean shown [] = new boolean[20];

	
//------------------------------------------------------------------------
///		On Create - Called when the activity is first created
//------------------------------------------------------------------------
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.setContentView(R.layout.gamescreen);
        delay = 900;
        
        images = new ArrayList<ImageView>();
        mDragController = new DragController(this);
		menuIntent = new Intent(Gameplay.this, Menu.class);
		gameIntent = new Intent(Gameplay.this, Gameplay.class);
		refreshTimer = new Timer();
		java.util.Arrays.fill(shown, false);
		
				
		refreshTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimerMethod();
			}

		}, 2000, delay);
		
	    SetupViews ();
    }// onCreate

//------------------------------------------------------------------------
///		TimerMethod
//------------------------------------------------------------------------	
	private void TimerMethod()
	{

		this.runOnUiThread(Timer_Tick);
	}

	private Runnable Timer_Tick = new Runnable() {
		public void run() {
			Toast.makeText(getApplicationContext(), "TIMER TICK", Toast.LENGTH_SHORT);
			Refresh();		}
	};

//------------------------------------------------------------------------
///		Display
//------------------------------------------------------------------------	
	public void Show(View v)
	{
    	v.setLayoutParams(SetPosition());
    	v.setVisibility(View.VISIBLE);
	}	
	
//------------------------------------------------------------------------
///		Display
//------------------------------------------------------------------------	
	public void Display()
	{
		int quit = 0;
		int i = 0;
		while(quit == 0 && i < images.size())
		{
			if(!shown[i])
			{
				Show(images.get(i));
				quit = 1;
	
			}
			else
				i++;
		}
			
	}
//------------------------------------------------------------------------
///		Refresh
//------------------------------------------------------------------------		
	public void Refresh()
	{
		int max = images.size();
		int count = 0;
		for(int i = 0; i < max; i++)
		{
			if(images.get(i).getVisibility() == View.GONE)
			{
				shown[i] = false;
							}
			else
			{
				shown[i] = true;
				count = count + 1;
			}
		}
		if (count == max)
		{
			GameOver();
			AlertDialog dl = onCreateDialog(GAME_OVER);
			dl.show();
			refreshTimer.cancel();
		}
		else Display();

	}
//------------------------------------------------------------------------
///		onCreateDialog - Creates Dialog Box
//------------------------------------------------------------------------	    
        protected AlertDialog onCreateDialog(int reason) 
        {	
        	refreshTimer.purge();
        	refreshTimer.cancel();
            AlertDialog dialog = new AlertDialog.Builder(this).create();

            if(reason == 0)
            {
  	          dialog.setTitle("Game Paused");
  	          dialog.setMessage("Press Play to return to game?");
  	          
  	          dialog.setButton(-2,"Play", new DialogInterface.OnClickListener() 
  	          {
  	          	public void onClick(DialogInterface dialog, int which) 
  	            	{
  	          			StartOver();
  	          			return;
  	            	} }); 
  	          
  	          dialog.setButton(-1, "Main Menu", new DialogInterface.OnClickListener() 
  	          {
  	              public void onClick(DialogInterface dialog, int which) 
  	              {
                	  System.runFinalizersOnExit(true); 
                	  android.os.Process.killProcess(android.os.Process.myPid());
  	              } }); 
  	          return dialog;
            }
            else
            {
          	  dialog.setTitle("Game Over");
  	          dialog.setMessage("Quit Game?");
  	          
  	          dialog.setButton(-2,"Play Again?", new DialogInterface.OnClickListener() 
  	          {
  	          	public void onClick(DialogInterface dialog, int which) 
  	            	{
  	          			reload();		
  	            	} }); 
  	          
  	          dialog.setButton(-1, "Exit Game", new DialogInterface.OnClickListener() 
  	          {
  	              public void onClick(DialogInterface dialog, int which) 
  	              {
  	            	  Gameplay.this.finish();
  	              } }); 
  	          return dialog;
          	  
            }
  	          
      }//onCreateDialog


  //------------------------------------------------------------------------
  ///		onBackPressed - Called when the Back Button is pressed
  //------------------------------------------------------------------------
      @Override
      public void onBackPressed()
      {
      	AlertDialog d = onCreateDialog(PAUSE);
      	d.show();
      }
    
  //------------------------------------------------------------------------
  ///		StartOver - Restarts Timer after returning to game
  //------------------------------------------------------------------------     
      public void StartOver()
      {
  		refreshTimer = new Timer();
		refreshTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimerMethod();
			}

		}, delay, delay);
      }
//------------------------------------------------------------------------
///		OnClick
//------------------------------------------------------------------------ 
	public void onClick(View v) 
	{
	
				toast ("Try a long click");
	
	}
	
//------------------------------------------------------------------------
///		GameOver
//------------------------------------------------------------------------
	public void GameOver()
	{
		DropSpot end = (DropSpot) mDragLayer.findViewById (R.id.drop_spot1);
		boolean status = false;
		status = end.getstatus();
		toast("END OF GAME!");
		int temp;
		temp= end.getpoints();
		String gamefinal = Integer.toString(temp);
		String post = "YOUR FINAL SCORE IS ";
		String b1="[ ";
    	String b2=" ]";
		toast(post+b1+gamefinal+b2);
		end.setstatus(true);
		end.setscrewas(temp);
		end.resetpoints();
		String funfact = " FUN FACT! An aluminum can that is thrown away will still be a can 500 years from now!";
		toast(funfact);
	}



//------------------------------------------------------------------------
///		OnLongClick 
//------------------------------------------------------------------------ 
public boolean onLongClick(View v) 
{
    if (!v.isInTouchMode()) {
        toast ("isInTouchMode returned false. Try touching the view again.");
        return false;
     }
    else
    {
    	//dont do anything
    }
      return startDrag (v);
   
}

//------------------------------------------------------------------------
///		StartDrag - StartDrag
//------------------------------------------------------------------------ 
	public boolean startDrag (View v)
	{
	    Object dragInfo = v;
	    mDragController.startDrag (v, mDragLayer, dragInfo, DragController.DRAG_ACTION_MOVE);
	    return true;
	}

//------------------------------------------------------------------------
///		Fill - Fills the ArrayList
//------------------------------------------------------------------------ 
	public void Fill()
	{
		images.add((ImageView) findViewById (R.id.Image1));
		images.add((ImageView) findViewById (R.id.Image2));
		images.add((ImageView) findViewById (R.id.Image3));
		images.add((ImageView) findViewById (R.id.Image4));
		images.add((ImageView) findViewById (R.id.Image5));
		images.add((ImageView) findViewById (R.id.Image6));
		images.add((ImageView) findViewById (R.id.Image7));
		images.add((ImageView) findViewById (R.id.Image8));
		images.add((ImageView) findViewById (R.id.Image9));
		images.add((ImageView) findViewById (R.id.Image10));
		images.add((ImageView) findViewById (R.id.Image11));
		images.add((ImageView) findViewById (R.id.Image12));
		images.add((ImageView) findViewById (R.id.Image13));
		images.add((ImageView) findViewById (R.id.Image14));
		images.add((ImageView) findViewById (R.id.Image15));
		images.add((ImageView) findViewById (R.id.Image16));
		images.add((ImageView) findViewById (R.id.Image17));
		images.add((ImageView) findViewById (R.id.Image18));
		images.add((ImageView) findViewById (R.id.Image19));
		images.add((ImageView) findViewById (R.id.Image20));
		
	}

//------------------------------------------------------------------------
///		SetupViews - Sets up the game screen to default
//------------------------------------------------------------------------ 
	private void SetupViews() 
	{
		toast("Hold your finger over an item to select it, then drag it to the proper recycle bin to score points");
		toast("When 20 items are on the screen, the game is over.");
		DragController dragController = mDragController;
	
	    mDragLayer = (DragLayer) findViewById(R.id.drag_layer);
	    mDragLayer.setDragController(dragController);
	    dragController.addDropTarget (mDragLayer);

  	    i21 = (ImageView) findViewById (R.id.Image21);
 	    Init(i21);
 	    Fill(); 	     
 	    for(int i = 0; i < images.size();i++)
 	    {
 	    	Init(images.get(i));
 	    }

	    DropSpot drop1 = (DropSpot) mDragLayer.findViewById (R.id.drop_spot1);
	    drop1.setup (mDragLayer, dragController, R.drawable.paperbin);
	
	    DropSpot drop2 = (DropSpot) mDragLayer.findViewById (R.id.drop_spot2);
	    drop2.setup (mDragLayer, dragController, R.drawable.plasticbin);
	
	    DropSpot drop3 = (DropSpot) mDragLayer.findViewById (R.id.drop_spot3);
	    drop3.setup (mDragLayer, dragController, R.drawable.metalbin);

	
	    // Save the second area so we can enable and disable it via the menu.
	    mSpot2 = drop2;
	    mSpot3 = drop3;

	}
//------------------------------------------------------------------------
///		RELOAD A NEW GAME 
//------------------------------------------------------------------------ 	
    public void reload() 
    {

        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void Init(View v)
    {
    	v.setLayoutParams(SetPosition());
    	v.setOnClickListener(this);
    	v.setOnLongClickListener(this);
    	v.setVisibility(View.GONE);
    }
 
//------------------------------------------------------------------------
///		SetPosition - Sets screen position 
//------------------------------------------------------------------------ 
    public MyAbsoluteLayout.LayoutParams SetPosition ()
    {
    	
    	Random xpos = new Random();
    	Random ypos = new Random();
    	Random size = new Random();
    	
    	int x = xpos.nextInt(400);
    	int y = ypos.nextInt(600);
    	int sz = size.nextInt(150);
    	if(sz < 75)
    		sz = sz + 75;
    	
		MyAbsoluteLayout.LayoutParams lp = new MyAbsoluteLayout.LayoutParams(sz,sz,x,y);
		return lp;
    }
    
    
//------------------------------------------------------------------------
///		toast - display text 
//------------------------------------------------------------------------ 
	public void toast (String msg)
	{
	    Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_SHORT).show ();
	} // end toast

}//Gameplay Class