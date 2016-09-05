<?php
require('config.php');
if(isset($_POST['u_id'])){
	$response['info']=array();
	$sql="SELECT full_name,pro_pic,cover_pic,passwrd FROM `login` WHERE u_id='".$_POST['u_id']."'";
		$res=mysql_query($sql);
		while($row=mysql_fetch_row($res)){
			$data=array();
			
			$data['full_name']=$row[0];
			$data['pro_pic']=$row[1];
			$data['cover_pic']=$row[2];
			$data['passwrd']=$row[3];
		
			$connect="select count(connect) as connection from connection where u_id='".$_POST['u_id']."'";
				$rst=mysql_query($connect);
				while($row1=mysql_fetch_row($rst)){
					$data['connection']=$row1[0];
				}
			
			$follower="select count(follower) as follower from follower where u_id='".$_POST['u_id']."'";
				$fol=mysql_query($follower);
				$row2=mysql_fetch_row($fol);
				$data['follower']=$row2[0];
				
				
			$follow="select count(following) as following from following where u_id='".$_POST['u_id']."'";
				$folw=mysql_query($follow);
				$row3=mysql_fetch_row($folw);
				$data['following']=$row3[0];
				
			$alert="select count(*) as alert from notify_access where to_id='".$_POST['u_id']."' and vwed=0";
				$alrt=mysql_query($alert);
				$row4=mysql_fetch_row($alrt);
				$data['alert']=$row4[0];
			
			array_push($response['info'],$data);
		}
		$response['success']=1;
		echo json_encode($response);
		
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