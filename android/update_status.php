<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['status'])){
	
	$chk="select * from user_imgs where u_id='".$_POST['u_id']."'";
	$cnt=count($chk);
	
	if($cnt==''){
		$ins="insert into user_imgs(u_id,status) values('".$_POST['u_id']."','".$_POST['status']."')";
		$res=mysql_query($ins);
			if($res>0){
				$response['message']="Inserted";
				$response['success']=1;
				echo json_encode($response);
			}else{
				$response['message']="Not Inserted";
				$response['success']=0;
				echo json_encode($response);
			}
	}else{
		$upt="update user_imgs set status='".$_POST['status']."' where u_id='".$_POST['u_id']."'";
		$res=mysql_query($upt);
			if($res>0){
				$response['message']="Inserted";
				$response['success']=1;
				echo json_encode($response);
			}else{
				$response['message']="Not Inserted";
				$response['success']=0;
				echo json_encode($response);
			}
	}
	
}else{
	$response['message']="NO Data Post";
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