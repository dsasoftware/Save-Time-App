<?php

/*Script que realiza la reserva de un nuevo turno mediante la adicion de una fila en la tabla "turnos" de la BD.
Se realizan llamadas a otros scripts de PHP.*/

$tiempo = $_GET["tiempo"];
$hora_reserva = $_GET["hora_reserva"];
$tipo_gestion = $_GET["tipo_gestion"];
$identidad = $_GET["identidad"];

include 'conexion.php';

$sql = "INSERT INTO turnos (tiempo, hora_reserva, tipo_gestion, identidad) VALUES ($tiempo,'$hora_reserva', '$tipo_gestion', '$identidad')";

mysqli_query($con, $sql);

$result = mysqli_query($con,"SELECT * FROM turnos");

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

