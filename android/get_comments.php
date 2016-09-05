<?php 
require('config.php');
if(isset($_POST['j_id'])){
	
	$sql=select_query("SELECT l.full_name,l.pro_pic,c.commnts,c.cmt_date FROM `comments` 
	c,login l WHERE l.u_id=c.u_id and c.j_id='".$_POST['j_id']."' order by c.id asc");
	
	$count=count($sql);
	$response['comments']=array();
		if($count==""){
			$response["message"]="No Data";
			$response["success"]=0;
			echo json_encode($response);
		}else{
			for($i=0;$i<$count;$i++){
				array_push($response["comments"],$sql[$i]);
			}
			$response['success']=1;
			echo json_encode($response); 
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