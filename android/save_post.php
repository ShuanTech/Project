<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['j_id'])){
	
		$save="update share_post set is_important=1 where to_id='".$_POST['u_id']."' and
			post_id	='".$_POST['j_id']."'";
		$res=mysql_query($save);
		
		if($res>0){
			$response['msg']='Successfully Saved';
			$response['success']=1;
			echo json_encode($response);	
		}else{
			$response['msg']='Not Saved';
			$response['success']=0;
			echo json_encode($response);	
		}
}else{
	$response['msg']='No data post';
	$response['success']=0;
	echo json_encode($response);	
}
?>