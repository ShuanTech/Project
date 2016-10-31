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
		$val=rtrim($_POST['summary'], ', ');
		$skill=array_map('trim',explode(',', $_POST['summary']));
			for($i=0;$i<count($skill);$i++){
				if(count(select_query("select skill from skill_tag where skill='".$skill[$i]."'"))!=0){
					$inss="insert into skill_tag(u_id,skill) values('".$_POST['u_id']."','".$skill[$i]."')";
					$ress=mysql_query($inss);
				}
				
			}
			if(count(select_query("select * from skill where u_id='".$_POST['u_id']."'"))==0){
				
				$ins="insert into skill(u_id,lang_known) values('".$_POST['u_id']."','".$val."')";
				$result=mysql_query($ins);
				if($result>0){
					$response['msg']='success';
					$response['success']=1;
					echo json_encode($response);
				}else{
					$response['msg']='Failed Skill';
					$response['success']=0;
					echo json_encode($response);
				}
			}else{
				$upt=mysql_query("update skill set lang_known='".$val."' where u_id='".$_POST['u_id']."'");
				if($upt>0){
					$response['msg']='success';
					$response['success']=1;
					echo json_encode($response);
				}else{
					$response['msg']='Failed Skill';
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