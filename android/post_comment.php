<?php 
require('config.php');

if(isset($_POST['u_id']) && isset($_POST['j_id']) && isset($_POST['cmts'])){
	$response=array();
	
	$sql="insert into comments(u_id,j_id,commnts,cmt_date) values('".$_POST['u_id']."',
	'".$_POST['j_id']."','".$_POST['cmts']."',now())";
	$res=mysql_query($sql);
	if($res>0){
		$response['msg']='Successfully Posted';
		$response['success']=1;
		echo json_encode($response);
	}else{
		$response['msg']='Not Posted';
		$response['success']=0;
		echo json_encode($response);
	}
	
}else{
	$response['msg']='No data post';
	$response['success']=0;
	echo json_encode($response);
}

?>