<?php 
require('config.php');
 if(isset($_POST['c_name'])){
	$slctId=mysql_query("select id from country where c_name='".$_POST['c_name']."'");
	$row=mysql_fetch_array($slctId);
	$slctState=select_query("select id,s_name from state where c_id='".$row[0]."'");
	$cnt=count($slctState);
	$response['state']=array();
				if($cnt==""){
					$response["message"]="No Data";
					$response["success"]=0;
					
					echo json_encode($response);
				}else{
					
					for($i=0;$i<$cnt;$i++){
						//$nattuvartha=$select_nattu_vartha[$i];
						array_push($response["state"],$slctState[$i]);
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