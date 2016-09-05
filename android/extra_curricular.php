<?php
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['extra'])){
	
	$ins="insert into extra_curricular(u_id,extra) values('".$_POST['u_id']."','".$_POST['extra']."')";
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