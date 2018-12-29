<?php

//echo '<h1>Welcome to Notes keeper</h1>';

require 'groupsOperations.php';

$response = array();

if ($_SERVER['REQUEST_METHOD']=='POST') {

	if (isset($_POST['groupname']) and isset($_POST['adminname'])) {

		$db = new groupsOperations();
        $num = $db->numGroups(); 
        $link = 'http://notes-keeper.000webhostapp.com/noteGroup.php?'.'id='.$num;
		$result = $db->craeteGroup($_POST['groupname'] , $_POST['adminname'] , $link);

		if ($result == 1) {
		    
			$response['error'] = false;
			$response['message'] = 'your note has been saved';
			$response['id'] = $num+1;
			$response['groupname'] = $_POST['groupname'];
			$response['adminname'] = $_POST['adminname'];
			$response['link'] = $link;
			//echo'<script> window.location="index.php"; </script> ';
		}elseif ($result == 2) {
			$response['error'] = true;
			$response['message'] = 'error occured, try again later';
		}

	}else{
		$response['error'] = true;
		$response['message'] = 'Required fields are missing';
	}
}

echo json_encode($response);