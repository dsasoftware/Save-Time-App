<?php

/* Script que calcula el tiempo restante para que sea atendido el cliente que
   ha reservado turno */

include 'conexion.php';

$sql = "SELECT tiempo FROM turnos";

$result = mysqli_query($con, $sql);

$rawdata = array();

$i=0;

while ($row = mysqli_fetch_array($result))
        {
                $rawdata[$i] = $row["tiempo"];
                $i++;
        }

$devuelve = 0;

for ($j=0; $j<=$i; $j++)
	{
		$devuelve += $rawdata[$j];
	}

echo $devuelve;

include 'desconexion';

?>


