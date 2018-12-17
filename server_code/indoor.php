<?php
header('Content-Type: application/json');

	// Access the DB table,
	// contains the building name and classroom information,
	// to get floor information for the building indoors.
	$db_host = "myymdb.cbs4aawfycnj.ap-northeast-2.rds.amazonaws.com:3306";
	$db_user = "bbang5368";
	$db_passwd = "qlalfqjsgh";
	$db_name = "bbang5368";

	$conn = mysqli_connect($db_host, $db_user, $db_passwd, $db_name);

	// Check connection
	if (!$conn) {
    	die("Connection failed: " . mysqli_connect_error());
	}

	//실내 wifi bssid인 macAddr으로 indoor table에 접근
	$sql="select * from indoor where macAddr = ".$_GET['macAddr'];

	$result = mysqli_query($conn, $sql);

	$Return_FinalArray = array();

    if ($result->num_rows > 0) {
        while( $row = mysqli_fetch_array($result) ) {

        	$my_array =	array(
          		'indoor_floor' => $row["floor"],     		
        		'indoor_content' => $row["content"]
        	);

        	array_push($Return_FinalArray,$my_array);
        	$groupArray["information"] = $Return_FinalArray;
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