<?php 
require('config.php');
if(isset($_POST['jId']) && isset($_POST['u_id'])){
	
	$sql="SELECT l.pro_pic,l.cover_pic,e.cmpny_name,e.c_website, j.* FROM 
		login l,employer_info e, job_post j WHERE j.u_id=e.u_id and j.u_id=l.u_id and 
		j.job_id='".$_POST['jId']."'";
		
		$res=mysql_query($sql);
		
		$response['post']=array();
		$data=array();
			$row=mysql_fetch_array($res);
			$data['pro_pic']=$row[0];
			$data['cover_pic']=$row[1];
			$data['cmpny_name']=$row[2];
			$data['c_website']=$row[3];
			$data['u_id']=$row[5];
			$data['title']=$row[7];
			$data['skill']=$row[8];
			$data['type']=$row[9];
			$data['category']=$row[10];
			$data['package']=$row[11];
			$data['level']=$row[12];
			$data['qualification']=$row[13];
			$data['location']=$row[14];
			$data['description']=$row[15];
			$data['viewed']=$row[16];
			$data['shared']=$row[17];
			$data['applied']=$row[18];
			$data['date_created']=$row[19];
			$data['close']=$row[20];
			
			$chk=select_query("select shrt from job_applied where job_id='".$_POST['jId']."' and 
			applied_usr='".$_POST['u_id']."'");
			
			if(count($chk)==0){
				$data['apply']='0';
			}else{
				$data['apply']='1';
			}
		
		$upt="update share_post set status=1 where to_id='".$_POST['u_id']."' and status!=2";
		$res=mysql_query($upt);
		array_push($response["post"],$data);
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