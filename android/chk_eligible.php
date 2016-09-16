<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['j_id']) && isset($_POST['level'])){


	$cmny_id="select j.u_id from share_post s, job_post j where j.job_id=s.post_id and 
	s.post_id='".$_POST['j_id']."' and s.to_id='".$_POST['u_id']."'";
	$res=mysql_query($cmny_id);
	$row=mysql_fetch_row($res);
	
	$cmpnyName=$row[0];
	
	if($_POST['level']=='2'){
		
		$chk=select_query("select  w.u_id from wrk_deatail w,employer_info e,share_post s
		where e.cmpny_name=w.org_name and e.u_id='".$row[0]."' and w.u_id=s.to_id and 
		s.to_id='".$_POST['u_id']."' and s.post_id='".$_POST['j_id']."'");
			$cnt=count($chk);
			
			if($cnt!=''){
				$response['message']='same company';
				$response['success']=1;
				echo json_encode($response);
			}else{ 
				$sql="select j.skill from job_post j,share_post s where s.post_id=j.job_id
				and s.post_id='".$_POST['j_id']."' and s.to_id='".$_POST['u_id']."'";
					$res1=mysql_query($sql);
					$row=mysql_fetch_row($res1);
					$getSkill=array_map('trim',explode(',', $row[0]));
					$len=count($getSkill);
					$match=round($len/2);
					
				$sql1=select_query("select s.skill from share_post p,skill_tag s where s.u_id=p.to_id 
				and p.to_id='".$_POST['u_id']."' and p.post_id='".$_POST['j_id']."'");
				
				
				 $ans=array();
				for($k=0;$k<count($sql1);$k++){
					$ans[$k]=implode(",",$sql1[$k]);
				}
				
				
					$cnt=1;
					for($i=0;$i<count($getSkill);$i++){
						
						for($j=0;$j<count($ans);$j++){
							if(strcasecmp($getSkill[$i],$ans[$j])==0){
								$cnt=$cnt+1;
								break;
							}
						} 
					}
					
					if($match<$cnt){
						$response['refer']=array();
						$sql=select_query("select l.u_id,l.full_name from wrk_deatail w
						,employer_info e,login l where e.cmpny_name=w.org_name and 
						e.u_id='".$cmpnyName."' and w.u_id=l.u_id");
							for($i=0;$i<count($sql);$i++){
								
								$chkfollow=select_query("select * from following where 
								u_id='".$_POST['u_id']."' and following='".$sql[$i]['u_id']."'");
								if(count($chkfollow)!=''){
									array_push($response['refer'],$sql[$i]);
								}
								
								
							}
						
						$response['success']=3;
						echo json_encode($response);
						
					}else{
						$response['message']='Not meet their Requirement';
						$response['success']=2;
						echo json_encode($response);
					} 
			}
		
		
		
	}else{
						$response['refer']=array();
						
						$chkFrsh=select_query("select level from job_post where 
						job_id='".$_POST['j_id']."'");
						
						if($chkFrsh[0]['level']=="fresher"){
								$sql=select_query("select l.u_id,l.full_name from wrk_deatail w
									,employer_info e,login l where e.cmpny_name=w.org_name and 
									e.u_id='".$cmpnyName."' and w.u_id=l.u_id");
										for($i=0;$i<count($sql);$i++){
											$chkfollow=select_query("select * from following where 
											u_id='".$_POST['u_id']."' and following='".$sql[$i]['u_id']."'");
											if(count($chkfollow)!=''){
												array_push($response['refer'],$sql[$i]);
											}
										}
									
									$response['success']=3;
									echo json_encode($response);
						}else{
							$response['success']=4;
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