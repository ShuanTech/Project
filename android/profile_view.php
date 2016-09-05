<?php 
require('config.php');

if(isset($_POST['u_id']) && isset($_POST['level']) && isset($_POST['frm_id'])){
	$response['pro_view']=array();
	$data=array();

		if($_POST['level']=='1'){
			
			$following=select_query("select * from following where u_id='".$_POST['frm_id']."' 
						and following='".$_POST['u_id']."'");
					$cnt=count($following);
					if($cnt==''){
						$data['following']='1';
					}else{
						$data['following']='0';
					}
					
			$follower=select_query("select * from following where u_id='".$_POST['u_id']."' 
						and following='".$_POST['frm_id']."'");
					$cnt=count($follower);
					if($cnt==''){
						$data['follower']='1';
					}else{
						$data['follower']='0';
					}
			
			$info=select_query("select full_name,email_id,ph_no,pro_pic,cover_pic from login where 
			u_id='".$_POST['u_id']."'");
			$cnt=count($info);
			if($cnt==''){
				$data['info']['info'][]=array("full_name"=>'');
			}else{
				for($i=0;$i<count($info);$i++){
					$data['info']['info'][]=$info[$i];
				}
			}
			$cntInfo=select_query("select city,country from usr_info where
			u_id='".$_POST['u_id']."'");
			$cnt=count($cntInfo);
			if($cnt==''){
				$data['cnt']['cnt'][]=array("city"=>'');
			}else{
				for($i=0;$i<count($info);$i++){
					$data['cnt']['cnt'][]=$cntInfo[$i];
				}
			}
			
			$edu=select_query("select concentration,ins_name,location,substr(frm_date,1,4) as frmDat,
			substr(to_date,1,4) as toDat from education where u_id='".$_POST['u_id']."' order by level asc");
			
			$cnt=count($edu);
			if($cnt==''){
				$data['edu']['edu'][]=array("concentration"=>'');
			}else{
				for($i=0;$i<count($edu);$i++){
					$data['edu']['edu'][]=$edu[$i];
				}
			}
			
			$skill=select_query("select skill from skill_tag where u_id='".$_POST['u_id']."' and del=0");
			$cnt=count($skill);
			if($cnt==''){
				$data['skill']['skill'][]=array("skill"=>'');
			}else{
				for($i=0;$i<count($skill);$i++){
					$data['skill']['skill'][]=$skill[$i];
				}
			}
			
			$pjct=select_query("select p_title from project_detail where u_id='".$_POST['u_id']."' and p_stus=0");
			$cnt=count($pjct);
			if($cnt==''){
				$data['project']['project'][]=array("p_title"=>'');
			}else{
				for($i=0;$i<count($pjct);$i++){
					$data['project']['project'][]=$pjct[$i];
				}
			}
			
			
			
		}else if($_POST['level']=='2'){
			
			$following=select_query("select * from following where u_id='".$_POST['frm_id']."' 
						and following='".$_POST['u_id']."'");
					$cnt=count($following);
					if($cnt==''){
						$data['following']='1';
					}else{
						$data['following']='0';
					}
					
			$follower=select_query("select * from following where u_id='".$_POST['u_id']."' 
						and following='".$_POST['frm_id']."'");
					$cnt=count($follower);
					if($cnt==''){
						$data['follower']='1';
					}else{
						$data['follower']='0';
					}
			$info=select_query("select full_name,email_id,ph_no,pro_pic,cover_pic from 
			login where u_id='".$_POST['u_id']."'");
				
				$cnt=count($info);
				if($cnt==''){
					$data['info']['info'][]=array("full_name"=>'');
				}else{
					for($i=0;$i<count($info);$i++){
						$data['info']['info'][]=$info[$i];
					}
				}
				
			$cntInfo=select_query("select org_name,position,location from wrk_deatail 
			where to_date='present' and u_id='".$_POST['u_id']."'");
				$cnt=count($cntInfo);
				if($cnt==''){
					$data['cnt']['cnt'][]=array("org_name"=>'');
				}else{
					for($i=0;$i<count($info);$i++){
						$data['cnt']['cnt'][]=$cntInfo[$i];
					}
				}
				
				
			$wrk=select_query("select org_name,position,location,substr(from_date,1,8) as frmDat,
			substr(to_date,1,8) as toDat from wrk_deatail where u_id='".$_POST['u_id']."'");
		
			$cnt=count($wrk);
			if($cnt==''){
				$data['wrk']['wrk'][]=array("org_name"=>'');
			}else{
				for($i=0;$i<count($wrk);$i++){
					$data['wrk']['wrk'][]=$wrk[$i];
				}
			}
			
			$edu=select_query("select concentration,ins_name,location,substr(frm_date,1,4) as frmDat,
			substr(to_date,1,4) as toDat from education where u_id='".$_POST['u_id']."' order by level asc");
			
			$cnt=count($edu);
			if($cnt==''){
				$data['edu']['edu'][]=array("concentration"=>'');
			}else{
				for($i=0;$i<count($edu);$i++){
					$data['edu']['edu'][]=$edu[$i];
				}
			}
			
			$skill=select_query("select skill from skill_tag where u_id='".$_POST['u_id']."' and del=0");
			$cnt=count($skill);
			if($cnt==''){
				$data['skill']['skill'][]=array("skill"=>'');
			}else{
				for($i=0;$i<count($skill);$i++){
					$data['skill']['skill'][]=$skill[$i];
				}
			}
			
			$pjct=select_query("select p_title from project_detail where u_id='".$_POST['u_id']."' and p_stus=1");
			$cnt=count($pjct);
			if($cnt==''){
				$data['project']['project'][]=array("p_title"=>'');
			}else{
				for($i=0;$i<count($pjct);$i++){
					$data['project']['project'][]=$pjct[$i];
				}
			}
			
		}else{
			
			$sql=select_query("select * from following where 
					u_id='".$_POST['frm_id']."' and following='".$_POST['u_id']."'");
				$cnt=count($sql);
				if($cnt==''){
					$data['follow']='1';
				}else{
					$data['follow']='0';
				}
				
			$info=select_query("select l.pro_pic,l.cover_pic,e.cmpny_name,e.c_type,e.landmark,
				e.country,e.year_of_establish,e.num_wrkers,e.c_desc,e.c_website from 
				login l,employer_info e where l.u_id=e.u_id and l.u_id='".$_POST['u_id']."'");
				$cnt=count($info);
				if($cnt==''){
					$data['info']['info'][]=array("pro_pic"=>'');
				}else{
					for($i=0;$i<count($info);$i++){
						$data['info']['info'][]=$info[$i];
					}
				}
			
		}
	array_push($response['pro_view'],$data);
	$response['success']=1;
	echo json_encode($response);
	
		 
		
}else{
	$response['msg']='No data found';
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