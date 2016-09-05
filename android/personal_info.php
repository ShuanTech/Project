<?php
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['dob']) && isset($_POST['gender']) && 
	isset($_POST['address']) && isset($_POST['city']) && isset($_POST['district']) && 
	isset($_POST['state']) && isset($_POST['country']) && isset($_POST['pincode']) &&
	isset($_POST['father_name']) && isset($_POST['mother_name']) && isset($_POST['insrt'])){
	
	if($_POST['insrt']=='true'){
		$insLoc="insert into location(city,district,state,country) values('".$_POST['city']."',
		'".$_POST['district']."','".$_POST['state']."','".$_POST['country']."')";
		$res=mysql_query($insLoc);
	}
	
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
		
		$ins="INSERT INTO `usr_info`(`u_id`,`full_name`,`dob`,`gender`,`address`,`city`,`district`,
		`state`,`country`,`pincode`,`email_id`,`ph_no`,`father_name`,`mother_name`) VALUES (
		'".$_POST['u_id']."','".$name."','".$_POST['dob']."','".$_POST['gender']."',
		'".$_POST['address']."','".$_POST['city']."','".$_POST['district']."','".$_POST['state']."',
		'".$_POST['country']."','".$_POST['pincode']."','".$mail."','".$phno."',
		'".$_POST['father_name']."','".$_POST['mother_name']."')";
			
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
	}else{
		
		$get="select full_name,email_id,ph_no from login where u_id='".$_POST['u_id']."'";
		$res=mysql_query($get);
		if($row=mysql_fetch_array($res)){
			$name=$row[0];
			$mail=$row[1];
			$phno=$row[2];
		}
		$ins1="update usr_info set full_name='".$name."',dob='".$_POST['dob']."',
		gender='".$_POST['gender']."',address='".$_POST['address']."',city='".$_POST['city']."',
		state='".$_POST['state']."',country='".$_POST['country']."',pincode='".$_POST['pincode']."',
		email_id='".$mail."',ph_no='".$phno."',father_name='".$_POST['father_name']."',
		mother_name='".$_POST['mother_name']."' where u_id='".$_POST['u_id']."'";
		
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