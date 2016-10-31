<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['type'])){
	
	if($_POST['type']=='all'){
		$sql=select_query("SELECT  e.cmpny_name,l.pro_pic,f.pro_pic as fp,j.job_id,j.title,j.skill,j.level,
	j.location,j.date_created,j.viewed,j.shared,j.applied,s.frm_id,s.is_important,s.shred,s.lvl FROM employer_info e,login l,`job_post` j, share_post s,login f 
	WHERE e.u_id=l.u_id and f.u_id=s.frm_id and j.u_id=l.u_id and j.job_id=s.post_id and s.to_id='".$_POST['u_id']."'
	and s.status in(0,1) and j.close=0 order by s.id desc");
	
	$count=count($sql);
	$response['post']=array();
		if($count==""){
			$response["message"]="No Data";
			$response["success"]=0;
			echo json_encode($response);
		}else{
			for($i=0;$i<$count;$i++){
				array_push($response["post"],$sql[$i]);
			}
			$response['success']=1;
			echo json_encode($response); 
		} 
	}else if($_POST['type']=='imp'){
		$sql=select_query("SELECT  e.cmpny_name,l.pro_pic,f.pro_pic as fp,j.job_id,j.title,j.skill,j.level,
	j.location,j.date_created,j.viewed,j.shared,j.applied,s.frm_id,s.is_important,s.shred,s.lvl FROM employer_info e,login l,`job_post` j, share_post s,login f 
	WHERE e.u_id=l.u_id and f.u_id=s.frm_id and j.u_id=l.u_id and j.job_id=s.post_id and s.to_id='".$_POST['u_id']."'
	and s.status in(0,1) and s.is_important=1 and j.close=0 order by s.id desc");
	
	$count=count($sql);
	$response['post']=array();
		if($count==""){
			$response["message"]="No Data";
			$response["success"]=0;
			echo json_encode($response);
		}else{
			for($i=0;$i<$count;$i++){
				array_push($response["post"],$sql[$i]);
			}
			$response['success']=1;
			echo json_encode($response); 
		} 
	}else{
		$sql=select_query("SELECT  e.cmpny_name,l.pro_pic,f.pro_pic as fp,j.job_id,j.title,j.skill,j.level,
	j.location,j.date_created,j.viewed,j.shared,j.applied,s.frm_id,s.is_important,s.shred,s.lvl FROM employer_info e,login l,`job_post` j, share_post s,login f 
	WHERE e.u_id=l.u_id and f.u_id=s.frm_id and j.u_id=l.u_id and j.job_id=s.post_id and s.to_id='".$_POST['u_id']."'
	and s.status in(2) and j.close=0 order by s.id desc");
	
	$count=count($sql);
	$response['post']=array();
		if($count==""){
			$response["message"]="No Data";
			$response["success"]=0;
			echo json_encode($response);
		}else{
			for($i=0;$i<$count;$i++){
				array_push($response["post"],$sql[$i]);
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