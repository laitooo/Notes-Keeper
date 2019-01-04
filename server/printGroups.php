<?php
	
	require_once dirname(__FILE__).'/connect.php';
	require('groupsOperations.php');

	$db = new connect();

	$con = $db->connectDB();
	
	$temp_array = array();

	if(isset($_POST["id_user"])){
	$ind = (int)$_POST["id_user"];

	$stmt = $con->prepare("SELECT * FROM `members` WHERE `id_user` = ?");
	$stmt->bind_param("s",$ind);
	$stmt->bind_result($id_group, $id_user);
	$stmt->execute();
	
	$counter = 0;
	$tmp = array();
	while($stmt->fetch()){
	    
	    
	    
	    $tmp["id_group"] = $id_group;
	    $tmp["id_user"] = $id_user;
	    $db = new groupsOperations();
	    $ttt = $db->findGroup($id_group);
	    $tmp["groupname"] = $ttt["groupname"];
		$tmp["id_admin"] = $ttt["id_admin"];
		$tmp["link"] = $ttt["link"];
	    
	    $temp_array[$counter] = $tmp;
        $counter++;

}

		}

	
	
	
	//header('Content-Type: application/json');
	echo json_encode(array("groups"=>$temp_array),JSON_UNESCAPED_SLASHES);
	mysqli_close($con);
	
	
	







?>
