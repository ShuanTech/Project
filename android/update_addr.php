<?php
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['door']) && isset($_POST['state']) && isset($_POST['city']) && isset($_POST['pincode'])){
	
	$uAddr="update usr_info set address='".$_POST['door']."',city='".$_POST['city']."',state='".$_POST['state']."',pincode='".$_POST['pincode']."' where u_id='".$_POST['u_id']."'";
	$res=mysql_query($uAddr);
	if($res>0){
		$response['message']="Address Update";
		$response['success']=1;
		echo json_encode($response);
	}else{
		$response['message']="NO Data Post";
		$response['success']=0;
		echo json_encode($response);
	}
}else{
	$response['message']="NO Data Post";
	$response['success']=0;
	echo json_encode($response);	
}
?>
