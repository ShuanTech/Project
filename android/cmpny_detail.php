<?php 
require('config.php');

if(isset($_POST['u_id']) && isset($_POST['compname']) && isset($_POST['compnytype']) && 
	isset($_POST['cdoorno']) && isset($_POST['location']) && isset($_POST['country']) && 
	isset($_POST['state']) && isset($_POST['city'])&& isset($_POST['pin']) && 
	isset($_POST['insrt'])){
		
		if($_POST['insrt']=='true'){
			$ins="insert into organization(org_name,type,addr,land_mark,city,state,country,
			pincode) values('".$_POST['compname']."','".$_POST['compnytype']."','".$_POST['cdoorno']."',
			'".$_POST['location']."','".$_POST['city']."','".$_POST['state']."',
			'".$_POST['country']."','".$_POST['pin']."')";
			$res=mysql_query($ins);
		}
		
		$chk=select_query("select * from employer_info where u_id='".$_POST['u_id']."'");
		
		$cnt=count($chk);
		if($cnt==''){
			$org="insert into employer_info(u_id,cmpny_name,c_type,addr,landmark,city,state,country,
			pincode) values('".$_POST['u_id']."','".$_POST['compname']."','".$_POST['compnytype']."',
			'".$_POST['cdoorno']."','".$_POST['location']."','".$_POST['city']."','".$_POST['state']."',
			'".$_POST['country']."','".$_POST['pin']."')";
			
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
		}else{
			
			$org="update employer_info set cmpny_name='".$_POST['compname']."',c_type='".$_POST['compnytype']."',
			addr='".$_POST['cdoorno']."',landmark='".$_POST['location']."',city='".$_POST['city']."',
			state='".$_POST['state']."',country='".$_POST['country']."',pincode='".$_POST['pin']."' 
			where u_id='".$_POST['u_id']."'";
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
			
		}
		
		
		
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