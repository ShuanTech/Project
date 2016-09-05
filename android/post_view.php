<?php 
require('config.php');
if(isset($_POST['jId']) && isset($_POST['u_id'])){
	$sql=select_query("SELECT l.pro_pic,l.cover_pic,e.cmpny_name,e.c_website, j.* FROM 
		login l,employer_info e, job_post j WHERE j.u_id=e.u_id and j.u_id=l.u_id and 
		j.job_id='".$_POST['jId']."'");
		
		$response['post']=array();
		for($i=0;$i<count($sql);$i++){
			array_push($response["post"],$sql[$i]);
		}
		
		$upt="update share_post set status=1 where to_id='".$_POST['u_id']."' and status!=2";
		$res=mysql_query($upt);
		
		$response['success']=1;
		echo json_encode($response); 
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