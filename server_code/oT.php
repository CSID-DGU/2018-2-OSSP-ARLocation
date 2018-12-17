<?php
header('Content-Type: application/json');

	// Access the DB table,
	// contains the name of the building, location information (latitude, longitude, altitude)
	// to get the information of the building outdoors, 
	$db_host = "myymdb.cbs4aawfycnj.ap-northeast-2.rds.amazonaws.com:3306";
	$db_user = "bbang5368";
	$db_passwd = "qlalfqjsgh";
	$db_name = "bbang5368";

	$conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name);

	// Check connection
	if (!$conn) {
 		die("Connection failed: " . mysqli_connect_error());
	}

	$sql="select * from building";
	
	$result = mysqli_query($conn, $sql);

	$Return_FinalArray = array();

    if ($result->num_rows > 0) {
        while( $row = mysqli_fetch_array($result) ) {

        	$my_array =	array(
        			'building_name' => $row["bname"],
        			'building_latitude' => $row["latitude"],
        			'building_longitude' => $row["longitude"],
        			'building_altitude' => $row["altitude"],        		
        		);

			array_push($Return_FinalArray,$my_array);
        	$groupArray["BUILDING"] = $Return_FinalArray;
        }
    }
    else {
   		echo '{"result": 0}';
    }
   
    // encode php array to json string
	$RESULT = json_encode($groupArray, JSON_UNESCAPED_UNICODE);
	
	echo $RESULT;

// handle error
	if (json_last_error() > 0) {
    	echo json_last_error_msg() . PHP_EOL;
	}
?>    
 
