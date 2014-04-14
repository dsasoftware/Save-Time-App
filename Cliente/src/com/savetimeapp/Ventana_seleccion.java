package com.savetimeapp;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.ClientProtocolException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class Ventana_seleccion extends Activity 
{		
	public final static String EXTRA_MESSAGE = "com.savetimeapp.MESSAGE";
	String seleccion = "Asociacion", android_ID;
	RadioGroup rg;
	RadioButton asociacion, aviso, ayudas, ciudad, negocio, deporte;

	boolean hay_turno;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ventana_seleccion);	
		
		android_ID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
		
		new Tarea0().execute();
		
		rg = (RadioGroup) findViewById(R.id.radioGroup);
		
		asociacion = (RadioButton)findViewById(R.id.asociacion);
		aviso 	   = (RadioButton)findViewById(R.id.avisos);
		ayudas     = (RadioButton)findViewById(R.id.ayudas);
		ciudad     = (RadioButton)findViewById(R.id.ciudad);
		negocio    = (RadioButton)findViewById(R.id.negocios);
		deporte    = (RadioButton)findViewById(R.id.deporte);	
		
		//Listener
		
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) 
			{
				int id = rg.getCheckedRadioButtonId();
																
				if (id == asociacion.getId())
					seleccion = "Asociacion";
				else if (id == aviso.getId())
					seleccion = "Aviso";
				else if (id == ayudas.getId())
					seleccion = "Ayudas";
				else if (id == ciudad.getId())
					seleccion = "Ciudad";
				else if (id == negocio.getId())
					seleccion = "Negocio";
				else if (id == deporte.getId())
					seleccion = "Deporte";				
			}			
		});
	}	
		 
	
	public void reservar(View view) throws InterruptedException, ExecutionException 
	{
		if (hay_turno == false)
		{	
			hay_turno = true;
			
			Intent intent = new Intent(this, Ventana_Informacion.class);
			intent.putExtra(EXTRA_MESSAGE, seleccion);
			startActivity(intent);
		}
		else
			Toast.makeText(getApplicationContext(),"Anule su reserva para realizar una nueva", 
					Toast.LENGTH_SHORT).show();			
	} 
	
	
	public void borrar (View view)
	{		
		if (hay_turno == true)
		{
			hay_turno = false;
			new Tarea1().execute();
			Toast.makeText(getApplicationContext(), "Reserva anulada con éxito", 
					Toast.LENGTH_SHORT).show();		
		}	
		
		else
			Toast.makeText(getApplicationContext(), "No ha realizado una reserva", 
				Toast.LENGTH_SHORT).show();		
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
	
	
	public class Tarea1 extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params) 
		{
			Reserva reserva = new Reserva();
			
			try 
			{
				reserva.borrarTurno(android_ID);
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
	}	
}