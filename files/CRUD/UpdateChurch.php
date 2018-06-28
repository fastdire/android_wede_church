<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

include 'DatabaseConfig.php';

 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);

 $id = $_POST['id'];
 $church_name = $_POST['church_name'];
 $denomination = $_POST['denomination'];
 $description = $_POST['description'];
 $location = $_POST['location'];	
 $longitude = $_POST["longitude"];
 $latitude = $_POST["latitude"];
 $email = $_POST["email"];
 $phone_number = $_POST["phone_number"];


$Sql_Query = "update churches set church_name = '$church_name', denomination = '$denomination', description = '$description', location = '$location', longitude = '$longitude', latitude = '$latitude', email = '$email', phone_number = '$phone_number' WHERE id = $id";

 if(mysqli_query($con,$Sql_Query))
{
 echo 'Record Updated Successfully';
}
else
{
 echo 'Something went wrong';
 }
 }
 mysqli_close($con);
?>