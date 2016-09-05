<?php
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['skill']) && isset($_POST['area']) && isset($_POST['devEnv']) && isset($_POST['other'])){
	
	$skill=array_map('trim',explode(',', $_POST['skill']));
	
	for($i=0;$i<count($skill);$i++){
		$inss="insert into skill_tag(u_id,skill) values('".$_POST['u_id']."','".$skill[$i]."')";
		$ress=mysql_query($inss);
	}
	
	
	
	$ins="insert into skill(u_id,lang_known,dev_envrnmnt,area_interest,others) values('".$_POST['u_id']."','".$_POST['skill']."','".$_POST['devEnv']."','".$_POST['area']."','".$_POST['other']."')";
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