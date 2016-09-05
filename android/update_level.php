<?php 
require('config.php');
if(isset($_POST['u_id'])){
	
	$sql="update login set level=2 where u_id='".$_POST['u_id']."'";
	$res=mysql_query($sql);
	if($res>0){
		$response['msg']='Updated';
		$response['success']=1;
		echo json_encode($response);
	}else{
		$response['msg']='Not Updated';
		$response['success']=0;
		echo json_encode($response);
	}
	
}else{
	$response['msg']='No data post';
	$response['success']=0;
	echo json_encode($response);
}
?>