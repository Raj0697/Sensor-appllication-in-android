package com.example.sensor_new;

import android.support.v7.app.ActionBarActivity;


import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements SensorEventListener {
	TextView t;
	SensorManager sm;
	RelativeLayout r;
	Sensor s;
	private boolean icolor = false;
	private View view;
	private long lastupdate;
	MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		view = findViewById(R.id.textView1);
		r = (RelativeLayout)findViewById(R.id.rl);
		view.setBackgroundColor(Color.GREEN);
		//r.setBackgroundColor(Color.BLUE);
		sm = (SensorManager)getSystemService(SENSOR_SERVICE);
		lastupdate = System.currentTimeMillis();
		
		mp = MediaPlayer.create(this, R.raw.tokyo);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
		if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER)
		{
			getAccelerometer(event);
		}
	}
	
	private void getAccelerometer(SensorEvent event)
	{
		
		float[]  values = event.values;
		
		
		
		float x = values[0];  
        float y = values[1];  
        float z = values[2];  
        
        float accelerate = (x*x+y*y+z*z)
        /(SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        
        long actualTime = System.currentTimeMillis();
        Toast.makeText(this, String.valueOf(accelerate) + " " + SensorManager.GRAVITY_EARTH, Toast.LENGTH_LONG).show();
        
        if(accelerate >= 2)
        {
        	if(actualTime-lastupdate < 200)
        	{
        		return;
        	}
        	lastupdate = actualTime;
        	if(icolor)
        	{
        		//view.setBackgroundColor(Color.GREEN);
        		r.setBackgroundColor(Color.CYAN);
        		ImageView img = (ImageView)findViewById(R.id.imageView1);
        		Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.blink);
        		img.startAnimation(anim2);
        		mp.pause();
        		
        	}else {
        		//view.setBackgroundColor(Color.RED);
        		r.setBackgroundResource(R.drawable.cartoonn);
        		mp.start();
        		
        		ImageView img = (ImageView)findViewById(R.id.imageView1);
        		Animation anim = AnimationUtils.loadAnimation(this, R.anim.clockwise);
        		img.startAnimation(anim);
        		
        	}
        	icolor = !icolor;
        }
	
	}
		
		
		
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		sm.unregisterListener(this);
	}
}
