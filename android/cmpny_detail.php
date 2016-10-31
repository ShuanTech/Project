<?php 
require('config.php');

if(isset($_POST['u_id']) && isset($_POST['compname']) && isset($_POST['compnytype']) && isset($_POST['indusType']) &&
	isset($_POST['country']) && isset($_POST['state']) && isset($_POST['dis']) && isset($_POST['city']) && 
	isset($_POST['insrt'])){
		
		//$selDis=select_query("SELECT district FROM `location` WHERE city='".$_POST['city']."'");
		
		 if($_POST['insrt']=='true'){
			$insLoc="insert into location(city,district,state,country) values('".$_POST['city']."',
				'".$_POST['dis']."','".$_POST['state']."','".$_POST['country']."')";
			$res=mysql_query($insLoc);
		} 
		
		//$chk=select_query("select * from employer_info where u_id='".$_POST['u_id']."'");
		
		//$cnt=count($chk);
		//if($cnt==''){
			$org="insert into employer_info(u_id,cmpny_name,c_type,i_type,city,district,state,country)
			values('".$_POST['u_id']."','".$_POST['compname']."','".$_POST['compnytype']."',
			'".$_POST['indusType']."','".$_POST['city']."','".$_POST['dis']."','".$_POST['state']."',
			'".$_POST['country']."')";
			
			$result=mysql_query($org);
			if($result>0){
				$response['msg']='Data Inserted';
				$response['success']=1;
				echo json_encode($response);
			}else{
				$response['msg']='Not Inserted';
				$response['success']=0;
				echo json_encode($response);
			}
		//}else{
			
		//	$org="update employer_info set cmpny_name='".$_POST['compname']."',c_type='".$_POST['compnytype']."',
		//	addr='".$_POST['cdoorno']."',landmark='".$_POST['location']."',city='".$_POST['city']."',
		//	state='".$_POST['state']."',country='".$_POST['country']."',pincode='".$_POST['pin']."' 
		//	where u_id='".$_POST['u_id']."'";
		//	$result=mysql_query($org);
		//	if($result>0){
		//		$response['msg']='Data Inserted';
		//		$response['success']=1;
			//	echo json_encode($response);
		//	}else{
		//		$response['msg']='Not Inserted';
			//	$response['success']=0;
			//	echo json_encode($response);
			//}
			
		//}
		
		
		
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