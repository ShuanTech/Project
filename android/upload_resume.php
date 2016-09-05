<?php
 $response=array();
$response['data']=array();
$ans=array();
require('config.php');
if(isset($_FILES['uploaded_file']['name'])){
	
	$fileName=preg_replace('/\\.[^.\\s]{3,4}$/', '',$_FILES["uploaded_file"]["name"]);
	$sv=explode('-',$fileName);
	
	try{
		if(!file_exists('../photos')){
			mkdir('../photos',0777,true);
			mkdir('../photos/'.$sv[0],0777,true);
			mkdir('../photos/'.$sv[0].'/resume',0777,true);
		}else{
			if(!file_exists('../photos/'.$sv[0])){
				mkdir('../photos/'.$sv[0],0777,true);
				mkdir('../photos/'.$sv[0].'/resume',0777,true);
			}else if(!file_exists('../photos/'.$sv[0].'/resume')){
				mkdir('../photos/'.$sv[0].'/resume',0777,true);
			}
		}
	}catch(Exception $e){}
	
	$path='../photos/'.$sv[0].'/resume/';
	$l_path='photos/'.$sv[0].'/resume/';
	$fName=date('ymdhi').'.pdf';
	$file_path = $path . $fName;
		if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file_path)) 
		{
			 $link="http://www.udyomitra.com/".$l_path.$fName;
			//$ans['data']=$link;
			//array_push($response['data'],$ans);
			
			$aName="SELECT l.full_name from login l,share_post s WHERE s.to_id=l.u_id and 
				s.to_id='".$sv[0]."' and s.post_id='".$sv[1]."'";
					$ares=mysql_query($aName);
					$arow=mysql_fetch_row($ares);
			
			$pName="SELECT e.cmpny_name,j.u_id,j.title from job_post j,employer_info e WHERE 
				j.u_id=e.u_id and j.job_id='".$sv[1]."'";
					$pres=mysql_query($pName);
					$prow=mysql_fetch_row($pres);
					
			$cmpnyCotent=$prow[2].' :'.$arow[0].'applied on your post';
			$seniorContent=$arow[0].' : Request to refer the job post of '.$prow[2];
			
			$sql="insert into job_applied(job_id,applied_usr,refer_id,resume) values(
			'".$sv[1]."','".$sv[0]."','direct','".$link."')";
			$res=mysql_query($sql);
			if($res>0){
				$sql1="update job_post set applied=applied+1 where job_id='".$sv[1]."'";
				$sql2="update share_post set status=2 where to_id='".$sv[0]."' and post_id='".$sv[1]."'";
				$sql3="insert into notify_access(frm_id,to_id,post_id,content,type) values(
					'".$sv[0]."','".$prow[1]."','".$sv[1]."','".$cmpnyCotent."',3)";
				
				$res1=mysql_query($sql1);
				$res2=mysql_query($sql2);
				$res3=mysql_query($sql3);
				
				if($sv[2]!='refer'){
					$referId=explode(',',$sv[2]);
					for($i=0;$i<count($referId);$i++){
						$rIns="insert into notify_access(frm_id,to_id,post_id,content,type
						) values('".$sv[0]."','".$referId[$i]."','".$sv[1]."',
						'".$seniorContent."',2)";
							$res=mysql_query($rIns);
							
					}
				}
				
				if($res1>0){
					$response['message']="Upload";
					$response['success']=1;
					echo json_encode($response);
				}else{
					$response['message']="Not Upload";
					$response['success']=0;
					echo json_encode($response);
				}
			}else{
				$response['message']="Not Upload";
				$response['success']=0;
				echo json_encode($response);
			} 
		}else{
			$response['message']="Not Updated";
			$response['success']=0;
			echo json_encode($response);
		}
	
}else{
	$response['message']="NO Data Post";
	$response['success']=0;
	echo json_encode($response);
}  
?>