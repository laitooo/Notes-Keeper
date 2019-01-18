<?php

require 'groupNotesOperations.php';
require 'picturesOperations.php';

$response = array();

if ($_SERVER['REQUEST_METHOD']=='POST') {

	if (isset($_POST['id_group']) and isset($_POST['id_user']) and isset($_POST['content'])) {

		$db = new groupNotesOperations();

		$result = $db->addNote($_POST['id_group'] +0, $_POST['id_user'] , $_POST['content']);

		if ($result == 1) {
			$response['error'] = false;
			$response['message'] = 'your note has been saved';
			$response['date'] = now();
			$response['id'] = $db->numNotes();
			$db2 = new picturesOperations();
			$response['profile'] = $db2->getUserProfile($_POST['id_user'])
			//$response['content'] = $_POST['content'];
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
