<?php 
require('config.php');
if(isset($_POST['ser']) && isset($_POST['type'])){
$response['result']=array();

	if($_POST['type']=='skill'){
		$sql=select_query("SELECT  e.cmpny_name,l.pro_pic,j.job_id,j.title,j.skill,j.level,
			j.location,j.date_created,j.viewed,j.shared,j.applied FROM employer_info e,login l,`job_post` j WHERE 
			e.u_id=l.u_id and j.u_id=l.u_id  and j.skill LIKE '%".$_POST['ser']."%' and j.close=0");
				
				$cnt=count($sql);
				if($cnt==""){
					$response["message"]="No Data";
					$response["success"]=0;
					echo json_encode($response);
				}else{
					for($i=0;$i<$cnt;$i++){
						array_push($response['result'],$sql[$i]);
					}
					
					$response['success']=1;
					echo json_encode($response); 
				} 
			
	}else if($_POST['type']=='job'){
		$sql=select_query("SELECT  e.cmpny_name,l.pro_pic,j.job_id,j.title,j.skill,j.level,
			j.location,j.date_created,j.viewed,j.shared,j.applied FROM employer_info e,login l,`job_post` j WHERE 
			e.u_id=l.u_id and j.u_id=l.u_id  and j.title='".$_POST['ser']."' and j.close=0");
				
				$cnt=count($sql);
				if($cnt==""){
					$response["message"]="No Data";
					$response["success"]=0;
					echo json_encode($response);
				}else{
					for($i=0;$i<$cnt;$i++){
						array_push($response['result'],$sql[$i]);
					}
					
					$response['success']=1;
					echo json_encode($response); 
				} 
	}else{
		$sql=select_query("SELECT  e.cmpny_name,l.pro_pic,j.job_id,j.title,j.skill,j.level,
			j.location,j.date_created,j.viewed,j.shared,j.applied FROM employer_info e,login l,`job_post` j WHERE 
			e.u_id=l.u_id and j.u_id=l.u_id  and j.location LIKE '%".$_POST['ser']."%' and 
			j.close=0");
				
				$cnt=count($sql);
				if($cnt==""){
					$response["message"]="No Data";
					$response["success"]=0;
					echo json_encode($response);
				}else{
					for($i=0;$i<$cnt;$i++){
						array_push($response['result'],$sql[$i]);
					}
					
					$response['success']=1;
					echo json_encode($response); 
				} 
	}

	
 }else{
	$response['message']="NO Data Post";
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