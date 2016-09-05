<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['j_id'])){
	$res;
	$res1;
	$num=0;
	$sql=select_query("select follower from follower where u_id='".$_POST['u_id']."'");
	for($i=0;$i<count($sql);$i++){
		$get=implode("",$sql[$i]);
		$share="insert into share_post(frm_id,to_id,post_id,status) values(
					'".$_POST['u_id']."','".$get."','".$_POST['j_id']."',0)";
		$res=mysql_query($share);
		if($res>0){
			$upt="update job_post set shared=shared+1 where job_id='".$_POST['j_id']."'";
			$res1=mysql_query($upt);
		}
	}
	
	if($res1>0){
		$response['message']="Shred Successfully";
		$response['success']=1;
		echo json_encode($response);
	}else{
		$response['message']="Not Shred Successfully";
		$response['success']=0;
		echo json_encode($response);
	}
	
	
}else{
	$response['msg']='No data post';
	$response['success']=0;
	echo json_encode($response);	
}

function select_query($qry){
	
	try{
		require('config.php');
		
		$parse_qry=mysql_query($qry,$con);
		
		if(!$parse_qry){
			 die('Could not get data: ' . mysql_error());
		} 
		$res_qry=array();
		while(($row = mysql_fetch_array($parse_qry,MYSQL_ASSOC))!=false){
			$res_qry[]=$row;
		}
		return $res_qry;
		mysql_close($con);
	}catch(Exception $e){}
	
	
}
?>