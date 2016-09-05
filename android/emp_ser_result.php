<?php 
require('config.php');
if(isset($_POST['ser']) && isset($_POST['type'])){
$response['result']=array();
	if($_POST['type']=='skill'){
		$sql=select_query("select l.u_id,l.pro_pic,l.full_name,l.level from login l,skill_tag s
			where l.u_id=s.u_id and s.skill='".$_POST['ser']."'");
				
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
			
	}else if($_POST['type']=='pos'){
		$sql=select_query("select l.u_id,l.pro_pic,l.full_name,l.level from login l,wrk_deatail w
			where l.u_id=w.u_id and w.position='".$_POST['ser']."'");
				
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
		$sql=select_query("SELECT l.u_id,l.pro_pic,l.full_name,l.level FROM login l,
					usr_info u WHERE l.u_id=u.u_id and u.city='".$_POST['ser']."'");
				
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