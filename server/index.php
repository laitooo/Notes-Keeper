<?php
    session_start();
?>
<html>
    <head>
        <style>
            #f{
                background-color: lightgray;
            }
            .center{
                color=blue;
                font-size=12px;
                text-align=center;
            }
        </style>
    </head>
    <body id="f">
    </body>
</html>
<?php
    

    
if (!empty($_SESSION['username'])) {

	$user = array();
    
    //$user['id'] = $_SESSION['id'];
    //$user['email'] = $_SESSION['email'];
    $user['username'] = $_SESSION['username'];

    
	echo '<h1 id="para1" class="center" style="color:blue;margin:50px 50px 50px 50px;
	background-color:LightGray;border:2px solid #0000ff;border-radius: 20px;padding:10px 20px 10px 20px;">
	Welcome to Notes keeper </h1>'.
	'<h2 style="color:blue;text-align: center;text-transform: capitalize;
	padding:10px 20px 10px 20px;font-family: Arial, Helvetica, sans-serif;
	font-style: oblique;font-size: 40px;">'.$user['username'].'</h2>'.
	'<button align="left" bacground="#ff0000"'.
	'onclick="location.href=\'http://notes-keeper.000webhostapp.com/logOutOperation.php\'"'.
	'type="button">Log out</button>';
	
	
	echo '<br></br>'.'<button align="left" bacground="#ff0000"'.
	'onclick="location.href=\'http://notes-keeper.000webhostapp.com/writeNote.html\'"'.
	'type="button">add a note</button>';

	require_once dirname(__FILE__).'/notesOperations.php';

	$nmm = new notesOperations();

	if ($nmm->findUserNumberOfNotes($user['username']) >0) {
	    echo '<h3>Here is your notes list</h3>';
		printNotes($user['username']);
	}else{
		echo '<h4>Sorry, you dont have any notes yet</h4>';
	}
	

}else{
	echo '<h1 id="para1" class="center" style="color:blue;margin:50px 50px 50px 50px;
	background-color:LightGray;border:2px solid #0000ff;border-radius: 20px;padding:10px 20px 10px 20px;">
	Welcome to Notes keeper </h1>'.
	'<button align="left" background="#0000ff" 
	onclick="location.href=\'http://notes-keeper.000webhostapp.com/signScreen.html\'"
	type="button">Sign up</button>
	<button align="right" background="#ff0000" 
	onclick="location.href=\'http://notes-keeper.000webhostapp.com/logScreen.html\'"
	type="button">Log in</button>';
}

 function printNotes($username){
	
	require_once dirname(__FILE__).'/connect.php';

	$db = new connect();

	$con = $db->connectDB();

	$stmt = $con->prepare("SELECT * FROM notes WHERE username = ?");
	$stmt->bind_param("s",$username);
	$stmt->bind_result($id, $username, $title, $content, $date);
	$stmt->execute();
	
	$counter = 0;
	
	while($stmt->fetch()){
	    
	    $counter = $counter +1;

	    echo '   '.$counter.
	    '<span style="margin-left:2em"> </span>'.$title.
	    '<span style="margin-left:2em"> </span>'.
	    $content.'<span style="margin-left:2em"> </span>'.
	    $date.'<br></br>';

	}
}
    
	



