package com.testapp;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.view.MotionEvent;
import android.view.View;

import com.tinidream.ngage.nGage;



public class MyView extends View {
	nGage ngage;
	int width,height;
	Paint paintHeader = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
	Paint paintBG = new Paint();
	final float fontScale = getContext().getResources().getDisplayMetrics().density;
	
	
	public MyView (Activity activity){
		super (activity);
		ngage=nGage.getInstance();
	
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN ) {

			if (event.getY()<height/3){
				ngage.showAchievements();
				
			} else if (event.getY()<height*2/3){
				ngage.showInterstitial();
				
			}else  { 
				ngage.showIncentedInterstitial();
			}
		
		}
		return true;
	}
	
	@Override
	 protected void onSizeChanged(int widthMeasureSpec, int heightMeasureSpec, int xOld, int yOld){
		//called once on orientation change
		
	     super.onSizeChanged(widthMeasureSpec, heightMeasureSpec, xOld, yOld);
    
	    width = MeasureSpec.getSize(widthMeasureSpec);
		height = MeasureSpec.getSize(heightMeasureSpec);
		    
		paintHeader.setColor(Color.WHITE);
		paintHeader.setStrokeWidth(2);
		paintHeader.setTextAlign(Align.CENTER); 
		paintHeader.setTextSize(scaleF(17));
		paintHeader.setFlags(Paint.FAKE_BOLD_TEXT_FLAG);
		 
		paintBG.setColor(Color.BLACK);
	}
		
	@Override	 
	protected void onDraw(Canvas c) {
	        super.onDraw(c);
	        
	        c.drawRect(0, 0, width, height, paintBG);
	        
	        c.drawText("Touch here to open nGage Dialog", width/2, height/4, paintHeader);//bottom anchor
	        c.drawText("Touch here to open nGage non-incentivised Intersticial", width/2, height*2/4, paintHeader);//bottom anchor
	        c.drawText("Touch here to open nGage incentivised Intersticial", width/2, height*3/4, paintHeader);//bottom anchor
	  
	        invalidate();
	}
	
	int scaleF(int pixels){ 
	    return (int) (pixels * fontScale + 0.5f);
	       
	}
		
}
