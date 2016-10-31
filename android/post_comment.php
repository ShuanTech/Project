<?php 
require('config.php');

if(isset($_POST['u_id']) && isset($_POST['j_id']) && isset($_POST['cmts'])){
	$response=array();
	
	$sql="insert into comments(u_id,j_id,commnts,cmt_date) values('".$_POST['u_id']."',
	'".$_POST['j_id']."','".$_POST['cmts']."',now())";
	$res=mysql_query($sql);
	if($res>0){
		
		$getName=select_query("select full_name from usr_info where u_id='".$_POST['u_id']."'");
		if(count($getName)!=0){
			$cnt=$getName[0]['full_name'].' commended on your post';
		$getCId=select_query("select u_id from job_post where job_id='".$_POST['j_id']."'");	
		$notify=mysql_query("insert into notify_access(frm_id,to_id,post_id,content,type) values('".$_POST['u_id']."',
					'".$getCId[0]['u_id']."','".$_POST['j_id']."','".$cnt."',6)");
		}
		
		$response['msg']='Successfully Posted';
		$response['success']=1;
		echo json_encode($response);
	}else{
		$response['msg']='Not Posted';
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