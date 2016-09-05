<?php 
require('config.php');
if(isset($_POST['j_id']) && isset($_POST['a_id']) && isset($_POST['r_id']) && isset($_POST['loc']) 
	&& isset($_POST['dt']) && isset($_POST['tme']) && isset($_POST['cmmts'])){
		
	$response=array();
	$sql="update job_applied set shrt=1 where applied_usr='".$_POST['a_id']."'";
	$res=mysql_query($sql);
	$sql1="update notify_access set vwed=1 where frm_id='".$_POST['a_id']."'";
	$nres=mysql_query($sql1);
	
	if($res>0){
		$ins="insert into intrvew_panel(job_id,usr_id,venue,intervew_date,intervew_time,
		type,commnts) values('".$_POST['j_id']."','".$_POST['a_id']."','".$_POST['loc']."',
		'".$_POST['dt']."','".$_POST['tme']."',1,'".$_POST['cmmts']."')";
		$res1=mysql_query($ins);
		if($res1>0){
			$usrName=select_query("select full_name from login where u_id='".$_POST['a_id']."'");
			$other=select_query("select e.cmpny_name,j.u_id,j.title from employer_info e,
				job_post j,job_applied a where e.u_id=j.u_id and j.job_id=a.job_id and 
				j.job_id='".$_POST['j_id']."' and a.applied_usr='".$_POST['a_id']."'");	
				
		
				$usr=$usrName[0]['full_name'];
				$cmpny=$other[0]['cmpny_name'];
				$cId=$other[0]['u_id'];
				$title=$other[0]['title'];
				
				$shrt_content=$cmpny." :".'You have short listed for the job post of'.$title;
				$ins_alrt="insert into notify_access(frm_id,to_id,post_id,content,type) values(
				'".$cId."','".$_POST['a_id']."','".$_POST['j_id']."','".$shrt_content."',1)";
				
				$res3=mysql_query($ins_alrt);
				if($res3>0){
					$response['message']='successfully updated';
					$response['success']=1;
					echo json_encode($response);
				}else{
					$response['message']='Not Inserted Notify';
					$response['success']=0;
					echo json_encode($response);
				}
			
			
		}else{
			$response['message']='Not Inserted';
			$response['success']=0;
			echo json_encode($response);
		}
		
	}else{
		$response['message']='Not successfully updated';
		$response['success']=0;
		echo json_encode($response);
	}
}else{
	$response['message']='No Data Post';
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