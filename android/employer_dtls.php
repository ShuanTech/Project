<?php
require('config.php');

if(isset($_POST['u_id']) && isset($_POST['compname']) && isset($_POST['crno']) && isset($_POST['cdoorno']) && isset($_POST['location']) 
	&& isset($_POST['country']) && isset($_POST['state']) && isset($_POST['city'])&& isset($_POST['pin'])&& isset($_POST['contprsn'])&& 
	isset($_POST['contphn'])&& isset($_POST['contmail'])&& isset($_POST['availtime'])&& isset($_POST['cmpntype'])&& 
	isset($_POST['yearstrt'])&& isset($_POST['workers'])&& isset($_POST['descrp'])){
		
		
		$ins="insert into employer_info(u_id,cmpny_name,addr,landmark,country,state,city,
		pincode,contact_person,contact_ph,contact_mail,contact_time,c_type,year_of_establish,
		num_wrkers,c_desc,c_reg_no) values('".$_POST['u_id']."','".$_POST['compname']."',
		'".$_POST['cdoorno']."','".$_POST['location']."','".$_POST['country']."',
		'".$_POST['state']."','".$_POST['city']."','".$_POST['pin']."','".$_POST['contprsn']."',
		'".$_POST['contphn']."','".$_POST['contmail']."','".$_POST['availtime']."',
		'".$_POST['cmpntype']."','".$_POST['yearstrt']."','".$_POST['workers']."',
		'".$_POST['descrp']."','".$_POST['crno']."')";
		
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
	$response['msg']='No data post';
	$response['success']=0;
	echo json_encode($response);
	
} 




?>