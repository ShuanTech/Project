<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['address']) && isset($_POST['city']) && 
	isset($_POST['district']) && isset($_POST['state']) && isset($_POST['country']) && 
	isset($_POST['pin']) && isset($_POST['insrt'])){
		
	
	$sql=select_query("select * from usr_info where u_id='".$_POST['u_id']."'");
	$count=count($sql);
	if($count==''){
		$get="select full_name,email_id,ph_no from login where u_id='".$_POST['u_id']."'";
		$res=mysql_query($get);
		if($row=mysql_fetch_array($res)){
			$name=$row[0];
			$mail=$row[1];
			$phno=$row[2];
		}
		
		$ins="INSERT INTO usr_info(u_id,full_name,address,city,district,state,country,
		email_id,ph_no,pincode) VALUES ('".$_POST['u_id']."','".$name."','".$_POST['address']."',
		'".$_POST['city']."','".$_POST['district']."','".$_POST['state']."','".$_POST['country']."',
		'".$mail."','".$phno."','".$_POST['pin']."')";
			
			$result=mysql_query($ins);
			if($result>0){
				$response['msg']='Data Inserted';
				$response['success']=1;
				echo json_encode($response);
			}else{
				$response['msg']='Data Not Inserted';
				$response['success']=0;
				echo json_encode($response);
			}
			
		if($_POST['insrt']=='true'){
			$insLoc="insert into location(city,district,state,country) values('".$_POST['city']."',
			'".$_POST['district']."','".$_POST['state']."','".$_POST['country']."')";
			$res=mysql_query($insLoc);
		}
	}else{
		
		$ins1="update usr_info set address='".$_POST['address']."',
		city='".$_POST['city']."',state='".$_POST['state']."',country='".$_POST['country']."',
		pincode='".$_POST['pin']."' where u_id='".$_POST['u_id']."'";
		
		$result1=mysql_query($ins1);
			if($result1>0){
				$response['msg']='Data Inserted';
				$response['success']=1;
				echo json_encode($response);
			}else{
				$response['msg']='Data Not Inserted';
				$response['success']=0;
				echo json_encode($response);
			}
		if($_POST['insrt']=='true'){
			$insLoc="insert into location(city,district,state,country) values('".$_POST['city']."',
			'".$_POST['district']."','".$_POST['state']."','".$_POST['country']."')";
			$res=mysql_query($insLoc);
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