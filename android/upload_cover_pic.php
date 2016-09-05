<?php
$response=array();
$response['data']=array();
$ans=array();
require('config.php');
if(isset($_FILES['uploaded_file']['name'])){
	
	$fileName=preg_replace('/\\.[^.\\s]{3,4}$/', '',$_FILES["uploaded_file"]["name"]);
	
	try{
		if(!file_exists('../photos')){
			mkdir('../photos',0777,true);
			mkdir('../photos/'.$fileName,0777,true);
			mkdir('../photos/'.$fileName.'/pro_pic',0777,true);
			mkdir('../photos/'.$fileName.'/cover_pic',0777,true);
			mkdir('../photos/'.$fileName.'/gallery',0777,true);
		}else{
			if(!file_exists('../photos/'.$fileName)){
				mkdir('../photos/'.$fileName,0777,true);
				mkdir('../photos/'.$fileName.'/cover_pic',0777,true);
			}else if(!file_exists('../photos/'.$fileName.'/cover_pic')){
				mkdir('../photos/'.$fileName.'/cover_pic',0777,true);
			}
		}
	}catch(Exception $e){}
	
	$path='../photos/'.$fileName.'/cover_pic/';
	$l_path='photos/'.$fileName.'/cover_pic/';
	$fName=date('ymdhi').'.jpg';
	$file_path = $path . $fName;
		if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $file_path)) 
		{
			$link="http://www.udyomitra.com/".$l_path.$fName;
			$sql="select cover_pic from login where u_id='".$fileName."'";
			$res=mysql_query($sql);
			$val=mysql_fetch_row($res);
			if($val[0]==null){
				$ins="update login set cover_pic='".$link."' where u_id='".$fileName."'";
				$res=mysql_query($ins);
				
				if($res>0){
					$ans['data']=$link;
					array_push($response['data'],$ans);
					
					$response['success']=1;
					echo json_encode($response);
				}else{
					$response['message']="Not Updated";
					$response['success']=0;
					echo json_encode($response);
				}
				
			}else{
				
				$sql1="select u_id from user_imgs where u_id='".$fileName."'";
				$res=mysql_query($sql1);
				$val1=mysql_fetch_row($res);
				
				if($val1[0]==null){
					$ins="insert into user_imgs(u_id,cover_photo) values('".$fileName."','".$val[0]."')";
					$res=mysql_query($ins);
					/* if($res>0){
						$response['message']="Updated";
						$response['success']=1;
						echo json_encode($response);
					}else{
						$response['message']="Not Updated";
						$response['success']=0;
						echo json_encode($response);
					} */
				}else{
					$data=$val[0].','.$link;
					$upt="update user_imgs set cover_photo='".$data."' where u_id='".$fileName."'";
					$res=mysql_query($upt);
					/* if($res>0){
						$response['message']="Updated";
						$response['success']=1;
						echo json_encode($response);
					}else{
						$response['message']="Not Updated";
						$response['success']=0;
						echo json_encode($response);
					} */
				}
				
				$ins="update login set cover_pic='".$link."' where u_id='".$fileName."'";
				$res=mysql_query($ins);
				
				if($res>0){
					$ans['data']=$link;
					array_push($response['data'],$ans);
					$response['success']=1;
					echo json_encode($response);
				}else{
					$response['message']="Not Updated";
					$response['success']=0;
					echo json_encode($response);
				}
				
			}
		}
	
}else{
	$response['message']="NO Data Post";
	$response['success']=0;
	echo json_encode($response);
} 
?>