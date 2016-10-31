<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['stus'])){
	$sql="update login set status='".$_POST['stus']."' where u_id='".$_POST['u_id']."'";
	$res=mysql_query($sql);
	if($res>0){
		$response['msg']='Success';
		$response['success']=1;
		echo json_encode($response);
	}else{
		$response['msg']='Not Inserted';
		$response['success']=0;
		echo json_encode($response);
	}
}else{
	$response['msg']='No data post';
	$response['success']=0;
	echo json_encode($response);
}