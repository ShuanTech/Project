<?php 
require('config.php');

if(isset($_POST['u_id']) && isset($_POST['contprsn'])&& isset($_POST['contphn'])&& 
	isset($_POST['contmail'])&& isset($_POST['availtime'])){
		
		
		$chk=select_query("select * from employer_info where u_id='".$_POST['u_id']."'");
		
		$cnt=count($chk);
		
		if($cnt==''){
			$ins="insert into employer_info(u_id,contact_person,contact_mail,contact_ph,
			contact_time) values('".$_POST['u_id']."','".$_POST['contprsn']."','".$_POST['contmail']."',
			'".$_POST['contphn']."','".$_POST['availtime']."')";
			
			$result=mysql_query($ins);
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
			
			$ins="update employer_info set contact_person='".$_POST['contprsn']."',
			contact_mail='".$_POST['contmail']."',contact_ph='".$_POST['contphn']."',
			contact_time='".$_POST['availtime']."' where u_id='".$_POST['u_id']."'";
			$result=mysql_query($ins);
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