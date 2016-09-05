<?php
require('config.php');
if(isset($_POST['u_id'])){

$response['employer']=array();
$data=array();

	$skill=select_query("select distinct skill,'skill' as name from skill_tag");
		$cnt=count($skill);
		if($cnt==''){
			$data['skill']['skill'][]=array("skill"=>'');
		}else{
			for($i=0;$i<count($skill);$i++){
				$data['skill']['skill'][]=$skill[$i];
			}
		}
	
	
	$postion=select_query("select distinct position,'pos' as name from wrk_deatail");
		$cnt=count($skill);
		if($cnt==''){
			$data['pos']['pos'][]=array("position"=>'');
		}else{
			for($i=0;$i<count($postion);$i++){
				$data['pos']['pos'][]=$postion[$i];
			}
		}
		
	$loc=select_query("select distinct city,'loc' as name from location");
		$cnt=count($loc);
		if($cnt==''){
			$data['loc']['loc'][]=array("loc"=>'');
		}else{
			for($i=0;$i<count($loc);$i++){
				$data['loc']['loc'][]=$loc[$i];
			}
		}
	
	
	array_push($response['employer'],$data);
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