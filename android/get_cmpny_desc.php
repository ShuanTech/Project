<?php 
require('config.php');
if(isset($_POST['u_id'])){
	$response["abt"]=array();
	$sql=select_query("select c_desc from employer_info where u_id='".$_POST['u_id']."'");
		for($i=0;$i<count($sql);$i++){
				array_push($response["abt"],$sql[$i]);
			}
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