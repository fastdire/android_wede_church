<?php
   
    $con = mysqli_connect("localhost","root","","wede-church");
    $email = $_POST["email"];
    $password = $_POST["password"];

    $statement = mysqli_prepare($con,"SELECT * FROM users WHERE email = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $email,$password);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $id, $first_name, $last_name, $user_name, $city, $region, $sex, $phone_number, $email, $password, 
        $remember_token, $created_at , $updated_at);

    $response = array();  
    $response["success"] = false;

    while (mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["first_name"] = $first_name;
        $response["last_name"] = $last_name;
        $response["user_name"] = $user_name;
        $response["city"] = $city;
        $response["region"] = $region;
        $response["sex"] = $sex;
        $response["phone_number"] = $phone_number;
        $response["email"] = $email;
        $response["password"] = $password;
        $response["remember_token"] = $remember_token;
        $response["created_at"] = $created_at;
        $response["updated_at"] = $updated_at;
    }

        echo json_encode($response);
?>






