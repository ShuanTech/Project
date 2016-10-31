<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['dob']) && isset($_POST['gender']) && 
	isset($_POST['father_name']) && isset($_POST['mother_name']) && isset($_POST['rel'])
	&& isset($_POST['lang']) && isset($_POST['hobby']) && isset($_POST['age'])){

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
		
		$ins="INSERT INTO `usr_info`(`u_id`,`full_name`,`dob`,`gender`,`email_id`,`ph_no`,
		`father_name`,`mother_name`,married_status,language,hobbies,age) VALUES (
		'".$_POST['u_id']."','".$name."','".$_POST['dob']."','".$_POST['gender']."',
		'".$mail."','".$phno."','".$_POST['father_name']."','".$_POST['mother_name']."',
		'".$_POST['rel']."','".$_POST['lang']."','".$_POST['hobby']."','".$_POST['age']."')";
			
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
		gender='".$_POST['gender']."',email_id='".$mail."',ph_no='".$phno."',
		father_name='".$_POST['father_name']."',mother_name='".$_POST['mother_name']."',
		married_status='".$_POST['rel']."',language='".$_POST['lang']."',
		hobbies='".$_POST['hobby']."',age='".$_POST['age']."' where u_id='".$_POST['u_id']."'";
		
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