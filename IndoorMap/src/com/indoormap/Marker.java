package com.indoormap;

import com.indoormap.R.id;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Marker  {
	private int id ;
	private String title;
	private float xCord,yCord;
	//private ImageView markerImage ;
	private TextView markerID;
	//private Bitmap markerImage;
	private LayoutInflater	l_Inflater;
	
	public int getId() {
		return id;
	}
	public Marker(int id , String title , Context context , float xCord , float yCord  )
	{
	
		this.id=id;
		this.title=title;
		this.xCord=xCord;
		this.yCord=yCord;
		
       // this.markerID.setText(id);
     //   this.setImageDrawable(imageMarker);
       /* this.markerImage.setX(xCord);
        this.markerImage.setY(yCord);*/
    

		
	}
	public float getxCord() {
		return xCord;
	}
	public void setxCord(float xCord) {
		this.xCord = xCord;
	}
	public float getyCord() {
		return yCord;
	}
	public void setyCord(float yCord) {
		this.yCord = yCord;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
