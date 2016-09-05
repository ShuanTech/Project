<?php
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['summary']) && isset($_POST['table'])){
	
	if($_POST['table']=='proSum'){
		$ins="insert into profile_summary(u_id,summary) values('".$_POST['u_id']."','".$_POST['summary']."')";
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
	}else if($_POST['table']=='wrkExp'){
		$ins="insert into wrk_experience(u_id,wrk_exp) values('".$_POST['u_id']."','".$_POST['summary']."')";
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
		
	}else if($_POST['table']=='skill'){
		$chk=select_query("select skill from skill_tag where skill='".$_POST['summary']."' and 
		u_id='".$_POST['u_id']."'");
		$res=count($chk);
		if($res!=''){
			$upt="update skill_tag set del=0 where skill='".$_POST['summary']."' and
			u_id='".$_POST['u_id']."'";
			$result=mysql_query($upt);
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
			$sql="select lang_known from skill where u_id='".$_POST['u_id']."'";
			$res=mysql_query($sql);
			$row=mysql_fetch_row($res);
			if($row>0){
				$val=$row[0].','.$_POST['summary'];
				
				$upt="update skill set lang_known='".$val."' where u_id='".$_POST['u_id']."'";
				$res1=mysql_query($upt);
			}else{
				$inss="insert into skill(u_id,lang_known) values('".$_POST['u_id']."','".$_POST['summary']."')";
				$ress=mysql_query($inss);
			}
			
			
			$ins="insert into skill_tag(u_id,skill) values('".$_POST['u_id']."',
			'".$_POST['summary']."')";
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