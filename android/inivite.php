<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['frm_id'])){
	$response=array();
	
		$get="select cmpny_name from employer_info where u_id='".$_POST['frm_id']."'";
			$res=mysql_query($get);
			$row=mysql_fetch_row($res);
		$content=$row[0].' : Send the Invitation for Job Openings.';
		
		$sql1="insert into notify_access(frm_id,to_id,content,type) values('".$_POST['frm_id']."',
			'".$_POST['u_id']."','".$content."',4)";
		$res1=mysql_query($sql1);
		if($res1>0){
			$response['message']="updated";
			$response['success']=1;
			echo json_encode($response);
		}else{
			$response['message']="Not Updated";
			$response['success']=0;
			echo json_encode($response);
		}
	
	
	
}else{
	$response['message']="NO Data Post";
	$response['success']=0;
	echo json_encode($response);
}
?>