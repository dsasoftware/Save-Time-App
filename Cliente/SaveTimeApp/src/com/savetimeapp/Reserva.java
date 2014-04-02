package com.savetimeapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Toast;

public class Reserva 
{	
	// Variables de tiempo (minutos) constantes:
	
	final int TIME_ASOCIACION = 15;
	final int TIME_AVISO = 5;
	final int TIME_AYUDAS = 25;
	final int TIME_CIUDAD = 10;
	final int TIME_NEGOCIO = 30;
	final int TIME_DEPORTE = 10;
	
	// Variables de clase:
	
	protected int turno;
	protected int tiempo;
	protected String tipo_gestion;
	protected String hora_reserva;
	protected String identidad;
	
	//	Constructor por defecto:
	
	public Reserva()
	{
		turno = 0;
		tiempo = 0;
		tipo_gestion = "";
		hora_reserva = "";
		identidad = "";
	}
		
	// MÉTODOS:
		
	public JSONObject reservarTurno(String strin, String ident) 
			throws ClientProtocolException, IOException, JSONException
	{
		// Se actualiza el tipo de gestión:
		
		tipo_gestion = strin;	
		identidad = ident;
		
		// Se establece el tiempo promedio de atención:
		
		if (tipo_gestion.equals("asociacion"))
			tiempo = TIME_ASOCIACION;
		else if (tipo_gestion.equals("aviso"))
			tiempo = TIME_AVISO;
		else if (tipo_gestion.equals("ayudas"))
			tiempo = TIME_AYUDAS;
		else if (tipo_gestion.equals("ciudad"))
			tiempo = TIME_CIUDAD;
		else if (tipo_gestion.equals("negocio"))
			tiempo = TIME_NEGOCIO;
		else if (tipo_gestion.equals("deporte"))
			tiempo = TIME_DEPORTE;				
		
		// Captura del tiempo del sistema para saber la hora de la reserva:
					
		Calendar hora = Calendar.getInstance();
		String h, m, s;				
		h = Integer.toString(hora.get(Calendar.HOUR_OF_DAY));
		m = Integer.toString(hora.get(Calendar.MINUTE));
		s = Integer.toString(hora.get(Calendar.SECOND));
		hora_reserva = h+":"+m+":"+s;
							   	
		// Creamos la conexión al PHP reservar.php:
		
		HttpClient cliente = new DefaultHttpClient();
		HttpGet htpget = new HttpGet("http://192.168.1.33/reserva.php?tiempo="+
		Integer.toString(tiempo)+"&hora_reserva="+hora_reserva+"&tipo_gestion="+tipo_gestion
		+"&identidad="+identidad);
			
		HttpResponse r = cliente.execute(htpget);
		int status = r.getStatusLine().getStatusCode();
		if (status == 200) // Conexión correcta
		{
			HttpEntity e = r.getEntity();
			String data = EntityUtils.toString(e);
			JSONArray timeline =  new JSONArray(data);
			
			// Se recoge el último objeto creado en la BD.
			
			JSONObject last = timeline.getJSONObject(timeline.length()-1);
			return last;			
		}
		else
		{
			// Este toast creo que no funcionará:
			Toast.makeText( null, "Error", Toast.LENGTH_SHORT).show();
			return null;
		}		
	}
	

	public void borrarTurno(String ident) 
			throws ClientProtocolException, IOException
	{	
		identidad = ident;
		
		HttpClient cliente = new DefaultHttpClient();
		HttpGet htpget = new HttpGet("http://192.168.1.33/borrar.php?&identidad="+identidad);
		cliente.execute(htpget);	
	}
	
	
	public boolean hayTurno(String ident) 
			throws ClientProtocolException, IOException
	{
		identidad = ident;
		BufferedReader bfr;
		String resultado;
		
		HttpClient cliente = new DefaultHttpClient();
		HttpGet htpget = new HttpGet("http://192.168.1.33/hay_Turno.php?&identidad="+identidad);
		
		HttpResponse respuesta = cliente.execute(htpget);
		bfr = new BufferedReader(new InputStreamReader(respuesta.getEntity().getContent()));
		StringBuffer stb = new StringBuffer("");
		String linea = "";
		while((linea = bfr.readLine()) != null)
		{
			stb.append(linea);
		}
		bfr.close();
		resultado = stb.toString();
		
		if (resultado.equals("true") == true)
			return true;
		else 
			return false;
	}
	
	
	public String calularTiempo() 
			throws ClientProtocolException, IOException
	{		
		BufferedReader bfr;
		String resultado;
		
		HttpClient cliente = new DefaultHttpClient();
		HttpGet htpget = new HttpGet("http://192.168.1.33/calcular_Tiempo.php");
		
		HttpResponse respuesta = cliente.execute(htpget);
		bfr = new BufferedReader(new InputStreamReader(respuesta.getEntity().getContent()));
		StringBuffer stb = new StringBuffer("");
		String linea = "";
		while((linea = bfr.readLine()) != null)
		{
			stb.append(linea);
		}
		bfr.close();
		resultado = stb.toString();
				
		return resultado;
	}
}