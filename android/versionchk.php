<?php
$response=array();
if(isset($_POST['ver'])){

	$version='2.1.3';						
							if($version>$_POST['ver']){
								$response["message"]="greater";
								$response["success"]=1;
								
								echo json_encode($response);
							}else{
								$response["message"]="less";
								$response["success"]=0;
								
								echo json_encode($response);
							}
}else{
	$response['message']="No Data to Post";
	$response['success']=0;
	echo json_encode($response);
}
/*if(isset($_POST['usrName']) && isset($_POST['pass']))
//{
	$usr='linda';    //$_POST['usrName'];
	$pass='12345';    //$_POST['pass'];
	
	if($con){
		
		$login=select_query("select emp_id,uname,password from login where uname='".$usr."' and password='".$pass."' and status=1");
		
		$count=count($login);
		
		if($count==''){
			$response['message']="success";
			$response['success']=1;
			echo json_encode($response);
		}else{
			$response['message']="No Rows Selected";
			$response['success']=0;
			echo json_encode($response);
			
			
			/*$sql=select_query("select name,propic from emp_profile where uname='".$usr."'");
			$count=count($sql);
			$response["login"]=array();
			
					
					for($i=0;$i<$count;$i++){
						//$nattuvartha=$select_nattu_vartha[$i];
						array_push($response["login"],$sql[$i]);
					}
					$response['success']=1;
					echo json_encode($response);
				
			
			
		}
		
	}else{
		$response['message']="DB Not Connected";
		$response['success']=0;
		echo json_encode($response);
	}
		
/*}else{
	$response['message']="No Data to Post";
	$response['success']=0;
	echo json_encode($response);
}*/


function select_query($qry){
	
	try{
		require('ma_config.php');
		
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