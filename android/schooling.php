<?php
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['level']) && isset($_POST['concent']) && isset($_POST['hName']) && isset($_POST['bName']) && 
	isset($_POST['cty']) && isset($_POST['frmDat']) && isset($_POST['toDat']) && 
	isset($_POST['agrt']) && isset($_POST['insrt']) && isset($_POST['type'])){
		
		if($_POST['type']=='add'){
			
			if($_POST['insrt']=='true'){
				$cIns="insert into institution(ins_name,board,location,status) values(
				'".$_POST['hName']."','".$_POST['bName']."','".$_POST['cty']."',2)";
				$res=mysql_query($cIns);
			}
			$ins="insert into education(u_id,level,concentration,ins_name,board,location,frm_date,to_date,aggregate) 
			values('".$_POST['u_id']."','".$_POST['level']."','".$_POST['concent']."','".$_POST['hName']."','".$_POST['bName']."',
			'".$_POST['cty']."','".$_POST['frmDat']."','".$_POST['toDat']."','".$_POST['agrt']."')";
			
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
			ins_name='".$_POST['hName']."',board='".$_POST['bName']."',location='".$_POST['cty']."',
			frm_date='".$_POST['frmDat']."',to_date='".$_POST['toDat']."',aggregate='".$_POST['agrt']."'
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

?>