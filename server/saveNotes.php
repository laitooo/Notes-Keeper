<?php

//echo '<h1>Welcome to Notes keeper</h1>';

require 'notesOperations.php';

session_start();
    
if (!empty($_SESSION['username'])) {

	$username = $_SESSION['username'];

}else  {
	echo'<script> window.location="index.php"; </script> ';
}


$response = array();

if ($_SERVER['REQUEST_METHOD']=='POST') {

	if ((isset($_POST['title']) and isset($_POST['content']))) {

		$db = new notesOperations();

		$result = $db->addNote($username , $_POST['title'] , $_POST['content']);

		if ($result == 1) {
			$response['error'] = false;
			$response['message'] = 'your note has been saved';
			echo'<script> window.location="index.php"; </script> ';
		}elseif ($result == 2) {
			$response['error'] = true;
			$response['message'] = 'error occured, try again later';
		}

	}else{
		$response['error'] = true;
		$response['message'] = 'Required fields are missing';
	}
}

		