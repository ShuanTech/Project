<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['j_id'])){
	$result;
	$res1;
	$num=0;
	$uName;
	$sql=select_query("select follower from follower where u_id='".$_POST['u_id']."'");
	if(count($sql)==0){
		$result=3;
	}else{
		$level=select_query("select level from login where u_id='".$_POST['u_id']."'");
		$getCName=select_query("select e.u_id,e.cmpny_name from employer_info e,job_post j where j.u_id=e.u_id and 
					j.job_id='".$_POST['j_id']."'");
		$cName=$getCName[0]['cmpny_name'];
		if($level[0]['level']=='3'){
			$getName=select_query("select cmpny_name from employer_info where u_id='".$_POST['u_id']."'");
			$uName=$getName[0]['cmpny_name'];
		}else{
			$getName=select_query("select full_name from usr_info where u_id='".$_POST['u_id']."'");
			$uName=$getName[0]['full_name'];
		}
		$content=$uName.' shared '.$cName.' post';
		for($i=0;$i<count($sql);$i++){
			$get=implode("",$sql[$i]);
			if(count(select_query("select * from share_post where frm_id='".$_POST['u_id']."' and  to_id='".$get."' and post_id='".$_POST['j_id']."'"))==0){
				$share="insert into share_post(frm_id,to_id,post_id,status) values(
						'".$_POST['u_id']."','".$get."','".$_POST['j_id']."',0)";
				$notify="insert into notify_access(frm_id,to_id,post_id,content,type) values('".$_POST['u_id']."',
					'".$get."','".$_POST['j_id']."','".$content."',5)";
				$res=mysql_query($share);
				$res1=mysql_query($notify);
				$result=1;
			}else{
			
				$result=2;
			}
			
		}
	}
	
	
	
	if($result==1){
		$upt="update job_post set shared=shared+1 where job_id='".$_POST['j_id']."'";
		$cnt=$uName.' shared your post.';
		$notifyy="insert into notify_access(frm_id,to_id,post_id,content,type) values('".$_POST['u_id']."',
					'".$getCName[0]['u_id']."','".$_POST['j_id']."','".$cnt."',5)";
		$ress=mysql_query($notifyy); 
		$res1=mysql_query($upt);
		$response['message']="Shred Successfully";
		$response['success']=1;
		echo json_encode($response);
	}else if($result==2){
		$response['message']="Already Shred";
		$response['success']=2;
		echo json_encode($response);
	}else if($result==3){
		$response['message']="You cannot shred";
		$response['success']=3;
		echo json_encode($response);
	}else{
		$response['message']="Not Shred Successfully";
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