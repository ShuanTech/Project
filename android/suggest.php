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
					if($val1==0){
						$chkfollower=select_query("select * from follower where u_id='".$chk."' and 
							follower='".$_POST['u_id']."'");
								$val2=count($chkfollower);
									if($val2==0){
									$data=array();
									$sql="select u_id,full_name,pro_pic,level from login where u_id='".$chk."'";
									$result=mysql_query($sql);
									if(mysql_num_rows($result)>0){
											
											while($row=mysql_fetch_array($result)){
												$data['u_id']=$row[0];
												$get=mysql_query("select full_name from usr_info where u_id='".$row[0]."'");
												$name=mysql_fetch_array($get);
												$data['full_name']=$name[0];
												$data['pro_pic']=$row[2];
												$data['level']=$row[3];
												if($row['level']=='1'||$row['level']=='2'){
													$wrk="select org_name,position,location from wrk_deatail where u_id='".$row[0]."' and to_date='present'";
														$wres=mysql_query($wrk);
														if(mysql_num_rows($wres)==0){
															$edu="select ins_name,location from education where u_id='".$row[0]."' order by level asc limit 1";
																$eres=mysql_query($edu);
																if(mysql_num_rows($eres)==0){
																	$loc="select city,country from usr_info where u_id='".$row[0]."'";
																		$lres=mysql_query($loc);
																		if(mysql_num_rows($lres)){
																			$data['sec']="";
																		}else{
																			$data['sec']=$row[0].', '.$row[1];
																		}
																}else{
																	$row2=mysql_fetch_row($eres);
																	$data['sec']=$row2[0].', '.$row2[1];
																}
														}else{
															$row1=mysql_fetch_row($wres);
															$data['sec']=$row1[1].' at '.$row1[0].', '.$row1[2];
														}
												}else{
													$cmpny="select cmpny_name,i_type,city,country from employer_info where u_id='".$row[0]."'";
														$cres=mysql_query($cmpny);
														if(mysql_num_rows($cres)==0){
															$data['full_name']=$row[1];
															$data['sec']='';
														}else{
															$getData=mysql_fetch_array($cres);
															$data['full_name']=$getData[0];
															$data['sec']=$getData[1].' - '.$getData[2].', '.$getData[3];
														}
												}
												array_push($response['suggest'],$data);
											}
												$response['success']=1;
												echo json_encode($response); 
										}else{
												$response["message"]="No Data";
												$response["success"]=0;
												echo json_encode($response);
										}
																		
																		
																		
																		
										
									}
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