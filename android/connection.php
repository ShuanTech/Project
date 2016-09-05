<?php
require('config.php');
if(isset($_POST['u_id'])){
	$num=0;
	$clg="select ins_name,concentration from education where u_id='".$_POST['u_id']."'";
	$val=mysql_query($clg);
	$row=mysql_fetch_row($val);
	
	$get=select_query("SELECT l.u_id from login l,education ed where l.u_id=ed.u_id and ed.ins_name='".$row[0]."' and ed.concentration='".$row[1]."' and l.level=2");
	$cnt=count($get);
	if($cnt==''){
		$response['message']="Senior Not Found";
		$response['success']=0;
		echo json_encode($response);
	}else{
		for($i=0;$i<$cnt;$i++){
			$chk=implode("",$get[$i]);
			
			$con=select_query("select connect from connection where u_id='".$_POST['u_id']."' and connect='".$chk."'");
			$val=count($con);
			
			if($val==''){
				if($_POST['u_id']!=$chk){
					$ins="insert into connection(u_id,connect) values('".$_POST['u_id']."','".$chk."')";
					$ins1="insert into connection(u_id,connect) values('".$chk."','".$_POST['u_id']."')";
					$res=mysql_query($ins);
					$res1=mysql_query($ins1);
					$num=$num+1;
				}
			}
		}
			$response['message']=$num."Connect Success";
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