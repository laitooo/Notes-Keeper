<?php
	
	require_once dirname(__FILE__).'/connect.php';

	$db = new connect();

	$con = $db->connectDB();
	


	$stmt = $con->prepare("SELECT * FROM `groups`");
	//$stmt->bind_param("s",$_POST['username']);
	$stmt->bind_result($id, $groupname, $id_admin, $link );
	$stmt->execute();
	
	$counter = 0;
	
	$temp_array = array();
	
	while($stmt->fetch()){
	    
	    
	    $tmp = array();
	    $tmp["id"] = $id;
	    $tmp["groupname"] = $groupname;
	    $tmp["id_admin"] = $id_admin;
	    $tmp["link"] = $link;
	    
	    $temp_array[$counter] = $tmp;
        $counter = $counter +1;
	    /*echo '   '.$counter.
	    '<span style="margin-left:2em"> </span>'.$title.
	    '<span style="margin-left:2em"> </span>'.
	    $content.'<span style="margin-left:2em"> </span>'.
	    $date.'<br></br>';*/

	}
	
	//if($number_of_rows > 0) {
		//while ($row = mysqli_fetch_assoc($result)) {
		//	$temp_array[] = $row;
		//}
	//}
	
	
	
	//header('Content-Type: application/json');
	echo json_encode(array("groups"=>$temp_array),JSON_UNESCAPED_SLASHES);
	mysqli_close($con);
	
//}







?>
