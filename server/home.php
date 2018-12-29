<?php

//echo '<h1>Welcome to Notes keeper</h1>';

require 'operations.php';

$response = array();

if ($_SERVER['REQUEST_METHOD']=='POST') {

	if(isset($_POST['var'])){
		if ($_POST['var'] == 'login') {

			if ((isset($_POST['email']) and isset($_POST['password']))) {

				$db = new operations();

				if ($db->userLogin($_POST['email'] , $_POST['password'])) {
					$user = $db->findUserByEmail($_POST['email']);
					$response['error'] = false;
					$response['id'] = $user['id'];
					$response['email'] = $user['email'];
					$response['username'] = $user['username'];
					session_start();
					$_SESSION['id'] = $user['id'];
					$_SESSION['email'] = $user['email'];
					$_SESSION['username'] = $user['username'];
					//echo'<script> window.location="index.php"; </script> ';
				}else{
					$response['error'] = true;
					$response['message'] = 'Invalid username or password';
				}

			}else{
				$response['error'] = true;
				$response['message'] = 'Required fields are missing';
			}
			
		}elseif ($_POST['var'] == 'signup') {

			if (isset($_POST['email']) and isset($_POST['username']) and isset($_POST['password'])) {
				$dp = new operations();

				$result = $dp->createUser($_POST['username'], $_POST['email'],$_POST['password']);

				if ($result == 1) {
					$response['error'] = false;
					$response['message'] = 'User registerd successfully';
					$db = new operations();
					$user = $db->findUserByEmail($_POST['email']);
					$response['id'] = $user['id'];
					$response['email'] = $user['email'];
					$response['username'] = $user['username'];
					session_start();
					$_SESSION['id'] = $user['id'];
					$_SESSION['email'] = $user['email'];
					$_SESSION['username'] = $user['username'];
					//echo'<script> window.location="index.php"; </script> ';
				}elseif ($result == 2) {
					$response['error'] = true;
					$response['message'] = 'error occured, try again later';
				}elseif ($result == 0) {
					$response['error'] = true;
					$response['message'] = 'user name already exist';
				}
			}

		}
	}

}

echo json_encode($response);