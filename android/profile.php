<?php 
require('config.php');

if(isset($_POST['u_id']) && isset($_POST['level'])){
	$response['profile']=array();
	$data=array();

		if($_POST['level']=='1'){
			$info=select_query("select pro_pic,cover_pic,status from login where u_id='".$_POST['u_id']."'");
			$cnt=count($info);
			if($cnt==''){
				$data['info']['info'][]=array("pro_pic"=>'');
			}else{
				for($i=0;$i<count($info);$i++){
					$data['info']['info'][]=$info[$i];
				}
			}
			$secc=select_query("select ins_name,location from education where u_id='".$_POST['u_id']."' order by level asc");
			if(count($secc)==0){
				$secc=select_query("select city,country from usr_info u_id='".$_POST['u_id']."'");
				if(count($secc)==0){
					$data['sec']['sec'][]=array("sec"=>'');
				}else{
					$data['sec']['sec'][]=array("sec"=>$secc[0]['city'].', '.$secc[0]['country']);
				}
			}else{
				$data['sec']['sec'][]=array("sec"=>$secc[0]['ins_name'].', '.$secc[0]['location']);
			}
			
			$cntInfo=select_query("select * from usr_info where u_id='".$_POST['u_id']."'");
			$cnt=count($cntInfo);
			if($cnt==''){
				$data['cnt']['cnt'][]=array("city"=>'');
			}else{
				for($i=0;$i<count($cntInfo);$i++){
					$data['cnt']['cnt'][]=$cntInfo[$i];
				}
			}
			
			$edu=select_query("select concentration,ins_name,location,frm_date as frmDat,to_date as toDat from education where u_id='".$_POST['u_id']."' order by level asc");
			
			$cnt=count($edu);
			if($cnt==''){
				$data['edu']['edu'][]=array("concentration"=>'');
			}else{
				for($i=0;$i<count($edu);$i++){
					$data['edu']['edu'][]=$edu[$i];
				}
			}
			
			$skill=select_query("select lang_known as skill from skill where u_id='".$_POST['u_id']."'");
			$cnt=count($skill);
			if($cnt==''){
				$data['skill']['skill'][]=array("skill"=>'');
			}else{
				$split=explode(",",$skill[0]['skill']);
				for($i=0;$i<count($split);$i++){
					$data['skill']['skill'][]=array("skill"=>$split[$i]);
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
			$info=select_query("select pro_pic,cover_pic,status from login where u_id='".$_POST['u_id']."'");
			$cnt=count($info);
			if($cnt==''){
				$data['info']['info'][]=array("pro_pic"=>'');
			}else{
				for($i=0;$i<count($info);$i++){
					$data['info']['info'][]=$info[$i];
				}
			}
			$secc=select_query("select org_name,position,location from wrk_deatail where u_id='".$_POST['u_id']."' order by id desc limit 1");
			if(count($secc)==0){
				$secc=select_query("select ins_name,location from education where u_id='".$_POST['u_id']."' order by level asc");
				if(count($secc)==0){
					$secc=select_query("select city,country from usr_info u_id='".$_POST['u_id']."'");
					if(count($secc)==0){
						$data['sec']['sec'][]=array("sec"=>'');
					}else{
						$data['sec']['sec'][]=array("sec"=>$secc[0]['city'].', '.$secc[0]['country']);
					}
				}else{
					$data['sec']['sec'][]=array("sec"=>$secc[0]['ins_name'].', '.$secc[0]['location']);
					
				}
			}else{
				$data['sec']['sec'][]=array("sec"=>$secc[0]['position'].' at '.$secc[0]['org_name'].', '.$secc[0]['location']);
			}
			$cntInfo=select_query("select * from usr_info where u_id='".$_POST['u_id']."'");
			$cnt=count($cntInfo);
			if($cnt==''){
				$data['cnt']['cnt'][]=array("city"=>'');
			}else{
				for($i=0;$i<count($cntInfo);$i++){
					$data['cnt']['cnt'][]=$cntInfo[$i];
				}
			}
	
			$wrk=select_query("select org_name,position,location,from_date as frmDat,
			to_date as toDat from wrk_deatail where u_id='".$_POST['u_id']."' order by id desc");
		
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
			
			$skill=select_query("select lang_known as skill from skill where u_id='".$_POST['u_id']."'");
			$cnt=count($skill);
			if($cnt==''){
				$data['skill']['skill'][]=array("skill"=>'');
			}else{
				$split=explode(",",$skill[0]['skill']);
				for($i=0;$i<count($split);$i++){
					$data['skill']['skill'][]=array("skill"=>$split[$i]);
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
			
			$info=select_query("select pro_pic,cover_pic from login where u_id='".$_POST['u_id']."'");
			$cnt=count($info);
			if($cnt==''){
				$data['info']['info'][]=array("pro_pic"=>'');
			}else{
				for($i=0;$i<count($info);$i++){
					$data['info']['info'][]=$info[$i];
				}
			}
			
			$cntInfo=select_query("select * from employer_info where u_id='".$_POST['u_id']."'");
			$cnt=count($cntInfo);
			if($cnt==''){
				$data['cnt']['cnt'][]=array("city"=>'');
			}else{
				for($i=0;$i<count($cntInfo);$i++){
					$data['cnt']['cnt'][]=$cntInfo[$i];
				}
			}
			
			$ser=select_query("select ser_name from services where u_id='".$_POST['u_id']."'");
			
			$cnt=count($ser);
			if($cnt==''){
				$data['ser']['ser'][]=array("ser_name"=>'');
			}else{
				for($i=0;$i<count($ser);$i++){
					$data['ser']['ser'][]=$ser[$i];
				}
			}
			
			$port=select_query("select p_title from project_detail where u_id='".$_POST['u_id']."' and p_stus=1");
			
			$cnt=count($port);
			if($cnt==''){
				$data['port']['port'][]=array("p_title"=>'');
			}else{
				for($i=0;$i<count($port);$i++){
					$data['port']['port'][]=$port[$i];
				}
			}
			
			$job=select_query("select job_id,title from job_post where u_id='".$_POST['u_id']."' and close=0");
			
			$cnt=count($job);
			if($cnt==''){
				$data['job']['job'][]=array("title"=>'');
			}else{
				for($i=0;$i<count($job);$i++){
					$data['job']['job'][]=$job[$i];
				}
			}
			
		}
	array_push($response['profile'],$data);
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