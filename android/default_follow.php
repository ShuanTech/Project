<?php 
require('config.php');

if(isset($_POST['u_id']) && isset($_POST['level']) && isset($_POST['clgName']) &&
	isset($_POST['course']) && isset($_POST['loc']) && isset($_POST['skill'])){
		
		$skill=array_map('trim',explode(',', $_POST['skill']));
			for($i=0;$i<count($skill);$i++){
				if($i==0){
					$mtch=$skill[$i];
				}
								
			}
		
		
		$selDis=select_query("SELECT district FROM `location` WHERE city='".$_POST['loc']."'");
	if($_POST['level']=="1"){
			$get=select_query("SELECT l.u_id from login l,education ed where l.u_id=ed.u_id and ed.ins_name='".$_POST['clgName']."' 
			and ed.concentration='".$_POST['course']."' and l.level=2");
				$cnt=count($get);
				if($cnt==0){
					$frsher=select_query("SELECT u_id FROM `employer_info` WHERE `district`='".$selDis[0]['district']."'");
					$fcnt=count($frsher);
					if($fcnt==0){
						$response['message']="No Data Found";
						$response['success']=0;
						echo json_encode($response);
					}else{
						for($i=0;$i<$fcnt;$i++){
							$chk=implode("",$frsher[$i]);
							$con=select_query("select following from following where u_id='".$_POST['u_id']."' and following='".$chk."'");
							$val=count($con);
							if($val==0){
								$ins="insert into follower(u_id,follower) values('".$chk."','".$_POST['u_id']."')";
								$ins1="insert into following(u_id,following) values('".$_POST['u_id']."','".$chk."')";
								$res=mysql_query($ins);
								$res1=mysql_query($ins1);
							}
							 $getJob=select_query("select job_id from job_post where u_id='".$chk."' and 
								skill like '%".$mtch."%' and close=0 order by id desc limit 0,3");
								if(count($getJob)!=0){
									for($j=0;$j<count($getJob);$j++){
										$val=implode("",$getJob[$j]);
										$sql="insert into share_post(frm_id,to_id,post_id,status) values(
										'".$chk."','".$_POST['u_id']."','".$val."',0)";
										$res=mysql_query($sql);
									}  
								}
						}
						
					}
					$response['message']="Success";
					$response['success']=1;
					echo json_encode($response);
				}else{
					for($i=0;$i<$cnt;$i++){
						$chk=implode("",$get[$i]);
						$follow=select_query("select * from following where u_id='".$_POST['u_id']."'
						and following='".$chk."'");
						$val=count($follow);
						
						if($val==0){
							$ins="insert into following(u_id,following) values('".$_POST['u_id']."','".$chk."')";
							$ins1="insert into follower(u_id,follower) values('".$chk."','".$_POST['u_id']."')";
							$res=mysql_query($ins);
							$res1=mysql_query($ins1);
						}
					}
					for($j=0;$j<$cnt;$j++){						
						$sql="SELECT e.u_id FROM wrk_deatail w,employer_info e WHERE w.org_name=e.cmpny_name and 
							w.to_date='present' and w.`u_id`='".$cnt[$j]['u_id']."'";
						$res=mysql_query($sql);
						$row=mysql_fetch_row($res);
						if($row>0){
							$follow=select_query("select * from following where u_id='".$_POST['u_id']."'and 
								following='".$row[0]."'");
							$val=count($follow);
							
							if($val==0){
								$ins="insert into following(u_id,following) values('".$_POST['u_id']."','".$row[0]."')";
								$ins1="insert into follower(u_id,follower) values('".$row[0]."','".$_POST['u_id']."')";
								$res=mysql_query($ins);
								$res1=mysql_query($ins1);
							}
							
							$getJob=select_query("select job_id from job_post where u_id='".$row[0]."' and 
								skill like '%".$mtch."%' and close=0 order by id desc limit 0,3");
								if(count($getJob)!=0){
									for($k=0;$k<count($getJob);$k++){
										$val=implode("",$getJob[$k]);
										$sql="insert into share_post(frm_id,to_id,post_id,status) values(
										'".$chk."','".$_POST['u_id']."','".$val."',0)";
										$res=mysql_query($sql);
									}  
								}
						}
					}
					$frsher=select_query("SELECT u_id FROM `employer_info` WHERE `district`='".$sel[0]['district']."'");
					$fcnt=count($frsher);
					if($fcnt==0){
						$response['message']="Data not found.";
						$response['success']=0;
						echo json_encode($response);
					}else{
						for($l=0;$l<$fcnt;$l++){
							$chk=implode("",$frsher[$l]);
							$con=select_query("select * from following where u_id='".$_POST['u_id']."' and following='".$chk."'");
							$val=count($con);
							if($val==0){
								$ins="insert into follower(u_id,follower) values('".$chk."','".$_POST['u_id']."')";
								$ins1="insert into following(u_id,following) values('".$_POST['u_id']."','".$chk."')";
								$res=mysql_query($ins);
								$res1=mysql_query($ins1);
							}
							 $getJob=select_query("select job_id from job_post where u_id='".$chk."' and 
								skill like '%".$mtch."%' and close=0 order by id desc limit 0,3");
								if(count($getJob)!=0){
									for($m=0;$m<count($getJob);$m++){
										$val=implode("",$getJob[$m]);
										$sql="insert into share_post(frm_id,to_id,post_id,status) values(
										'".$chk."','".$_POST['u_id']."','".$val."',0)";
										$res=mysql_query($sql);
									}  
								}
						}
						
					}
					
				}
				$response['message']="success";
				$response['success']=1;
				echo json_encode($response);
		}else{
			$get=select_query("SELECT l.u_id from login l,education ed where l.u_id=ed.u_id and ed.ins_name='".$_POST['clgName']."' 
				and ed.concentration='".$_POST['course']."' and l.level=1");
			$cnt=count($get);
			if($cnt==0){
				$frsher=select_query("SELECT u_id FROM `employer_info` WHERE `district`='".$sel[0]['district']."'");
					$fcnt=count($frsher);
					if($fcnt==0){
						$response['message']="Data not found";
						$response['success']=0;
						echo json_encode($response);
					}else{
						for($i=0;$i<$fcnt;$i++){
							$chk=implode("",$frsher[$i]);
							$con=select_query("select following from following where u_id='".$_POST['u_id']."' and following='".$chk."'");
							$val=count($con);
							if($val==0){
								$ins="insert into follower(u_id,follower) values('".$chk."','".$_POST['u_id']."')";
								$ins1="insert into following(u_id,following) values('".$_POST['u_id']."','".$chk."')";
								$res=mysql_query($ins);
								$res1=mysql_query($ins1);
							}
							 $getJob=select_query("select job_id from job_post where u_id='".$chk."' and 
								skill like '%".$mtch."%' and close=0 order by id desc limit 0,3");
								if(count($getJob)!=0){
									for($j=0;$j<count($getJob);$j++){
										$val=implode("",$getJob[$j]);
										$sql="insert into share_post(frm_id,to_id,post_id,status) values(
										'".$chk."','".$_POST['u_id']."','".$val."',0)";
										$res=mysql_query($sql);
									}  
								}
						}
						
					}
					$response['message']="Success";
					$response['success']=1;
					echo json_encode($response);
			}else{
				for($i=0;$i<$cnt;$i++){
					$chk=implode("",$get[$i]);
					$follow=select_query("select * from follower where u_id='".$_POST['u_id']."' and follower='".$chk."'");
					$val=count($follow);					
					if($val==0){
						$ins="insert into following(u_id,following) values('".$chk."','".$_POST['u_id']."')";
						$ins1="insert into follower(u_id,follower) values('".$_POST['u_id']."','".$chk."')";
						$res=mysql_query($ins);
						$res1=mysql_query($ins1);
					}
				}
				$sql=select_query("SELECT e.u_id FROM wrk_deatail w,employer_info e WHERE w.org_name=e.cmpny_name and 
					w.to_date='present' and w.`u_id`='".$_POST['u_id']."'");
					if(count($sql)!=0){	
						 for($j=0;$j<$cnt;$j++){
							$chk=implode("",$get[$j]);
							$follow=select_query("select * from follower where u_id='".$sql[0]['u_id']."' and follower='".$chk."'");
							$val=count($follow);
							if($val==0){
								$ins="insert into following(u_id,following) values('".$chk."','".$sql[0]['u_id']."')";
								$ins1="insert into follower(u_id,follower) values('".$sql[0]['u_id']."','".$chk."')";
								$res=mysql_query($ins);
								$res1=mysql_query($ins1);
							}
							$getJob=select_query("select job_id from job_post where u_id='".$sql[0]['u_id']."' and 
										skill like '%".$mtch."%' and close=0 order by id desc limit 0,3");
										if(count($getJob)!=0){
											for($k=0;$k<count($getJob);$k++){
												$val=implode("",$getJob[$k]);
												$sql="insert into share_post(frm_id,to_id,post_id,status) values(
												'".$chk."','".$_POST['u_id']."','".$val."',0)";
												$res=mysql_query($sql);
											}  
										}
						} 						
					}
					$frsher=select_query("SELECT u_id FROM `employer_info` WHERE `district`='".$sel[0]['district']."'");
					$fcnt=count($frsher);
					if($fcnt==0){
						$response['message']="NO Data found";
						$response['success']=0;
						echo json_encode($response);
					}else{
						for($l=0;$l<$fcnt;$l++){
							$chk=implode("",$frsher[$l]);
							$con=select_query("select following from following where u_id='".$_POST['u_id']."' and following='".$chk."'");
							$val=count($con);
							if($val==0){
								$ins="insert into follower(u_id,follower) values('".$chk."','".$_POST['u_id']."')";
								$ins1="insert into following(u_id,following) values('".$_POST['u_id']."','".$chk."')";
								$res=mysql_query($ins);
								$res1=mysql_query($ins1);
							}
							 $getJob=select_query("select job_id from job_post where u_id='".$chk."' and 
								skill like '%".$mtch."%' and close=0 order by id desc limit 0,3");
								if(count($getJob)!=0){
									for($m=0;$m<count($getJob);$m++){
										$val=implode("",$getJob[$m]);
										$sql="insert into share_post(frm_id,to_id,post_id,status) values(
										'".$chk."','".$_POST['u_id']."','".$val."',0)";
										$res=mysql_query($sql);
									}  
								}
						}
						
					}
			}
			$response['message']="Success";
			$response['success']=1;
			echo json_encode($response);
		}
	/* if($_POST['level']=='1'){
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
					
					$getJob=select_query("select job_id from job_post where u_id='".$row[0]."' and close=0");
					for($i=0;$i<count($getJob);$i++){
						$val=implode("",$getJob[$i]);
						$sql="insert into share_post(frm_id,to_id,post_id,status) values(
						'".$row[0]."','".$_POST['u_id']."','".$val."',0)";
						$res=mysql_query($sql);
						$upt="update job_post set shared=shared+1 where job_id='".$val."'";
						$res1=mysql_query($upt);	
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
		
	} */
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