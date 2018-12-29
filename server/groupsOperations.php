<?php

/**
 * 
 */
/**
 * 
 */
class groupsOperations 
{
	
	function __construct()
	{
		require_once dirname(__FILE__).'/connect.php';

		$db = new connect();

		$this->con = $db->connectDB();
	}

	public function createGroup( $gruopname , $adminname , $link){
		$num = $this->numGroups() + 1;
		$stmt = $this->con->prepare("INSERT INTO `groups` (`id`, `gruopname`, `adminname` ,`link`) 
			VALUES ($num,?,?,?");
		$stmt->bind_param("sss",$gruopname,$adminname,$link);
		if($stmt->execute()){
			return 1;
		}else{
			return 2;
		}
	}

	public function numGroups(){
		    
		include_once dirname(__FILE__).'/constants.php';
		    
		$cons=mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME);
        $sqls="SELECT * FROM groups";

        if ($resulta=mysqli_query($cons,$sqls)){
            $rowcount=mysqli_num_rows($resulta);
        }

        mysqli_close($cons);

		return $rowcount;
	}

	public function findGruop($id){
		$stmt = $this->con->prepare("SELECT * FROM groups WHERE id = ?");
		$stmt->bind_param("s",$id);
		$stmt->bind_result($id, $gruopname, $adminname, $link);
		$stmt->execute();
		$stmt->fetch();

		$ff = array();
		$ff['id'] = $id;
		$ff['gruopname'] = $gruopname;
		$ff['adminname'] = $adminname;
		$ff['link'] = $link;
		return $ff;
	}

	public function isUserAdmin($id,$username){
		$stmt = $this->con->prepare("SELECT * FROM groups WHERE id = ?");
		$stmt->bind_param("s",$id);
		$stmt->bind_result($id, $gruopname, $adminname, $link);
		$stmt->execute();
		$stmt->fetch();

		return $adminname==$username;
	}



}