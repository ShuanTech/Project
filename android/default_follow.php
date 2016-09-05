<?php 
require('config.php');

if(isset($_POST['u_id']) && isset($_POST['level']) && isset($_POST['clgName']) &&
	isset($_POST['course'])){
	/* $clg="select ins_name,concentration from education where u_id='".$_POST['u_id']."'";
	$val=mysql_query($clg);
	$row=mysql_fetch_row($val); */
	if($_POST['level']=='1'){
		$get=select_query("SELECT l.u_id from login l,education ed where l.u_id=ed.u_id 
		and ed.ins_name='".$_POST['clgName']."' and ed.concentration='".$_POST['course']."' 
		and l.level=2");
		$cnt=count($get);
		if($cnt==''){
			$response['message']="Senior Not Found";
			$response['success']=0;
			echo json_encode($response);
		}else{
			for($i=0;$i<$cnt;$i++){
				$chk=implode("",$get[$i]);
				$follow=select_query("select following from following where u_id='".$_POST['u_id']."'
				and following='".$chk."'");
				$val=count($follow);
				
				if($val==''){
					$ins="insert into following(u_id,following) values('".$_POST['u_id']."','".$chk."')";
					$ins1="insert into follower(u_id,follower) values('".$chk."','".$_POST['u_id']."')";
					$res=mysql_query($ins);
					$res1=mysql_query($ins1);
				}
			}
			
			for($i=0;$i<$cnt;$i++){
				$chk=implode("",$get[$i]);
				
				$sql="SELECT e.u_id FROM wrk_deatail w,employer_info e WHERE 
				w.org_name=e.cmpny_name and w.to_date='present' and w.`u_id`='".$chk."'";
				$res=mysql_query($sql);
				$row=mysql_fetch_row($res);
				if($row>0){
					$follow=select_query("select following from following where u_id='".$_POST['u_id']."'
				and following='".$row[0]."'");
				$val=count($follow);
					
					if($val==''){
						$ins="insert into following(u_id,following) values('".$_POST['u_id']."','".$row[0]."')";
						$ins1="insert into follower(u_id,follower) values('".$row[0]."','".$_POST['u_id']."')";
						$res=mysql_query($ins);
						$res1=mysql_query($ins1);
					}
				}
				
				
				
			}
			
			$response['message']="Follow Success";
			$response['success']=1;
			echo json_encode($response);
		}
		
		
	}else{
		$get=select_query("SELECT l.u_id from login l,education ed where l.u_id=ed.u_id 
		and ed.ins_name='".$_POST['clgName']."' and ed.concentration='".$_POST['course']."' 
		and l.level=1");
		$cnt=count($get);
		if($cnt==''){
			$response['message']="Junior Not Found";
			$response['success']=0;
			echo json_encode($response);
		}else{
			for($i=0;$i<$cnt;$i++){
				$chk=implode("",$get[$i]);
				$follow=select_query("select follower from follower where u_id='".$_POST['u_id']."'");
				$val=count($follow);
				
				if($val==''){
					$ins="insert into following(u_id,following) values('".$chk."','".$_POST['u_id']."')";
					$ins1="insert into follower(u_id,follower) values('".$_POST['u_id']."','".$chk."')";
					$res=mysql_query($ins);
					$res1=mysql_query($ins1);
				}
			}
			
			for($i=0;$i<$cnt;$i++){
				$chk=implode("",$get[$i]);
				
				$sql="SELECT e.u_id FROM wrk_deatail w,employer_info e WHERE 
				w.org_name=e.cmpny_name and w.to_date='present' and w.`u_id`='".$_POST['u_id']."'";
				$res=mysql_query($sql);
				$row=mysql_fetch_row($res);
				
				$follow=select_query("select following from following where u_id='".$_POST['u_id']."'
				and following='".$row[0]."'");
				$val=count($follow);
				
				if($val==''){
					$ins="insert into following(u_id,following) values('".$chk."','".$row[0]."')";
					$ins1="insert into follower(u_id,follower) values('".$row[0]."','".$chk."')";
					$res=mysql_query($ins);
					$res1=mysql_query($ins1);
				}
				
			}
			
			
			$response['message']="Follow Success";
			$response['success']=1;
			echo json_encode($response);
			
		}
		
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