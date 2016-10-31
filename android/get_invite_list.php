<?php 
require('config.php');

if(isset($_POST['u_id'])){
	$response['invite']=array();
	
	$data=array();
	$sql="SELECT l.u_id,l.pro_pic,e.cmpny_name,l.level FROM `notify_access` n,employer_info e, login l 
		WHERE l.u_id=e.u_id and e.u_id=n.frm_id and n.to_id='".$_POST['u_id']."'";
	$result=mysql_query($sql);
	if(mysql_num_rows($result)>0){
			
			while($row=mysql_fetch_array($result)){
				$data['u_id']=$row[0];
				$data['pro_pic']=$row[1];
				$data['level']=$row[3];
				
					$cmpny="select cmpny_name,i_type,city,country from employer_info where u_id='".$row[0]."'";
						$cres=mysql_query($cmpny);
						if(mysql_num_rows($cres)==0){
							$data['full_name']=$row[2];
							$data['sec']='';
						}else{
							$getData=mysql_fetch_array($cres);
							$data['full_name']=$getData[0];
							$data['sec']=$getData[1].' - '.$getData[2].', '.$getData[3];
						}
				
				array_push($response["invite"],$data);
			}
				$response['success']=1;
				echo json_encode($response); 
		}else{
				$response["message"]="No Data";
				$response["success"]=0;
				echo json_encode($response);
		}
	
	
	$sql=select_query("SELECT l.u_id,l.pro_pic,e.cmpny_name,l.level FROM `notify_access` n,employer_info e, login l 
		WHERE l.u_id=e.u_id and e.u_id=n.frm_id and n.to_id='".$_POST['u_id']."'");
		
			$cnt=count($sql);
			if($cnt==''){
				$response["message"]="No Data";
				$response["success"]=0;
				echo json_encode($response);
			}else{
				for($i=0;$i<$cnt;$i++){
					array_push($response["invite"],$sql[$i]);
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