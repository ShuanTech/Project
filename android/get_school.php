<?php
require('config.php');
if(isset($_POST['id'])){
$slctIns=select_query("SELECT ins_name,board,location from institution where status=2");
	$cnt=count($slctIns);
	$response['school']=array();
	
				if($cnt==""){
					$response["message"]="No Data";
					$response["success"]=0;
					
					echo json_encode($response);
				}else{
					for($i=0;$i<$cnt;$i++){
						array_push($response['school'],$slctIns[$i]);
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