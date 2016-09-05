<?php
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['level'])){
	$response['info']=array();
	if($_POST['level']=='1'){
			$sql=select_query("select full_name,pro_pic,cover_pic from login where u_id='".$_POST['u_id']."'");
			$cnt=count($sql);
			if($cnt==''){
				$response['msg']='No data found';
				$response['success']=0;
				echo json_encode($response);
			}else{
				for($i=0;$i<$cnt;$i++){
					array_push($response['info'],$sql[$i]);
				}
				$response['success']=1;
				echo json_encode($response); 
			}
		}else if($_POST['level']=='2'){
			$sql=select_query("select l.full_name,l.pro_pic,l.cover_pic,w.position as level,concat(w.org_name,', ',w.location) locate from login l,wrk_deatail w where l.u_id=w.u_id and l.u_id='".$_POST['u_id']."' and w.to_date='present'");
			
			$cnt=count($sql);
			if($cnt==''){
				$response['msg']='No data found';
				$response['success']=0;
				echo json_encode($response);
			}else{
				for($i=0;$i<$cnt;$i++){
					array_push($response['info'],$sql[$i]);
				}
				$response['success']=1;
				echo json_encode($response); 
			}
		}else{
			$sql=select_query("select l.full_name,l.pro_pic,l.cover_pic,e.c_type as level,e.landmark as locate from login l,employer_info e where l.u_id=e.u_id and l.u_id='".$_POST['u_id']."'");
			
			$cnt=count($sql);
			if($cnt==''){
				$response['msg']='No data found';
				$response['success']=0;
				echo json_encode($response);
			}else{
				for($i=0;$i<$cnt;$i++){
					array_push($response['info'],$sql[$i]);
				}
				$response['success']=1;
				echo json_encode($response); 
			}
		}
	
	
	
	/* $data=array();
	$sql="SELECT concentration,ins_name FROM education WHERE u_id='".$_POST['u_id']."' order by level limit 1";
		$res=mysql_query($sql);
		
		
				$row=mysql_fetch_row($res);
					
				if($row==0){
					$data['course']='';
					$data['ins_name']='';
					
					$connect="select married_status,city from usr_info where u_id='".$_POST['u_id']."'";
							$rst=mysql_query($connect);
							
							if(mysql_fetch_row($rst)==0){
								$data['status']='';
								$data['city']='';
							}else{
								while($row1=mysql_fetch_row($rst)){
									$data['status']=$row1[0];
									$data['city']=$row1[1];
								}
							}
								
						array_push($response['info'],$data);
				}else{
					$data['course']=$row[0];
					$data['ins_name']=$row[1];
			
					$connect="select married_status,city from usr_info where u_id='".$_POST['u_id']."'";
						$rst=mysql_query($connect);
						
						if(mysql_fetch_row($rst)==0){
							$data['status']='';
							$data['city']='';
						}else{
							while($row1=mysql_fetch_row($rst)){
								$data['status']=$row1[0];
								$data['city']=$row1[1];
							}
						}
							
					array_push($response['info'],$data);
				}	
			$response['success']=1;
			echo json_encode($response); */	
 }else{
	$response['message']="NO Data Post";
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