<?php

//echo '<h1>Welcome to Notes keeper</h1>';

require 'notesOperations.php';

$response = array();

if ($_SERVER['REQUEST_METHOD']=='POST') {

	if (isset($_POST['username']) and isset($_POST['title']) and isset($_POST['content'])) {

		$db = new notesOperations();

		$result = $db->addNote($_POST['username'] , $_POST['title'] , $_POST['content']);

		if ($result == 1) {
			$response['error'] = false;
			$response['message'] = 'your note has been saved';
			$response['title'] = $_POST['title'];
			$response['content'] = $_POST['content'];
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