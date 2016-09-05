<?php 
require('config.php');
if(isset($_POST['a_id']) && isset($_POST['txt'])){
	$response=array();
	$sql="update job_applied set shrt=2,commnts='".$_POST['txt']."' where applied_usr='".$_POST['a_id']."'";
	$res=mysql_query($sql);
	$sql1="update notify_access set vwed=1 where frm_id='".$_POST['a_id']."'";
	$nres=mysql_query($sql1);
	
	if($res>0){
		$response['message']='successfully updated';
		$response['success']=1;
		echo json_encode($response);
	}else{
		$response['message']='Not successfully updated';
		$response['success']=0;
		echo json_encode($response);
	}
}else{
	$response['message']='No Data Post';
	$response['success']=0;
	echo json_encode($response);
}

?>