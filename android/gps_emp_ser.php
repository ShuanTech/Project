<?php
require('config.php');
if(isset($_POST['loc']) && isset($_POST['level'])){
	$response['emp']=array();
	
	if($_POST['level']=='jobs'){
		
		$getInfo=select_query("SELECT  e.cmpny_name,l.pro_pic,j.job_id,j.title,j.skill,
			j.level,j.location,j.date_created,j.viewed,j.shared,j.applied FROM employer_info e,login l,`job_post` j 
			WHERE e.u_id=l.u_id and j.u_id=l.u_id and j.location LIKE 
			'%".$_POST['loc']."%' and j.close=0");
			$count=count($getInfo);
			if($count==''){
				$response['msg']='No data found';
				$response['success']=0;
				echo json_encode($response);
			}else{

				for($i=0;$i<$count;$i++){
					array_push($response['emp'],$getInfo[$i]);
				}
				$response['success']=1;
				echo json_encode($response); 
			}
		
	}else{
		$getInfo=select_query("SELECT l.u_id,l.pro_pic,e.cmpny_name,l.level from login l,
			employer_info e where l.u_id=e.u_id and e.city='".$_POST['loc']."'");
			$count=count($getInfo);
			if($count==''){
				$response['msg']='No data found';
				$response['success']=0;
				echo json_encode($response);
			}else{

				for($i=0;$i<$count;$i++){
					array_push($response['emp'],$getInfo[$i]);
				}
				$response['success']=1;
				echo json_encode($response); 
			}
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