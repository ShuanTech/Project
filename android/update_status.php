<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['status'])){
	
	$chk=select_query("select objective from usr_info where u_id='".$_POST['u_id']."'");
	$cnt=count($chk);
	
	if($cnt==0){
		$ins="insert into usr_info(u_id,objective) values('".$_POST['u_id']."','".$_POST['status']."')";
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
		$upt="update usr_info set objective='".$_POST['status']."' where u_id='".$_POST['u_id']."'";
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