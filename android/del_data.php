<?php 
require('config.php');
$response=array();
if(isset($_POST['p_id']) && isset($_POST['table'])){
	
	if($_POST['table']=='proSum'){
		$sql="delete from profile_summary where id='".$_POST['p_id']."'";
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
	}else if($_POST['table']=='wrkDet'){
		$sql="delete from wrk_deatail where id='".$_POST['p_id']."'";
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
		$sql="delete from wrk_experience where id='".$_POST['p_id']."'";
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
	}else if($_POST['table']=='edu'){
		$sql="delete from education where id='".$_POST['p_id']."'";
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
	}else if($_POST['table']=='skill'){
		$sql="update skill_tag set del=1 where id='".$_POST['p_id']."'";
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
	}else if($_POST['table']=='prjct'){
		$sql="delete from project_detail where id='".$_POST['p_id']."'";
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
	}else if($_POST['table']=='cert'){
		$sql="delete from ceritification where id='".$_POST['p_id']."'";
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
	}else if($_POST['table']=='ach'){
		$sql="delete from achivmnt where id='".$_POST['p_id']."'";
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
	}else if($_POST['table']=='extra'){
		$sql="delete from extra_curricular where id='".$_POST['p_id']."'";
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