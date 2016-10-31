<?php 
require('config.php');
if(isset($_POST['usr']) && isset($_POST['type'])){
	
	if($_POST['type']=='verify'){
		
		$slctCity=select_query("SELECT * FROM `login` WHERE '".$_POST['usr']."' in(`email_id`,`ph_no`)");
		$cnt=count($slctCity);
		$response['login']=array();
			if($cnt==""){
				$response["message"]="No Data";
				$response["success"]=0;
						
				echo json_encode($response);
			}else{
				for($i=0;$i<$cnt;$i++){
					array_push($response["login"],$slctCity[$i]);
				}
				$response['success']=1;
				echo json_encode($response); 
			} 
	}else{
		
		$sql="update login set passwrd='".$_POST['pass']."',c_pass='".$_POST['pass']."' where u_id='".$_POST['usr']."'";
			$res=mysql_query($sql);
			if($res>0){
				$response["message"]="success";
				$response["success"]=1;
				echo json_encode($response);
			}else{
				$response["message"]="Not success";
				$response["success"]=0;
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