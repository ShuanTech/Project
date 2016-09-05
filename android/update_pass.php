<?php 

require('config.php');
if(isset($_POST['u_id']) && isset($_POST['pass']) && isset($_POST['cfrm'])){
	$response=array();
	
	$upt="update login set passwrd='".$_POST['pass']."',c_pass='".$_POST['cfrm']."' 
		where u_id='".$_POST['u_id']."'";
		$res=mysql_query($upt);
		if($res>0){
			$response['message']="Success";
			$response['success']=1;
			echo json_encode($response);
		}else{
			$response['message']="Not Update";
			$response['success']=0;
			echo json_encode($response);
		}
}else{
	$response['message']="NO Data Post";
	$response['success']=0;
	echo json_encode($response);
}
?>