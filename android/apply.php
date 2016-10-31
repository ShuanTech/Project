<?php
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['j_id']) && isset($_POST['refer'])){
	
		
	 $aName="SELECT l.full_name from login l,share_post s WHERE s.to_id=l.u_id and 
				s.to_id='".$_POST['u_id']."' and s.post_id='".$_POST['j_id']."'";
					$ares=mysql_query($aName);
					$arow=mysql_fetch_row($ares);
			
			$pName="SELECT e.cmpny_name,j.u_id,j.title from job_post j,employer_info e WHERE 
				j.u_id=e.u_id and j.job_id='".$_POST['j_id']."'";
					$pres=mysql_query($pName);
					$prow=mysql_fetch_row($pres);
					
			$cmpnyCotent=$prow[2].' :'.$arow[0].' applied on your post';
			$seniorContent=$arow[0].' : Request to refer the job post of '.$prow[2];
			$link="http://udyo.udyomitra.com/pages/resume.php?u_id=".$_POST['u_id'];
			$sql="insert into job_applied(job_id,applied_usr,refer_id,resume) values(
			'".$_POST['j_id']."','".$_POST['u_id']."','direct','".$link."')";
			$res=mysql_query($sql);
			if($res>0){
				$sql1="update job_post set applied=applied+1 where job_id='".$_POST['j_id']."'";
				$sql2="update share_post set status=2 where to_id='".$_POST['u_id']."' and post_id='".$_POST['j_id']."'";
				$sql3="insert into notify_access(frm_id,to_id,post_id,content,type) values(
					'".$_POST['u_id']."','".$prow[1]."','".$_POST['j_id']."','".$cmpnyCotent."',3)";
				
				$res1=mysql_query($sql1);
				$res2=mysql_query($sql2);
				$res3=mysql_query($sql3);
				
				if($_POST['refer']!='refer'){
					$referId=explode(',',$_POST['refer']);
					for($i=0;$i<count($referId);$i++){
						$rIns="insert into notify_access(frm_id,to_id,post_id,content,type
						) values('".$_POST['u_id']."','".$referId[$i]."','".$_POST['j_id']."',
						'".$seniorContent."',2)";
							$res=mysql_query($rIns);
							
					}
				}
				
				if($res1>0){
					
					$response['message']='Success';
					$response['success']=1;
					echo json_encode($response);
				}else{
					
					$response['message']='Not Success';
					$response['success']=0;
					echo json_encode($response);
				}
			}else{
			
				$response['message']='Not Success';
				$response['success']=0;
				echo json_encode($response);
			}  
		
	
	
}else{
	$response['message']='Data not post';
	$response['success']=0;
	echo json_encode($response);
}
 
?>