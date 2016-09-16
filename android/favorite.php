<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['frm_id'])){
	$response=array();
	
		 $chk=select_query("select * from favorite where u_id='".$_POST['frm_id']."'
		and to_id='".$_POST['u_id']."'");
			$cnt=count($chk);
			
			if($cnt==''){ 
				$sql1="insert into favorite(u_id,to_id) values('".$_POST['frm_id']."',
					'".$_POST['u_id']."')";
				$res1=mysql_query($sql1);
				if($res1>0){
					$response['message']="updated";
					$response['success']=1;
					echo json_encode($response);
				}else{
					$response['message']="Not Updated";
					$response['success']=0;
					echo json_encode($response);
				}
	 
			}else{
				$response['message']="Added";
				$response['success']=2;
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