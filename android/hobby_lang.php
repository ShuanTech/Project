<?php
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['hobby']) && isset($_POST['lang'])){
	
	$sql=select_query("select * from usr_info where u_id='".$_POST['u_id']."'");
	$count=count($sql);
	
	if($count==''){
		$ins="insert into usr_info(u_id,language,hobbies) values('".$_POST['u_id']."','".$_POST['lang']."','".$_POST['hobby']."')";
		$result=mysql_query($ins);
	
			if($result>0){
				$response['msg']='Data Inserted';
				$response['success']=1;
				echo json_encode($response);
			}else{
				$response['msg']='Data Not Inserted';
				$response['success']=0;
				echo json_encode($response);
			}
	}else{
		
		$ins1="update usr_info set language='".$_POST['lang']."',hobbies='".$_POST['hobby']."' where u_id='".$_POST['u_id']."'";
		$result1=mysql_query($ins1);
	
			if($result1>0){
				$response['msg']='Data Inserted';
				$response['success']=1;
				echo json_encode($response);
			}else{
				$response['msg']='Data Not Inserted';
				$response['success']=0;
				echo json_encode($response);
			}
	}
	
	
	
}else{
	$response['msg']='No data post';
	$response['success']=0;
	echo json_encode($response);
}


function select_query($qry){
	
	try{
		require('config.php');
		
		$parse_qry=mysql_query($qry,$con);
		
		if(!$parse_qry){
			 die('Could not get data: ' . mysql_error());
		} 
		$res_qry=array();
		while(($row = mysql_fetch_array($parse_qry,MYSQL_ASSOC))!=false){
			$res_qry[]=$row;
		}
		return $res_qry;
		mysql_close($con);
	}catch(Exception $e){}
	
	
}

?>