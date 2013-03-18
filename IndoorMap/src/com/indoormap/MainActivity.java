package com.indoormap;


import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.indoormap.R.id;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	
	private ImageView map;
	private Context context;
	private int markerCounter=0;
	private MarkerList markerList;
	private EditText markerText;
	private TextView mainTextView;
	private Button doneButton,delButton,createButton;
	private float dpX,dpY;
	private RelativeLayout relativeLayout;
	private int markerTag;
	LayoutParams params;
	

	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context=this;
		map 		    = (ImageView) findViewById(id.mapView1);
		markerList      = MarkerList.getInstance(context);
		markerText      = (EditText) findViewById(id.editText1);
		mainTextView    = (TextView) findViewById(id.main_text_view);
		doneButton      = (Button) findViewById(R.id.doneButton);
		delButton		= (Button) findViewById(R.id.del_button);
		createButton    = (Button) findViewById(R.id.create_button1);
		relativeLayout  = (RelativeLayout) findViewById(R.id.main_layout);
	    params = new LayoutParams (RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		
		delButton.setVisibility(View.INVISIBLE);
		doneButton.setVisibility(View.INVISIBLE);
		createButton.setVisibility(View.INVISIBLE);
		markerText.setVisibility(View.INVISIBLE);
		
		
		
		
		final GestureDetector gd = new GestureDetector(this,gdListener); 
		
		
		map.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) { gd.onTouchEvent(event);
			return true;
			} });
		
	}
	
	
	// create new marker LongPress listener
	public void createNewMarker (View v)
	{
		createButton.setVisibility(View.INVISIBLE);
		markerText.setVisibility(View.INVISIBLE);
		markerCounter++;
		Marker newMarker = new Marker( markerCounter , markerText.getText().toString(), context, dpX, dpX ); 
		markerList.addMarker(newMarker);
		LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
		ImageView markerImage = (ImageView) inflater.inflate(R.layout.marker, null);
		params.setMargins((int) dpX , (int) dpY, 0, 0);
		markerImage.setLayoutParams(params);
		markerImage.setX(dpX);
		markerImage.setY(dpY);
		markerImage.bringToFront();
		markerImage.setId(markerCounter);
		relativeLayout.addView(markerImage);
		
		
		markerImage.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				// TODO Auto-generated method stub
				System.out.println(v.getId());
				createButton.setVisibility(View.INVISIBLE);
				delButton.setVisibility(View.VISIBLE);
	    		doneButton.setVisibility(View.VISIBLE);
	    		markerText.setVisibility(View.VISIBLE);
				markerTag=v.getId();
				for(int i=0 ; i<markerList.getSize() ; i++)
			    {
			    	if( v.getId() == markerList.getMarker(i).getId())
			    	{
			    	
			    		
						
					   // dpX=convertPixelsToDp(x,context);
					    //dpY=convertPixelsToDp(y,context);
			    		mainTextView.setText("Edit Marker");
			    		markerText.setText(markerList.getMarker(i).getTitle());
			    		return true;
			    	}
			    }
				return false;
			}
		});
		markerText.setText("");
		mainTextView.setText("New marker added!");	
	}
	
	// Done edit marker Onclick listener 
	public void Done (View v)
	{
		for(int i=0 ; i<markerList.getSize() ; i++)
		{
			if( markerTag == markerList.getMarker(i).getId())
			{
				markerList.getMarker(i).setTitle(markerText.getText().toString());
			}
		}
		markerText.setText("");
		mainTextView.setText("Marker has been edited");	
		delButton.setVisibility(View.INVISIBLE);
		doneButton.setVisibility(View.INVISIBLE);
		markerText.setVisibility(View.INVISIBLE);
	}
	
	// Delete marker Onclick listener 
	public void delMarker(View v)
	{
		for(int i=0 ; i<markerList.getSize() ; i++)
		{
			if( markerTag == markerList.getMarker(i).getId())
			{
				markerList.delMarker(i);
			}
	    }
		relativeLayout.removeView(findViewById(markerTag));
		markerText.setText("");
		mainTextView.setText("Delete Marker!");
		delButton.setVisibility(View.INVISIBLE);
		doneButton.setVisibility(View.INVISIBLE);
		markerText.setVisibility(View.INVISIBLE);
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		
		return true;
	}
	public static float convertPixelsToDp(float px,Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float dp = px / (metrics.densityDpi / 160f);
	    return dp;

	}
	
	
	
	GestureDetector.OnGestureListener gdListener = new GestureDetector.SimpleOnGestureListener(){
		@Override
		public boolean onSingleTapUp(MotionEvent e) { 
			float x = e.getX();
			float y = e.getY();
		    
		   
			
			
			return false;
		}
		@Override
		public void onLongPress(MotionEvent e) {
			
			delButton.setVisibility(View.INVISIBLE);
			doneButton.setVisibility(View.INVISIBLE);
			createButton.setVisibility(View.VISIBLE);
			markerText.setVisibility(View.VISIBLE);
			//dpX = e.getX();
			//dpY = e.getY();
			dpX = map.getLeft() + (int) e.getX() - 20;
			dpY = map.getTop() + (int) e.getY() -35 ;       
			mainTextView.setText("Enter marker's title");
			//ImageView tempMarker = new ImageView(context);
			//tempMarker.setImageResource(com.indoormap.R.drawable.maps_marker_small);
			//params.setMargins((int) dpX , (int) dpY, 0, 0);
			//relativeLayout.addView(tempMarker);
			
			
			
			
		
			
			
			
			
			
			
		}
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
		float velocityY) { return false;
		}
		//There are more gestures. RTFM.
		};
		
		
		

}
