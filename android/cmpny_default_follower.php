<?php
require('config.php');
if(isset($_POST['cName']) && isset($_POST['cDistrict'])){
	$num=0;
	$fnum=0;
	
	$frsher=select_query("SELECT l.u_id FROM `usr_info` u,login l WHERE l.u_id=u.u_id 
	and u.city='".$_POST['cDistrict']."' and l.level='1'");
		$fcnt=count($frsher);
		if($fcnt==''){
			$response['message']="No Fresher";
			$response['success']=0;
			echo json_encode($response);
		}else{
			for($i=0;$i<$fcnt;$i++){
				$chk=implode("",$frsher[$i]);
				$con=select_query("select follower from follower where u_id='".$_POST['u_id']."' and follower='".$chk."'");
				$val=count($con);
				if($val==''){
					$ins="insert into follower(u_id,follower) values('".$_POST['u_id']."','".$chk."')";
					$ins1="insert into following(u_id,following) values('".$chk."','".$_POST['u_id']."')";
					$res=mysql_query($ins);
					$res1=mysql_query($ins1);
					$fnum=$fnum+1;
				}
			}
		}
	
	
	$get=select_query("SELECT l.u_id from login l,wrk_deatail w WHERE l.u_id=w.u_id and 
		w.org_name='".$_POST['cName']."'");
	$cnt=count($get);
	if($cnt==''){
		$response['message']="Follower Not Found";
		$response['success']=0;
		echo json_encode($response);
	}else{
		for($i=0;$i<$cnt;$i++){
			$chk=implode("",$get[$i]);
			$con=select_query("select follower from follower where u_id='".$_POST['u_id']."' and follower='".$chk."'");
			$val=count($con);
			if($val==''){
				$ins="insert into follower(u_id,follower) values('".$_POST['u_id']."','".$chk."')";
				$ins1="insert into following(u_id,following) values('".$chk."','".$_POST['u_id']."')";
				$res=mysql_query($ins);
				$res1=mysql_query($ins1);
				$num=$num+1;
			}
		}
		
			$response['message']=$frsher.'+'.$num."Followers";
			$response['success']=1;
			echo json_encode($response);
		 
	}
}else{
	$response['message']="NO Data Post";
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