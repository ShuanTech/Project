<?php
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['title']) && isset($_POST['platform']) && 
	isset($_POST['role']) && isset($_POST['teamSze']) && isset($_POST['dur']) && 
	isset($_POST['desc']) && isset($_POST['url']) && isset($_POST['isAcd']) && isset($_POST['type'])){
	
	if($_POST['type']=='add'){
		 $ins="insert into project_detail(u_id,p_title,p_platform,p_role,p_team_mem,p_dur,
		 p_url,p_description) values('".$_POST['u_id']."','".$_POST['title']."','".$_POST['platform']."',
		 '".$_POST['role']."','".$_POST['teamSze']."','".$_POST['dur']."','".$_POST['url']."',
		 '".$_POST['desc']."')";
		
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
		$ins="update project_detail set p_title='".$_POST['title']."',p_platform='".$_POST['platform']."',
		p_role='".$_POST['role']."',p_team_mem='".$_POST['teamSze']."',p_dur='".$_POST['dur']."',
		p_url='".$_POST['url']."',p_description='".$_POST['desc']."',p_stus='".$_POST['isAcd']."' 
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