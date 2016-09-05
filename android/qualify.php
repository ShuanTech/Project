<?php
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['level']) && isset($_POST['concent']) && isset($_POST['clgName']) && 
	isset($_POST['univ']) && isset($_POST['loc']) && isset($_POST['frm']) && 
	isset($_POST['to']) && isset($_POST['agrt']) && isset($_POST['insrt']) && 
	isset($_POST['cInsrt']) && isset($_POST['type'])){
		
		
		if($_POST['type']=='add'){
			
			if($_POST['insrt']=='true'){
				$cIns="insert into institution(ins_name,board,location,status) values('".$_POST['clgName']."','".$_POST['univ']."','".$_POST['loc']."',1)";
				$res=mysql_query($cIns);
			}
			
			if($_POST['cInsrt']=='true'){
				$cInsrt="insert into course(course) values('".$_POST['concent']."')";
				$res=mysql_query($cInsrt);
			}
			
				$ins="insert into education(u_id,level,concentration,ins_name,board,location,frm_date,to_date,aggregate) 
			values('".$_POST['u_id']."','".$_POST['level']."','".$_POST['concent']."',
			'".$_POST['clgName']."','".$_POST['univ']."','".$_POST['loc']."','".$_POST['frm']."',
			'".$_POST['to']."','".$_POST['agrt']."')";
			
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
			$ins="update education set level='".$_POST['level']."',concentration='".$_POST['concent']."',
				ins_name='".$_POST['clgName']."',board='".$_POST['univ']."',location='".$_POST['loc']."',
				frm_date='".$_POST['frm']."',to_date='".$_POST['to']."',aggregate='".$_POST['agrt']."'
				where id='".$_POST['u_id']."'";
				
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