<?php

	class connect {

		private $con;
		
		function __construct(){
			
		}

		function connectDB(){
			include_once dirname(__FILE__).'/constants.php';
			$this->con = new mysqli(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME);

			if (mysqli_connect_errno()) {
				$response['error'] = true;
			    $response['message'] = 'Error connecting to database';
			}

			return $this->con;
		}
	}