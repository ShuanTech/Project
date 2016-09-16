<?php 
require('config.php');
if(isset($_POST['jId']) && isset($_POST['title']) && isset($_POST['skill'])&& 
	isset($_POST['type']) && isset($_POST['cate']) && isset($_POST['sal']) && isset($_POST['loc']) 
	&& isset($_POST['level']) && isset($_POST['descr'])){
		
		
		$ins="update job_post set title='".$_POST['title']."',skill='".$_POST['skill']."',
			type='".$_POST['type']."',category='".$_POST['cate']."',package='".$_POST['sal']."',
			level='".$_POST['level']."',location='".$_POST['loc']."',
			description='".$_POST['descr']."' where job_id='".$_POST['jId']."'";
		
		$result=mysql_query($ins);
			if($result>0){		
				$response['msg']='Data Updated';
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



?>