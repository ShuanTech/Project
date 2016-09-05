<?php 
require('config.php');
if(isset($_POST['jId'])){
	
	$slctCity=select_query("select applied_usr,refer_id,resume from job_applied where job_id='".$_POST['jId']."' and
		shrt=0");
	$cnt=count($slctCity);
	$response['job']=array();
				if($cnt==""){
					$response["message"]="No Data";
					$response["success"]=0;
					echo json_encode($response);
				}else{
					$data=array();
					for($i=0;$i<$cnt;$i++){
						if($slctCity[$i]['refer_id']=='direct'){
							$sql=select_query("select full_name from login 
							where u_id='".$slctCity[$i]['applied_usr']."'");
							$data['applied']=$sql[0]['full_name'];
							$data['refer']='direct';
							$data['applied_usr']=$slctCity[$i]['applied_usr'];
							$data['refer_id']=$slctCity[$i]['refer_id'];
							$data['resume']=$slctCity[$i]['resume'];
						}else{
							$sql=select_query("select l.full_name as applied,n.full_name
							as refer from login l,login n 
							where l.u_id='".$slctCity[$i]['applied_usr']."' and 
							n.u_id='".$slctCity[$i]['refer_id']."'");
							$data['applied']=$sql[0]['applied'];
							$data['refer']=$sql[0]['refer'];
							$data['applied_usr']=$slctCity[$i]['applied_usr'];
							$data['refer_id']=$slctCity[$i]['refer_id'];
							$data['resume']=$slctCity[$i]['resume'];
						}
						
						array_push($response["job"],$data);
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