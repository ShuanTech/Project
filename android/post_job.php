<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['title']) && isset($_POST['skill'])&& 
	isset($_POST['type']) && isset($_POST['cate']) && isset($_POST['sal']) && isset($_POST['loc']) 
	&& isset($_POST['level']) && isset($_POST['descr'])){
		
		$jId="J".date('ymdhi');
		$ins="insert into job_post(u_id,job_id,title,skill,type,category,package,level,
		location,description,date_created) values('".$_POST['u_id']."','".$jId."','".$_POST['title']."',
		'".$_POST['skill']."','".$_POST['type']."','".$_POST['cate']."','".$_POST['sal']."',
		'".$_POST['level']."','".$_POST['loc']."','".$_POST['descr']."',now())";
		
		$result=mysql_query($ins);
			if($result>0){
				
				$post=select_query("select follower from follower where u_id='".$_POST['u_id']."'");
				
				for($i=0;$i<count($post);$i++){
					$val=implode("",$post[$i]);
					$sql="insert into share_post(frm_id,to_id,post_id,status) values(
					'".$_POST['u_id']."','".$val."','".$jId."',0)";
					$res=mysql_query($sql);
					$upt="update job_post set shared=shared+1 where job_id='".$jId."'";
					$res1=mysql_query($upt);	
				}
				$sql="insert into share_post(frm_id,to_id,post_id,status) values(
					'".$_POST['u_id']."','".$_POST['u_id']."','".$jId."',0)";
					$res=mysql_query($sql);
				
				$response['msg']='Data Inserted';
				$response['success']=1;
				echo json_encode($response);
			}else{
				$response['msg']='Not Inserted';
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