<?php 
require('config.php');
if(isset($_POST['u_id'])){
	
	$clg="select ins_name from education where u_id='".$_POST['u_id']."'";
	$val=mysql_query($clg);
	$row=mysql_fetch_row($val);
	
	$follow=select_query("select l.u_id from login l,education e where 
	e.ins_name='".$row[0]."' and l.u_id=e.u_id and l.u_id!='".$_POST['u_id']."'");
		$val=count($follow);
		$response['suggest']=array();
		for($i=0;$i<$val;$i++){
			$chk=implode("",$follow[$i]);
			$chkfollow=select_query("select * from following where u_id='".$_POST['u_id']."' and 
			following='".$chk."'");
				$val1=count($chkfollow);
					if($val1==''){
						$chkfollower=select_query("select * from follower where u_id='".$chk."' and 
							follower='".$_POST['u_id']."'");
								$val2=count($chkfollower);
									if($val2==''){
										$ans="select full_name,pro_pic,level from login where
											u_id='".$chk."'";
												$res=mysql_query($ans);
												$row=mysql_fetch_row($res);
												$data=array();
												$data['u_id']=$chk;
												$data['full_name']=$row[0];
												$data['pro_pic']=$row[1];
												$data['level']=$row[2];
												array_push($response['suggest'],$data);
									}
					}
				
		}
		$response['success']=1;
		echo json_encode($response);
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