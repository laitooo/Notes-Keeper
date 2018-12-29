<?php

/*if($_SERVER["REQUEST_METHOD"]=="POST"){
	include 'connect.php';
	showStudent();
}

function showStudent()
{*/
	
	require_once dirname(__FILE__).'/connect.php';

	$db = new connect();

	$con = $db->connectDB();
	
	if(isset($_POST["username"])){

	$stmt = $con->prepare("SELECT * FROM notes WHERE username = ?");
	$stmt->bind_param("s",$_POST["username"]);
	$stmt->bind_result($id, $username, $title, $content, $date);
	$stmt->execute();
	
	$counter = 0;
	
	$temp_array = array();
	
	while($stmt->fetch()){
	    
	    
	    $tmp = array();
	    $tmp["id"] = $id;
	    $tmp["title"] = $title;
	    $tmp["content"] = $content;
	    $tmp["date"] = $date;
	    	
	    
	    $temp_array[$counter] = $tmp;
        $counter = $counter +1;
	    /*echo '   '.$counter.
	    '<span style="margin-left:2em"> </span>'.$title.
	    '<span style="margin-left:2em"> </span>'.
	    $content.'<span style="margin-left:2em"> </span>'.
	    $date.'<br></br>';*/
}

		}else{
	    	    $temp_array = ["error"]; 
	    	}
	//if($number_of_rows > 0) {
		//while ($row = mysqli_fetch_assoc($result)) {
		//	$temp_array[] = $row;
		//}
	//}
	
	
	
	//header('Content-Type: application/json');
	echo json_encode(array("notes"=>$temp_array));
	mysqli_close($con);
	
	
	
//}







?>