<?php 
require('config.php');
if(isset($_POST['jId'])){
	
		$save="update job_post set close=1 where job_id='".$_POST['jId']."'";
		$res=mysql_query($save);
		
		if($res>0){
			$response['msg']='Successfully Closed';
			$response['success']=1;
			echo json_encode($response);	
		}else{
			$response['msg']='Not Closed';
			$response['success']=0;
			echo json_encode($response);	
		}
}else{
	$response['msg']='No data post';
	$response['success']=0;
	echo json_encode($response);	
}
?>