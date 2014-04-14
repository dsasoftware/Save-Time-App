package com.savetimeapp;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.widget.TextView;

public class Ventana_Informacion extends Activity 
{
	TextView txt_turno, txt_hora_reserva, txt_tipo_gestion, txt_tiempo;
	JSONObject json;
	String android_ID;
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
	{    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana__informacion);
        
        txt_turno = (TextView) findViewById(R.id.txt_turno);
        txt_hora_reserva = (TextView) findViewById(R.id.txt_hora_reserva);
        txt_tipo_gestion = (TextView) findViewById(R.id.txt_tipo_gestion);
        txt_tiempo = (TextView) findViewById(R.id.txt_tiempo);
        
     // Obtengo la ID del dispositivo móvil:
    	
    	android_ID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
                
        // Recoge el mensaje del Intent:
        
        Intent intent = getIntent();
        String message = intent.getStringExtra(Ventana_seleccion.EXTRA_MESSAGE);
        
        // Llamo al thread que establecerá comunicación con el Servidor:
        
        new Tarea().execute(message, "id_user", "hora_reserva", "tipo_gestion");
    }

	
    public class Tarea extends AsyncTask<String, Void, String[]>
    {
    	@Override
    	protected String[] doInBackground(String... params) 
    	{    		
    		Reserva reserva = new Reserva();
    		String turno, hora, gestion, tiempo;
    		
    		try 
    		{
    			// Llamo a la función de la clase Reserva que reservará el turno:
    			
				json = reserva.reservarTurno(params[0], android_ID);
				turno = json.getString(params[1]);
				hora = json.getString(params[2]);
				gestion = json.getString(params[3]);
				
				tiempo = reserva.calularTiempo();
				
				String []array = {turno,hora,gestion, tiempo};				
				return array;
			}     		
    		catch (ClientProtocolException e) 
    		{
				e.printStackTrace();
			}     		
    		catch (IOException e) 
    		{
				e.printStackTrace();
			}     		
    		catch (JSONException e)
    		{
				e.printStackTrace();
			}    		
    		return null;
    	}
    	
    	@Override
    	protected void onPostExecute(String[] result)
    	{    	
    		// Aquí realizo los cambios en la interfaz:
    		
    		txt_turno.setText(result[0]);
    		txt_hora_reserva.setText(result[1]);
    		txt_tipo_gestion.setText(result[2]);  
    		txt_tiempo.setText("Será atendido aproximadamente en " + result[3] + " minutos.");
    	}
    }
}