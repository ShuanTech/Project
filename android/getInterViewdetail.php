<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['j_id'])){
	$response['interview']=array();
	$sql=select_query("SELECT e.cmpny_name,l.pro_pic,j.title,i.* FROM 
	`intrvew_panel` i, job_post j, employer_info e,login l WHERE i.job_id=j.job_id and 
	j.u_id=e.u_id and j.u_id=l.u_id and i.usr_id='".$_POST['u_id']."'");
	$count=count($sql);
	if($count==""){
			$response["message"]="No Data";
			$response["success"]=0;
			echo json_encode($response);
		}else{
			for($i=0;$i<$count;$i++){
				array_push($response["interview"],$sql[$i]);
			}
			$response['success']=1;
			echo json_encode($response); 
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