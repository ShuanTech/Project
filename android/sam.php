<?php 
require('config.php');




$slctCity=select_query("select applied_usr,refer_id,resume from job_applied where job_id='J1609040300' and
		shrt=0");
	$cnt=count($slctCity);
	$response['job']=array();
				if($cnt==""){
					$response["message"]="No Data";
					$response["success"]=0;
					
					echo json_encode($response);
				}else{
					$data=array();
					for($i=0;$i<$cnt;$i++){
						if($slctCity[$i]['refer_id']=='direct'){
							$sql=select_query("select full_name from login 
							where u_id='".$slctCity[$i]['applied_usr']."'");
							$data['applied']=$sql[0]['full_name'];
							$data['refer']='direct';
							$data['applied_usr']=$slctCity[$i]['applied_usr'];
							$data['refer_id']=$slctCity[$i]['refer_id'];
							$data['resume']=$slctCity[$i]['resume'];
						}else{
							$sql=select_query("select l.full_name as applied,n.full_name
							as refer from login l,login n 
							where l.u_id='".$slctCity[$i]['applied_usr']."' and 
							n.u_id='".$slctCity[$i]['refer_id']."'");
							$data['applied']=$sql[0]['applied'];
							$data['refer']=$sql[0]['refer'];
							$data['applied_usr']=$slctCity[$i]['applied_usr'];
							$data['refer_id']=$slctCity[$i]['refer_id'];
							$data['resume']=$slctCity[$i]['resume'];
						}
						
						array_push($response["job"],$data);
					}
					$response['success']=1;
					echo json_encode($response); 
				} 

/* echo "The time is " . date("ymdhisu");
$chk="select * from following where u_id='U1609020324' and 
					following='U1608191228'";
						$chres=mysql_query($chk);
						$chdata=mysql_fetch_row($chres);
			if($chdata[0]>0){
				echo 'wrk';
			}else{
				echo 'af';
			} */

/* select j.u_id from share_post s, job_post j where j.job_id=s.post_id and s.post_id='J1608200300' and s.to_id='U1059506762'

Core Java,Json Parser,Xml
select  w.u_id from wrk_deatail w,employer_info e,share_post s where e.cmpny_name=w.org_name and e.u_id='U1608191228' and w.u_id=s.to_id and s.to_id='U1059506762' and s.post_id='J1608200300'


select l.full_name,l.pro_pic from wrk_deatail w,employer_info e,login l where e.cmpny_name=w.org_name and e.u_id='U1608191228' and w.u_id=l.u_id

select j.skill from job_post j,share_post s where s.post_id=j.job_id and s.post_id='J1608200300' and s.to_id='U1059506762'


select s.skill from share_post p,skill_tag s where s.u_id=p.to_id and p.to_id='U1059506762' and p.post_id='J1608200300'

 */

	//$cmny_id="select j.u_id from share_post s, job_post j where j.job_id=s.post_id and s.post_id='J1608200300' and s.to_id='U1059506762'";
	$res=mysql_query("select j.u_id from share_post s, job_post j where 
	j.job_id=s.post_id and s.post_id='J1608200300' and s.to_id='U1059506762'");
	$row=mysql_fetch_row($res);
	
	
	$level='1';
	if($level=='2'){
		
		$chk=select_query("select  w.u_id from wrk_deatail w,employer_info e,share_post s
		where e.cmpny_name=w.org_name and e.u_id='".$row[0]."' and w.u_id=s.to_id and 
		s.to_id='U1059506766' and s.post_id='J1608200300'");
			$cnt=count($chk);
			
			if($cnt!=''){
				$response['message']='same company';
				$response['success']=1;
				echo json_encode($response);
			}else{
				$sql="select j.skill from job_post j,share_post s where s.post_id=j.job_id
				and s.post_id='J1608200300' and s.to_id='U1059506762'";
					$res1=mysql_query($sql);
					$row=mysql_fetch_row($res1);
					$getSkill=array_map('trim',explode(',', $row[0]));
					$len=count($getSkill);
					$match=round($len/2);
					
				$sql1=select_query("select s.skill from share_post p,skill_tag s where s.u_id=p.to_id 
				and p.to_id='U1059506762' and p.post_id='J1608200300'");
				
				
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
					
					if($match<=$cnt){
						$response['refer']=array();
						$sql=select_query("select l.full_name,l.pro_pic from wrk_deatail w
						,employer_info e,login l where e.cmpny_name=w.org_name and 
						e.u_id='U1608191228' and w.u_id=l.u_id");
							for($i=0;$i<count($sql);$i++){
								array_push($response['refer'],$sql[$i]);
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