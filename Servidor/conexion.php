<?php

$con = conectarBD();

// Función que crea y devuelve un objeto de conexión a la base de datos y chequea el estado de la misma.
// IMPORTANTE: Si no se eliminan los "echo" del script, no funcionará la aplicación Android.

function conectarBD()
{
	//echo "Ejecutando conectarBD()<br>";

	$server = "localhost";
        $usuario = 
        $pass = 
        $BD = 

	//variable que guarda la conexión de la base de datos

	$connect = mysqli_connect($server, $usuario, $pass, $BD);

	//Comprobamos si la conexión ha tenido exito

	/*if($connect)
	{
           echo "La conexion de la base de datos se ha hecho satisfactoriamente.<br><br>";
        }
	else
	{
           echo "Ha sucedido un error inesperado en la conexion de la base de datos.<br>";
        }*/

        //devolvemos el objeto de conexión para usarlo en las consultas

        return $connect;
}

?>



