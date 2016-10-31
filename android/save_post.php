<?php 
require('config.php');
if(isset($_POST['u_id']) && isset($_POST['j_id'])){
	
		
		if(count(select_query("select * from share_post where to_id='".$_POST['u_id']."' and post_id='".$_POST['j_id']."' and is_important=1"))!=0){
			$response['msg']='Already in';
			$response['success']=2;
			echo json_encode($response);
		}else{
			$save="update share_post set is_important=1 where to_id='".$_POST['u_id']."' and
			post_id	='".$_POST['j_id']."'";
			$res=mysql_query($save);
			
			if($res>0){
				$response['msg']='Successfully Saved';
				$response['success']=1;
				echo json_encode($response);	
			}else{
				$response['msg']='Not Saved';
				$response['success']=0;
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