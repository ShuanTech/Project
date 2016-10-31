<?php
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['frm_id']) && isset($_POST['level'])){
	
			if(count(select_query("select * from following where u_id='".$_POST['frm_id']."' and following='".$_POST['u_id']."' and close=0"))!=0){
				$uptfolw=mysql_query("update following set close=1 where u_id='".$_POST['frm_id']."' and following='".$_POST['u_id']."'");
				$uptunfolw=mysql_query("update follower set close=1 where u_id='".$_POST['u_id']."' and follower='".$_POST['frm_id']."'");
				if($uptfolw>0){
					$response['message']="Success";
					$response['success']=2;
					echo json_encode($response);
				}else{
					$response['message']="Follower Not Found";
					$response['success']=0;
					echo json_encode($response);
				}
			}else if(count(select_query("select * from following where u_id='".$_POST['frm_id']."' and following='".$_POST['u_id']."' and close=1"))!=0){
				$uptfolw=mysql_query("update following set close=0 where u_id='".$_POST['frm_id']."' and following='".$_POST['u_id']."'");
				$uptunfolw=mysql_query("update follower set close=0 where u_id='".$_POST['u_id']."' and follower='".$_POST['frm_id']."'");
				if($uptfolw>0){
					$response['message']="Success";
					$response['success']=3;
					echo json_encode($response);
				}else{
					$response['message']="Follower Not Found";
					$response['success']=0;
					echo json_encode($response);
				}
			}else{
				$get=select_query("select u.full_name,l.level from usr_info u,login l where l.u_id=u.u_id and l.u_id='".$_POST['frm_id']."'");
					
					$content=$get[0]['full_name'].' : '.'Follows you.';
					$notify="insert into notify_access(frm_id,to_id,content,type,level) values('".$_POST['frm_id']."','".$_POST['u_id']."','".$content."',7,'".$get[0]['level']."')";
					mysql_query($notify);
				$ins1="insert into following(u_id,following) values('".$_POST['frm_id']."','".$_POST['u_id']."')";
				$ins="insert into follower(u_id,follower) values('".$_POST['u_id']."','".$_POST['frm_id']."')";
				$res1=mysql_query($ins1);
				$res=mysql_query($ins);
				
				if($_POST['level']=='3'){
					
					$getJob=select_query("select job_id from job_post where u_id='".$_POST['u_id']."' and close=0");
						for($i=0;$i<count($getJob);$i++){
							$val=implode("",$getJob[$i]);
							$sql="insert into share_post(frm_id,to_id,post_id,status) values(
							'".$_POST['u_id']."','".$_POST['frm_id']."','".$val."',0)";
							$res=mysql_query($sql);
							$upt="update job_post set shared=shared+1 where job_id='".$val."'";
							$res1=mysql_query($upt);	
						}
				}		
				if($res1>0){
					$response['message']="Success";
					$response['success']=1;
					echo json_encode($response);
				}else{
					$response['message']="Follower Not Found";
					$response['success']=0;
					echo json_encode($response);
				}
			}
			
	
	
	
}else{
	$response['message']="Data Not Found";
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