<?php
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['org_name']) && isset($_POST['position']) && 
	isset($_POST['loc']) && isset($_POST['frm']) && isset($_POST['to']) && 
	isset($_POST['type']) && $_POST['ins']){
	
	if($_POST['type']=='add'){
		
		if($_POST['ins']=='true'){
			$oIns="insert into organization(org_name,land_mark) values('".$_POST['org_name']."',
				'".$_POST['loc']."')";
					$res=mysql_query($oIns);
		}
			
		$ins="insert into wrk_deatail(u_id,org_name,position,location,from_date,to_date) values(
		'".$_POST['u_id']."','".$_POST['org_name']."','".$_POST['position']."',
		'".$_POST['loc']."','".$_POST['frm']."','".$_POST['to']."')";
		
		$result=mysql_query($ins);
		
		$cId="SELECT u_id from employer_info where cmpny_name='".$_POST['org_name']."'";
			$cres=mysql_query($cId);
			$cdata=mysql_fetch_row($cres);
			if($cdata[0]>0){
				$chk="select * from following where u_id='".$_POST['u_id']."' and 
					following='".$cdata[0]."'";
						$chres=mysql_query($chk);
						$chdata=mysql_fetch_row($chres);
							if($chdata[0]<0){
								
								$following="insert into following(u_id,following) values('".$_POST['u_id']."',
									'".$cdata[0]."')";
								$follower="insert into follower(u_id,follower) values('".$cdata[0]."',
									'".$_POST['u_id']."')";
									$efg=mysql_query($following);
									$efr=mysql_query($follower);
							}
			}
			
			
		
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
		
		$ins="update wrk_deatail set org_name='".$_POST['org_name']."',
		position='".$_POST['position']."',location='".$_POST['loc']."',
		from_date='".$_POST['frm']."',to_date='".$_POST['to']."' where id='".$_POST['u_id']."'";
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