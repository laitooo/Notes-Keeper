<?php

/**
 * 
 */
/**
 * 
 */
class picturesOperations 
{
	
	function __construct()
	{
		require_once dirname(__FILE__).'/connect.php';

		$db = new connect();

		$this->con = $db->connectDB();
	}

	public function addPicture( $username ,$picture_name ){
		$num = $this->numPictures() + 1;
		$stmt = $this->con->prepare("INSERT INTO `profile` (`id`, `username`, `picture_name`) 
			VALUES ($num,?,?);");
		$stmt->bind_param("ss",$username,$picture_name);
		if($stmt->execute()){
			return 1;
		}else{
			return 2;
		}
	}

	public function numPictures(){
		    
		include_once dirname(__FILE__).'/constants.php';
		    
		$cons=mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME);
        $sqls="SELECT * FROM profile";

        if ($resulta=mysqli_query($cons,$sqls)){
            $rowcount=mysqli_num_rows($resulta);
        }

        mysqli_close($cons);

		return $rowcount;
	}

	public function findUserPicture($username){
		$stmt = $this->con->prepare("SELECT * FROM profile WHERE username = ?");
		$stmt->bind_param("s",$username);
		$stmt->bind_result($id, $username, $picture_name);
		$stmt->execute();
		$stmt->fetch();

		$ff = array();
		$ff['id'] = $id;
		$ff['username'] = $username;
		$ff['picture_name'] = $picture_name;
		return $ff;
	}

	public function userHasPicture($username){
		include_once dirname(__FILE__).'/connect.php';
		
		$fff = new connect();

		if ($res = mysqli_query($fff->connectDB(),"SELECT * FROM profile WHERE username = '".$username."'")) {
			$numN = mysqli_num_rows($res);
		}

		return $numN>0;
	}

	public function changePicture($id,$username,$new_picture_name){
		$stmt = $this->con->prepare("UPDATE profile SET picture_name = ? WHERE id = ? AND username = ?");
		$stmt->bind_param("sss",$new_picture_name,$id,$username);
		if($stmt->execute()){
			return 1;
		}else{
			return 2;
		}
	}


}