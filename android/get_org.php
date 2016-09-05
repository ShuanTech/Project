<?php 
require('config.php');
if(isset($_POST['id'])){
	$slctCity=select_query("select org_name,type,addr,land_mark,city,dis,state,country,pincode from organization");
	$cnt=count($slctCity);
	$response['org']=array();
				if($cnt==""){
					$response["message"]="No Data";
					$response["success"]=0;
					
					echo json_encode($response);
				}else{
					for($i=0;$i<$cnt;$i++){
						array_push($response["org"],$slctCity[$i]);
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