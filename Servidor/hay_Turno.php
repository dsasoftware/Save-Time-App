<?php

/* Script que comprueba si el dispositivo móvil ya ha reservado turno anteriormente.
   Hay que pasarle como parámetro la identidad del dispositivo. */

$identidad = $_GET["identidad"];

include 'conexion.php';

$sql = "SELECT * FROM turnos WHERE identidad LIKE '$identidad'";

$result = mysqli_query($con, $sql);

$rawdata = array();

$i=0;

while ($row = mysqli_fetch_array($result))
        {
                $rawdata[$i] = $row;
                $i++;
        }

if ($rawdata[0] == "")
{
	echo "false";
}

else
{
	echo "true";
}

include 'desconexion.php';

?>
