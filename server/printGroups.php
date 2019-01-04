<?php
	
	require_once dirname(__FILE__).'/connect.php';
	require('groupsOperations.php');

	$db = new connect();

	$con = $db->connectDB();

	if(isset($_POST["id_user"])){
	$ind = $_POST["id_user"] +0;

	$stmt = $con->prepare("SELECT * FROM members WHERE id_user = ?");
	$stmt->bind_param("s",$ind);
	$stmt->bind_result($id_group, $id_user);
	$stmt->execute();
	
	$counter = 0;
	
	$temp_array = array();
	
	while($stmt->fetch()){
	    
	    
	    $tmp = array();
	    $tmp["id_group"] = $id_group;
	    $tmp["id_user"] = $id_user;
	    $db = new groupsOperations();
	    $ttt = $db->findGroup($id_group);
	    $tmp["groupname"] = $ttt["groupname"];
		$tmp["id_admin"] = $ttt["id_admin"];
		$tmp["link"] = $ttt["link"];
	    
	    $temp_array[$counter] = $tmp;
        $counter = $counter +1;
	    /*echo '   '.$counter.
	    '<span style="margin-left:2em"> </span>'.$title.
	    '<span style="margin-left:2em"> </span>'.
	    $content.'<span style="margin-left:2em"> </span>'.
	    $date.'<br></br>';*/
}

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
