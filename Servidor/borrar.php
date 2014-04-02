<?php
/* Script que borra la reserva realizada. Hay que pasarle la identidad del dispositivo
como parametro para que funcione*/

$identidad = $_GET["identidad"];

include 'conexion.php';

$sql = "DELETE FROM turnos WHERE identidad = '$identidad'";

mysqli_query($con, $sql);

include'desconexion.php';

?>
