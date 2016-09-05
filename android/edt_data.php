<?php 
require('config.php');
$response=array();
if(isset($_POST['p_id']) && isset($_POST['data']) && isset($_POST['table'])){
	
	if($_POST['table']=='proSum'){
		$sql="update profile_summary set summary='".$_POST['data']."' where id='".$_POST['p_id']."'";
		$res=mysql_query($sql);
		if($res>0){
			$response['message']='Successfully Deleted';
			$response['success']=1;
			echo json_encode($response);
		}else{
			$response['message']='Not Deleted';
			$response['success']=0;
			echo json_encode($response);
		}
	}else if($_POST['table']=='wrkExp'){
		$sql="update wrk_experience set wrk_exp='".$_POST['data']."' where id='".$_POST['p_id']."'";
		$res=mysql_query($sql);
		if($res>0){
			$response['message']='Successfully Deleted';
			$response['success']=1;
			echo json_encode($response);
		}else{
			$response['message']='Not Deleted';
			$response['success']=0;
			echo json_encode($response);
		}
	}
	
}else{
	$response['message']='No Data POST';
	$response['success']=0;
	echo json_encode($response);
}

?>