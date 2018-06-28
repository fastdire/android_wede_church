<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

include 'DatabaseConfig.php';

 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);

 $church_name = $_POST["church_name"];
 $denomination 	 = $_POST["denomination"];
 $description = $_POST["description"];
 $location = $_POST["location"];
 $longitude = $_POST["longitude"];
 $latitude = $_POST["latitude"];
 $email = $_POST["email"];
 $phone_number = $_POST["phone_number"];


$Sql_Query = "insert into churches (church_name, denomination, description, location, longitude, latitude, email, phone_number) values ('$church_name', '$denomination', '$description', '$location', '$longitude', '$latitude', '$email', '$phone_number')";

 if(mysqli_query($con,$Sql_Query))
{
 $response = array();  
             
    $response["success"] = true; 

    echo json_encode($response);
}
else
{
 echo "Something went wrong";
 }
 }
 mysqli_close($con);
?>