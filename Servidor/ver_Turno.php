<?php

/*Script que realiza búsqueda de información de una determinada reserva. 
Solo es necesario enviar al Script la identidad del dispositivo.
Se realizan llamadas a otros scripts de PHP.*/

$identidad = $_GET["identidad"];

include 'conexion.php';

$sql = "SELECT * FROM turnos WHERE identidad LIKE '$identidad'";

mysqli_query($con, $sql);

$result =mysqli_query($con, $sql);

$rawdata = array();

$i=0;

while ($row = mysqli_fetch_array($result))
        {
                $rawdata[$i] = $row;
                $i++;
        }

echo json_encode($rawdata);

include 'desconexion.php';

?>

