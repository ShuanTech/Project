<?php
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['a_name'])){
	
	$ins="insert into achivmnt(u_id,a_name) values('".$_POST['u_id']."','".$_POST['a_name']."')";
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
	$response['msg']='No data post';
	$response['success']=0;
	echo json_encode($response);
}

?>