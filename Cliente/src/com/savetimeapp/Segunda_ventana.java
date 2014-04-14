package com.savetimeapp;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Segunda_ventana extends Activity
{
	String android_ID;
	boolean hay_turno;
	Button btn_aceptar1, btn_aceptar2;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_segunda_ventana);
		
		android_ID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
		
		new Tarea0().execute();
				
		btn_aceptar1 = (Button)findViewById(R.id.btn_aceptar1);		
		btn_aceptar2 = (Button)findViewById(R.id.btn_aceptar2);
		
		
		//Implementamos el evento click del botón coger turno;crear entrada en el servidor
        btn_aceptar1.setOnClickListener(new OnClickListener() 
        {
             @Override
             public void onClick(View v) 
             {               	 
                  //Creamos el Intent
                  Intent intent = new Intent(Segunda_ventana.this, Ventana_seleccion.class);
                  
                  //Iniciamos la nueva actividad
                  startActivity(intent);     
             }
        });
        
        
      //Implementamos el evento click del botón ver turnos;leer los datos del servidor
        btn_aceptar2.setOnClickListener(new OnClickListener() 
        {
             @Override
             public void onClick(View v)
             {            	 
                 if (hay_turno == false)
                	 Toast.makeText(getApplicationContext(),"No ha reservado turno todavía", 
         					Toast.LENGTH_SHORT).show();  
                 else
                 {
                	 Intent intent = new Intent(Segunda_ventana.this, Ventana_Informacion2.class);
                     startActivity(intent);                    	 
                 }
             }
        });
    }
	
	@Override
	protected void onStart() 
	{
		super.onStart();
		new Tarea0().execute();		
	}
		
	
	public class Tarea0 extends AsyncTask<Void, Void, Boolean>
    {     	
		@Override
		protected Boolean doInBackground(Void... arg0) 
		{
			Reserva reserva = new Reserva();
			boolean r;
			try 
			{
				r = reserva.hayTurno(android_ID);
				return r;
			} 
			
			catch (ClientProtocolException e) 
			{
				e.printStackTrace();
			} 
			
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Boolean result) 
		{
			hay_turno = result;
		}
    }
}