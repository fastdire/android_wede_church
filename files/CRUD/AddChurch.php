 <?php
     
      if($_SERVER["REQUEST_METHOD"]=="POST"){

      require 'connection.php';
      add_chrch();

      }

     function add_chrch(){

       global $connect;

 $church_name = $_POST["church_name"];
 $denomination   = $_POST["denomination"];
 $description = $_POST["description"];
 $location = $_POST["location"];
 $longitude = $_POST["longitude"];
 $latitude = $_POST["latitude"];
 $email = $_POST["email"];
 $phone_number = $_POST["phone_number"];
 

              $query ="insert into churches (church_name, denomination, description, location, longitude, latitude, email, phone_number,user_id) values ('$church_name', '$denomination', '$description', '$location', '$longitude', '$latitude', '$email', '$phone_number')";


              mysqli_query($connect, $query) or die (mysqli_error($connect));
              mysqli_close($connect);
        
              $response = array();  
              $response["success"] = true; 

              echo json_encode($response);

      }
          
   ?>