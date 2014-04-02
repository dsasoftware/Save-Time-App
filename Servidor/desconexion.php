<?php

desconectarBD($con);

// Desconectar la conexion a la base de datos.
// IMPORTANTE: Si no se eliminan los "echo" la aplicación Android no funcionará.

function desconectarBD($conexion)
{
	//echo "<br><br>Ejecutando desconectarBD()<br>";

        //Cierra la conexión y guarda el estado de la operación en una variable

        $close = mysqli_close($conexion);

        //Comprobamos si se ha cerrado la conexión correctamente

        /*if($close)
        {
           echo "La desconexion de la base de datos se ha hecho satisfactoriamente.";
        }

        else
        {
           echo "Ha sucedido un error inesperado en la desconexion de la base de datos.";
        }*/

        //devuelve el estado del cierre de conexión

        return $close;
}

?>
