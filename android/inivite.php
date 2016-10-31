<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['frm_id']) && isset($_POST['name'])){
	$response=array();
		
		$jt=mysql_query("select job_id from job_post where title='".$_POST['name']."'");
		$res1=mysql_fetch_row($jt);
		$chk=mysql_query("select * from notify_access where to_id='".$_POST['u_id']."' and post_id='".$res1[0]."' and type=4");
		if(mysql_num_rows($chk)>0){
			$response['message']="Already Invite";
			$response['success']=2;
			echo json_encode($response);
		}else{
			$get="select cmpny_name from employer_info where u_id='".$_POST['frm_id']."'";
				$res=mysql_query($get);
				$row=mysql_fetch_row($res);
			$content=$row[0].' : Send the Invitation for '.$_POST['name'].' Job Openings.';
			
			$sql1="insert into notify_access(frm_id,to_id,post_id,content,type) values('".$_POST['frm_id']."',
				'".$_POST['u_id']."','".$res1[0]."','".$content."',4)";
			$res1=mysql_query($sql1);
			if($res1>0){
				$response['message']="updated";
				$response['success']=1;
				echo json_encode($response);
			}else{
				$response['message']="Not Updated";
				$response['success']=0;
				echo json_encode($response);
			}
		}
	
	
}else{
	$response['message']="NO Data Post";
	$response['success']=0;
	echo json_encode($response);
}
?>