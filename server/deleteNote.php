<?php


require 'notesOperations.php';

$response = array();

if ($_SERVER['REQUEST_METHOD']=='POST') {

	if (isset($_POST['id'])) {

		$db = new notesOperations();

		$result = $db->deleteNote($_POST['id']);

		if ($result == 1) {
			$response['error'] = false;
			$response['message'] = 'note deleted';
		}elseif ($result == 2) {
			$response['error'] = true;
			$response['message'] = 'error occured, try again later';
		}

	}else{
		$response['error'] = true;
		$response['message'] = 'no selected note';
	}
}

echo json_encode($response); 
