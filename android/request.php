<?php 
require('config.php');
if(isset($_POST['frm_id']) && isset($_POST['to_id']) && isset($_POST['level'])){
	
	if($_POST['level']=='connect'){
		$sql="insert into connection(u_id,connect) values('".$_POST['frm_id']."',
		'".$_POST['to_id']."')";
		$res=mysql_query($sql);
		if($res>0){
			$sql1="insert into connection(u_id,connect) values('".$_POST['to_id']."',
				'".$_POST['frm_id']."')";
			$res1=mysql_query($sql1);
			if($res1>0){
				$response['message']="Connect Success";
				$response['success']=1;
				echo json_encode($response);

			}else{
				$response['message']="NO Data Post";
				$response['success']=0;
				echo json_encode($response);
			}
		}else{
			$response['message']="NO Data Post";
			$response['success']=0;
			echo json_encode($response);

		}
		
		
	}else{
		$sql="insert into following(u_id,following) values('".$_POST['frm_id']."',
		'".$_POST['to_id']."')";
		$sql1="insert into follower(u_id,follower) values('".$_POST['to_id']."',
		'".$_POST['frm_id']."')";
		$res=mysql_query($sql);
		$res1=mysql_query($sql1);
		if($res1>0){
			$response['message']="Following Success";
			$response['success']=1;
			echo json_encode($response);
		}else{
			$response['message']="NO Data Post";
			$response['success']=0;
			echo json_encode($response);
		}
	}
	
}else{
	$response['message']="NO Data Post";
	$response['success']=0;
	echo json_encode($response);
}

?>