<?php

/**
 * 
 */
/**
 * 
 */
class notesOperations 
{
	
	function __construct()
	{
		require_once dirname(__FILE__).'/connect.php';

		$db = new connect();

		$this->con = $db->connectDB();
	}

	public function addNote( $id_user , $title , $content ){
		$num = $this->numNotes() + 1;
		$stmt = $this->con->prepare("INSERT INTO `notes` (`id`, `id_user`, `title` , `content`, `date`) 
			VALUES ($num,?,?,?,now());");
		$stmt->bind_param("sss",$id_user,$title,$content);
		if($stmt->execute()){
			return 1;
		}else{
			return 2;
		}
	}

	public function numNotes(){
		    
		include_once dirname(__FILE__).'/constants.php';
		    
		$cons=mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME);
        $sqls="SELECT * FROM notes";

        if ($resulta=mysqli_query($cons,$sqls)){
            $rowcount=mysqli_num_rows($resulta);
        }

        mysqli_close($cons);

		return $rowcount;
	}

	public function findUserNumberOfNotes($id_user){
		include_once dirname(__FILE__).'/connect.php';
		
		$fff = new connect();

		if ($res = mysqli_query($fff->connectDB(),"SELECT * FROM notes WHERE id_user = '".$id_user."'")) {
			$numN = mysqli_num_rows($res);
		}

		return $numN;
	}

	public function editNote($id,$newTitle,$newContent){
		$stmt = $this->con->prepare("UPDATE notes SET title = ?, content = ? WHERE id = ?");
		$stmt->bind_param("sss",$newTitle,$newContent,$id);
		if($stmt->execute()){
			return 1;
		}else{
			return 2;
		}
	}

	public function deleteNote($id){
		$stmt = $this->con->prepare("DELETE FROM notes WHERE id = ?");
		$stmt->bind_param("s",$id);
		if($stmt->execute()){
			return 1;
		}else{
			return 2;
		}
	}


}
