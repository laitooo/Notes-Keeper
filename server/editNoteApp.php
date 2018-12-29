<?php


require 'notesOperations.php';

$response = array();

if ($_SERVER['REQUEST_METHOD']=='POST') {

	if (isset($_POST['id']) and isset($_POST['newTitle']) and isset($_POST['newContent'])) {

		$db = new notesOperations();

		$result = $db->editNote($_POST['id'] + 0, $_POST['newTitle'] , $_POST['newContent']);

		if ($result == 1) {
			$response['error'] = false;
			$response['message'] = 'your note has been edited';
			//$response['id'] = $_POST['id'];
			//$response['title'] = $_POST['newTitle'];
			//$response['content'] = $_POST['newContent'];
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