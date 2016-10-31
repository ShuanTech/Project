<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['name']) && isset($_POST['qualify']) && isset($_POST['clgName']) && 
	isset($_POST['conCent']) && isset($_POST['frmYr']) && isset($_POST['toYr']) && isset($_POST['skill']) && 
	isset($_POST['loc']) && isset($_POST['insrt']) && isset($_POST['cInsrt'])){
		
			if($_POST['insrt']=='true'){
				$cIns="insert into institution(ins_name,status) values('".$_POST['clgName']."',1)";
				$res=mysql_query($cIns);
			}
			
			if($_POST['cInsrt']=='true'){
				$cInsrt="insert into course(course) values('".$_POST['conCent']."')";
				$res=mysql_query($cInsrt);
			}
			
			$get="select email_id,ph_no from login where u_id='".$_POST['u_id']."'";
			$res=mysql_query($get);
			if($row=mysql_fetch_array($res)){
				$mail=$row[0];
				$phno=$row[1];
			}
			$selDis=select_query("SELECT district,state,country FROM `location` WHERE city='".$_POST['loc']."'");
			
			$ins="INSERT INTO `usr_info`(`u_id`,`full_name`,`city`,`district`,`state`,`country`,`email_id`,`ph_no`) 
				VALUES ('".$_POST['u_id']."','".$_POST['name']."','".$_POST['loc']."','".$selDis[0]['district']."',
				'".$selDis[0]['state']."','".$selDis[0]['country']."','".$mail."','".$phno."')";
				
				$res=mysql_query($ins);
				if($res>0){
					$qIns="insert into education(u_id,level,concentration,ins_name,frm_date,to_date) values(
						'".$_POST['u_id']."','".$_POST['qualify']."','".$_POST['conCent']."','".$_POST['clgName']."',
						'".$_POST['frmYr']."','".$_POST['toYr']."')";
						$res1=mysql_query($qIns);
						
						if($res1>0){
							$skill=array_map('trim',explode(',', $_POST['skill']));
							for($i=0;$i<count($skill);$i++){
								$inss="insert into skill_tag(u_id,skill) values('".$_POST['u_id']."','".$skill[$i]."')";
								$ress=mysql_query($inss);
							}
							$ins="insert into skill(u_id,lang_known) values('".$_POST['u_id']."','".$_POST['skill']."')";
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
							$response['msg']='Failed Education';
							$response['success']=0;
							echo json_encode($response);
						}
						
				}else{
					$response['msg']='Failed usrInfo';
					$response['success']=0;
					echo json_encode($response);
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