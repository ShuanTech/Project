<?php
require('config.php');
if(isset($_POST['u_id'])){
	$num=0;
	$cName="select cmpny_name from employer_info where u_id='".$_POST['u_id']."'";
	$val=mysql_query($cName);
	$row=mysql_fetch_row($val);
	$get=select_query("SELECT l.u_id from login l,wrk_deatail w WHERE l.u_id=w.u_id and 
		w.org_name='".$row[0]."'");
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
		
			$response['message']=$num."Followers";
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