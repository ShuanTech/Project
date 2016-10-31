<?php 
require('config.php');
 if(isset($_POST['u_id'])){ 
	
$response["notify"]=array();
	$slct=select_query("select * from notify_access where to_id='U1609031120' order by id desc");
	$cnt1=count($slct);
						if($cnt1==0){
							$response["message"]="No Data";
							$response["success"]=0;
							echo json_encode($response);
						}else{
							for($i=0;$i<$cnt1;$i++){
								array_push($response["notify"],$slct[$i]);
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