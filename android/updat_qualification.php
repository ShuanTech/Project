<?php
require('config.php');
if(isset($_POST['uId']) && isset($_POST['qualify']) && isset($_POST['clgName']) && isset($_POST['degree']) && isset($_POST['course']) && 
	isset($_POST['join']) && isset($_POST['end']) && isset($_POST['percent']) && isset($_POST['status'])){
		
		$ins="insert into education(u_id,qualification,instname,degree,course,yjoin,yend,percent,status) 
		values('".$_POST['uId']."','".$_POST['qualify']."','".$_POST['clgName']."','".$_POST['degree']."','".$_POST['course']."','".$_POST['join']."',
		'".$_POST['end']."','".$_POST['percent']."','".$_POST['status']."')";
		
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
