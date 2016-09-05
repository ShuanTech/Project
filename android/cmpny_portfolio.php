<?php 
require('config.php');
if(isset($_POST['u_id'])){

		$sql=select_query("select img,p_title,p_description from project_detail 
		where u_id='".$_POST['u_id']."' and p_stus=1");
			$cnt=count($sql);
			$response['portfolio']=array();
			if($cnt==""){
					$response["message"]="No Data";
					$response["success"]=0;
					
					echo json_encode($response);
			}else{
					for($i=0;$i<$cnt;$i++){
						array_push($response['portfolio'],$sql[$i]);
					}
					
					$response['success']=1;
					echo json_encode($response); 
			} 

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
