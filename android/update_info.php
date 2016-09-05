<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['name']) && isset($_POST['dob']) && isset($_POST['gender'])
	&& isset($_POST['rel']) && isset($_POST['bld']) && isset($_POST['address']) && isset($_POST['city'])
	&& isset($_POST['district']) && isset($_POST['state']) && isset($_POST['country']) 
	&& isset($_POST['pincode'])){
		
		$chk=select_query("select * from usr_info where u_id='".$_POST['u_id']."'");
		$cnt=count($chk);
		
		if($cnt==''){
			$ins="insert into usr_info(u_id,full_name,dob,gender,address,city,district,state,country,
			pincode,blood_grp,married_status) values('".$_POST['u_id']."','".$_POST['name']."',
			'".$_POST['dob']."','".$_POST['gender']."','".$_POST['address']."','".$_POST['city']."',
			'".$_POST['district']."','".$_POST['state']."','".$_POST['country']."','".$_POST['pincode']."',
			'".$_POST['bld']."','".$_POST['rel']."')";
			$res=mysql_query($ins);
			if($res>0){
				$response['message']="NO Data Post";
				$response['success']=0;
				echo json_encode($response);
			}else{
				$response['message']="Inserted";
				$response['success']=1;
				echo json_encode($response);
			}
		}else{
			$upt="update usr_info set full_name='".$_POST['name']."',dob='".$_POST['dob']."',
			gender='".$_POST['gender']."',address='".$_POST['address']."',city='".$_POST['city']."',
			district='".$_POST['district']."',state='".$_POST['state']."',country='".$_POST['country']."',
			pincode='".$_POST['pincode']."',blood_grp='".$_POST['bld']."',married_status='".$_POST['rel']."'
			where u_id='".$_POST['u_id']."'";
			
			$res=mysql_query($ins);
			if($res>0){
				$response['message']="NO Data Post";
				$response['success']=0;
				echo json_encode($response);
			}else{
				$response['message']="Inserted";
				$response['success']=1;
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