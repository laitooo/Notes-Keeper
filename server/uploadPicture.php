<?php

require 'picturesOperations.php';

    $currentDir = getcwd();
    $uploadDirectory = "/profile_pictures/";

    $response = array();

    $fileExtensions = ['jpeg','jpg','png'];

    $db = new picturesOperations();
    
    if ($_POST["submit"] and isset($_POST['username'])) {
        
        $fileName = $_FILES['myfile']['name'];
        $fileSize = $_FILES['myfile']['size'];
        $fileTmpName  = $_FILES['myfile']['tmp_name'];
        $fileType = $_FILES['myfile']['type'];
        $tmp = explode('.',$fileName);
        $fileExtension = strtolower(end($tmp));

        
        
        if (! in_array($fileExtension,$fileExtensions)) {
            $response['error'] = true;
            $response['message'] = "This file extension is not allowed. Please upload a JPEG or PNG file";
        }else{
            if ($fileSize > 2000000) {
                $response['error'] = true;
                $response['message'] = "This file is more than 2MB. Sorry, it has to be less than  to 2MB";
            }else{
                
                $bo = $db->userHasPicture($_POST['username']);
                if(!$bo){
                    $index = $db->numPictures();
                    $name = 'profile_'.($index+1).'.'.$fileExtension;
                    $result = $db->addPicture($_POST['username'],$name);
                }else{
                    $fff = $db->findUserPicture($_POST['username']);
                    if(is_file($currentDir . $uploadDirectory . $fff['picture_name'])){
                        unlink($currentDir . $uploadDirectory . $fff['picture_name']);
                    }
                    $name = 'profile_'.$fff['id'].'.'.$fileExtension;
                    $result = $db->changePicture($fff['id'],$fff['username'],$name);
                }
                if ($result == 1) {
                
                    $uploadPath =  $currentDir . $uploadDirectory . $name;
                    //basename($fileName); 
                    
                    $didUpload = move_uploaded_file($fileTmpName, $uploadPath);
                
                    if ($didUpload) {
                        $response['error'] = false;
                        $response['message'] = "The file " . basename($fileName) . " has been uploaded";
                    } else {
                        $response['error'] = true;
                        $response['message'] = "An error occurred somewhere. Try again or contact the admin";
                    }
                }elseif ($result == 2) {
                    $response['error'] = true;
                    $response['message'] = 'error occured, try again later';
                }else {
                    $response['error'] = true;
                    $response['message'] = 'error occured, try again later';
                }
            }
        }

    }else{
        $response['error'] = true;
        $response['message'] = "no file posted";
    }
echo json_encode($response);