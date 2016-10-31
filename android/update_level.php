<?php 
require('config.php');
if(isset($_POST['u_id'])){
	
	$sql="update login set level=2 where u_id='".$_POST['u_id']."'";
	$res=mysql_query($sql);
	if($res>0){
		$selClg=mysql_query("select ins_name,concentration from education where u_id='".$_POST['u_id']."'");
		$sel=mysql_fetch_row($selClg);
		$seldis=mysql_query("select district from usr_info where u_id='".$_POST['u_id']."'");
		$sell=mysql_fetch_row($seldis);
		$get=select_query("SELECT l.u_id from login l,education ed where l.u_id=ed.u_id and ed.ins_name='".$sel['clgName']."' 
				and ed.concentration='".$sel['course']."' and l.level=1");
			$cnt=count($get);
			if($cnt==0){
				
						$response['message']="Data not found";
						$response['success']=0;
						echo json_encode($response);
				
			}else{
				for($i=0;$i<$cnt;$i++){
					$chk=implode("",$get[$i]);
					$follow=select_query("select * from follower where u_id='".$_POST['u_id']."' and follower='".$chk."'");
					$val=count($follow);					
					if($val==0){
						$ins="insert into following(u_id,following) values('".$chk."','".$_POST['u_id']."')";
						$ins1="insert into follower(u_id,follower) values('".$_POST['u_id']."','".$chk."')";
						$res=mysql_query($ins);
						$res1=mysql_query($ins1);
					}
				}
				
					
			}
			$response['message']="Success";
			$response['success']=1;
			echo json_encode($response);
		
		
		
		
		$response['msg']='Updated';
		$response['success']=1;
		echo json_encode($response);
	}else{
		$response['msg']='Not Updated';
		$response['success']=0;
		echo json_encode($response);
	}
	
}else{
	$response['msg']='No data post';
	$response['success']=0;
	echo json_encode($response);
}
?>