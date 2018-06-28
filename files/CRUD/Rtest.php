  
  <?php
     
      if($_SERVER["REQUEST_METHOD"]=="POST"){

      require 'connection.php';
      register_user();

      }

     function register_user(){

       global $connect;

              $first_name = $_POST["first_name"];
              $last_name = $_POST["last_name"];
              $user_name = $_POST["user_name"];
              $city = $_POST["city"];
              $region = $_POST["region"];
              $sex = $_POST["sex"];
              $phone_number = $_POST["phone_number"];
              $email = $_POST["email"];
              $password = $_POST["password"];

              $query = "insert into users(first_name, last_name, user_name, city, region, sex, phone_number, email, password) values 
              ('$first_name', '$last_name', '$user_name', '$city', '$region', '$sex', '$phone_number', '$email', '$password');";

              mysqli_query($connect, $query) or die (mysqli_error($connect));
              mysqli_close($connect);
        
              $response = array();  
              $response["success"] = true; 

              echo json_encode($response);

      }
          
   ?>


















