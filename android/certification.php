<?php
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['cer_name']) && isset($_POST['cer_centre']) && 
	isset($_POST['cer_dur']) && isset($_POST['type'])){
	
	if($_POST['type']=='add'){
		$ins="insert into ceritification(u_id,cer_name,cer_centre,cer_dur) 
		values('".$_POST['u_id']."','".$_POST['cer_name']."','".$_POST['cer_centre']."','".$_POST['cer_dur']."')";
	
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
		$ins="update ceritification set cer_name='".$_POST['cer_name']."',
		cer_centre='".$_POST['cer_centre']."',cer_dur='".$_POST['cer_dur']."' 
		where id='".$_POST['u_id']."'";
		
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
	}
	
	
		
	
}else{
	$response['msg']='No data post';
	$response['success']=0;
	echo json_encode($response);
}

?>