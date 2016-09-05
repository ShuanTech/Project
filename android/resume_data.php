<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['level'])){

	
	$response['resume']=array();
	$data=array();
	
		
		$pro=select_query("select * from profile_summary where u_id='".$_POST['u_id']."'");
		$cnt=count($pro);
		if($cnt==''){
			$data['pro']['summary'][]=array("summary"=>'');
		}else{
			for($i=0;$i<count($pro);$i++){
				$data['pro']['summary'][]=$pro[$i];
			}
		}
		
		$wrk=select_query("select id,org_name,position,location,from_date,to_date from wrk_deatail where u_id='".$_POST['u_id']."'");
		
		$cnt=count($wrk);
		if($cnt==''){
			$data['wrk']['wrk'][]=array("id"=>'');
		}else{
			for($i=0;$i<count($wrk);$i++){
				$data['wrk']['wrk'][]=$wrk[$i];
			}
		}
		
		$wrk_exp=select_query("select * from wrk_experience where u_id='".$_POST['u_id']."'");
		$cnt=count($wrk_exp);
		if($cnt==''){
			$data['wrk_exp']['wrk_exp'][]=array("wrk_exp"=>'');
		}else{
			for($i=0;$i<count($wrk_exp);$i++){
				$data['wrk_exp']['wrk_exp'][]=$wrk_exp[$i];
			}
		}
		
		
		
	
		$edu=select_query("select * from education where u_id='".$_POST['u_id']."' order by level asc");
			
			$cnt=count($edu);
			if($cnt==''){
				$data['edu']['edu'][]=array("id"=>'');
			}else{
				for($i=0;$i<count($edu);$i++){
					$data['edu']['edu'][]=$edu[$i];
				}
			}
		$skill=select_query("select * from skill_tag where u_id='".$_POST['u_id']."' and del=0");
			$cnt=count($skill);
			if($cnt==''){
				$data['skill']['skill'][]=array("id"=>'');
			}else{
				for($i=0;$i<count($skill);$i++){
					$data['skill']['skill'][]=$skill[$i];
				}
			}
		$pjct=select_query("select * from project_detail where u_id='".$_POST['u_id']."'");
			$cnt=count($pjct);
			if($cnt==''){
				$data['project']['project'][]=array("p_title"=>'');
			}else{
				for($i=0;$i<count($pjct);$i++){
					$data['project']['project'][]=$pjct[$i];
				}
			}
		$cert=select_query("select * from ceritification where u_id='".$_POST['u_id']."'");
			$cnt=count($cert);
			if($cnt==''){
				$data['cert']['cert'][]=array("cer_name"=>'');
			}else{
				for($i=0;$i<count($cert);$i++){
					$data['cert']['cert'][]=$cert[$i];
				}
			}
		$ach=select_query("select * from achivmnt where u_id='".$_POST['u_id']."'");
			$cnt=count($ach);
				if($cnt==''){
					$data['achieve']['achieve'][]=array("a_name"=>'');
				}else{
					for($i=0;$i<count($ach);$i++){
						$data['achieve']['achieve'][]=$ach[$i];
					}
				}
		$extra=select_query("select * from extra_curricular where u_id='".$_POST['u_id']."'");
			$cnt=count($extra);
				if($cnt==''){
					$data['extra']['extra'][]=array("extra_curricular"=>'');
				}else{
					for($i=0;$i<count($extra);$i++){
						$data['extra']['extra'][]=$extra[$i];
					}
				}
		$prsnl=select_query("select * from usr_info where u_id='".$_POST['u_id']."'");
			$cnt=count($prsnl);
				if($cnt==''){
					$data['prsnl']['prsnl'][]=array("prsnl"=>'');
				}else{
					for($i=0;$i<count($prsnl);$i++){
						$data['prsnl']['prsnl'][]=$prsnl[$i];
					}
				}
		
	array_push($response['resume'],$data);
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