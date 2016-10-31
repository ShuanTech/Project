<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['postId'])){
	$response=array();
	
		$sql1="update notify_access set vwed=1 where id='".$_POST['postId']."'";
		$res1=mysql_query($sql1);
		if($res1>0){
			$response['message']="updated";
			$response['success']=1;
			echo json_encode($response);
		}else{
			$response['message']="Not Updated";
			$response['success']=0;
			echo json_encode($response);
		}
	
	
	
}else{
	$response['message']="NO Data Post";
	$response['success']=0;
	echo json_encode($response);
}
?>