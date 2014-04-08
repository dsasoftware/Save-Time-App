package com.savetimeapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
 
public class Portada extends Activity 
{ 
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
 
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);
 
        new Handler().postDelayed(
        		new Runnable() 
        		{ 
        			@Override
        			public void run() 
        			{
        				Intent i = new Intent(Portada.this, Segunda_ventana.class);
        				startActivity(i);
        				finish();
        			}
        		}, SPLASH_TIME_OUT);
    }
 
}