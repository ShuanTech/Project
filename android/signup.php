<?php
require('config.php');

if(isset($_POST['name']) && isset($_POST['email']) && isset($_POST['phno']) && isset($_POST['pass']) &&
	isset($_POST['cnfrmpass']) && $_POST['level']){ 
		
		$mail=select_query("select * from login where email_id='".$_POST['email']."'");
		$mcnt=count($mail);
		if($mcnt!=''){
			$response['msg']='Email Id Exist';
			$response['success']=2;
			echo json_encode($response);
		}else{
			$ph=select_query("select * from  login where ph_no='".$_POST['phno']."'");
			$pcnt=count($ph);
			if($pcnt!=''){
				$response['msg']='Phone Number Exist';
				$response['success']=3;
				echo json_encode($response);
			}else{
					$t = microtime(true);
					$micro = sprintf("%06d",($t - floor($t)) * 1000000);
					$d = new DateTime( date('Y-m-d H:i:s.'.$micro, $t) );
					$uId="U".$d->format("Hisu");
				$ins="insert into login(u_id,full_name,email_id,ph_no,passwrd,c_pass,level,deleted)
				values('".$uId."','".$_POST['name']."','".$_POST['email']."','".$_POST['phno']."','".$_POST['pass']."','".$_POST['cnfrmpass']."','".$_POST['level']."',0)";
				$inss="insert into usr_info(u_id,full_name,email_id,ph_no) values('".$uId."','".$_POST['name']."','".$_POST['email']."','".$_POST['phno']."')";
					$ress=mysql_query($inss);
				$result=mysql_query($ins);
				if($result>0){
					if(!file_exists('../photos')){
						mkdir('../photos',0777,true);
						mkdir('../photos/'.$uId,0777,true);
						mkdir('../photos/'.$uId.'/pro_pic',0777,true);
						mkdir('../photos/'.$uId.'/cover_pic',0777,true);
						mkdir('../photos/'.$uId.'/gallery',0777,true);
					}else{
						if(!file_exists('../photos/'.$uId)){
							mkdir('../photos/'.$uId,0777,true);
							mkdir('../photos/'.$uId.'/pro_pic',0777,true);
							mkdir('../photos/'.$uId.'/cover_pic',0777,true);
							mkdir('../photos/'.$uId.'/gallery',0777,true);
						}
					}
					$response['user']=array();
					$usr=array();
					$usr['u_id']=$uId;
					array_push($response['user'],$usr);
					$response['success']=1;
					echo json_encode($response);
				}else{
					$response['msg']='Not Inserted';
					$response['success']=0;
					echo json_encode($response);
				}
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