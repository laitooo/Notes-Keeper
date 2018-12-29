<?php

/**
 * 
 */
class operations
{

	private $con;
	
	function __construct()
	{
		require_once dirname(__FILE__).'/connect.php';

		$db = new connect();

		$this->con = $db->connectDB();
	}

		public function createUser(  $username , $email , $pass ){
			if ($this->isUserExist($email,$username)) {
				return 0;
			}else{
				$num = $this->numUsers() + 1;
				$password = md5($pass);
				$stmt = $this->con->prepare("INSERT INTO `users` (`id`, `username`, `email` , `password`) 
					VALUES ($num,?,?,?);");
				$stmt->bind_param("sss",$username,$email,$password);

				if($stmt->execute()){
					return 1;
				}else{
					return 2;
				}
			}
		}

		public function numUsers(){
		    
		    include_once dirname(__FILE__).'/constants.php';
		    
		    $cons=mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME);
            $sqls="SELECT * FROM users";

            if ($resulta=mysqli_query($cons,$sqls)){
                $rowcount=mysqli_num_rows($resulta);
            }

            mysqli_close($cons);

			return $rowcount;
		}

		public function userLogin($email,$pass){
			$password = md5($pass);
			$stmt = $this->con->prepare("SELECT id FROM users WHERE email = ? OR password = ?");
			$stmt->bind_param("ss",$email,$password);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows >0;
		}

		public function findUserByEmail($email){
			$stmt = $this->con->prepare("SELECT * FROM users WHERE email = ?");
			$stmt->bind_param("s",$email);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}

		private function isUserExist($email, $username){
			$stmt = $this->con->prepare("SELECT id FROM users WHERE username = ? OR email = ?");
			$stmt->bind_param("ss",$username,$email);
			$stmt->execute();
			$stmt->store_result();
			return $stmt->num_rows >0;
		}

	
}