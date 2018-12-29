<?php
    session_start();
    
    $user = array();
    
    $user['id'] = $_SESSION['id'];
    $user['email'] = $_SESSION['email'];
    $user['username'] = $_SESSION['username'];
    
?>


<h2><?php echo 
'Hello '.$user['username'].' your id is '.$user['id'].' and your email is '
.$user['email']; ?></h1>