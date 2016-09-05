<?php
class Admin
{
	function login_usr()
	{
		$var1="SELECT * FROM `login` WHERE `level`='1'";
		 $var2=mysql_query($var1);
		return $var2;
	}
	function emp_freshr()
	{
		$va="SELECT u.`u_id`,u.`full_name`,u.`dob`,u.`gender`,u.`address`,u.`city`,u.`district`,u.`state`,u.`country`,u.`pincode`,u.`email_id`,u.`ph_no`,u.`father_name`,u.`mother_name`,u.`blood_grp`,u.`married_status`,u.`language`,u.`hobbies`,u.`phy_chlnge`,u.`id_proof`,l.`u_id`, l.`level` FROM `usr_info` u, `login` l  WHERE u.`u_id`=l.`u_id` AND l.`level`='1'";
		 $var2=mysql_query($va);
		return $var2;
	}
	function fopy($uname,$pass)
	{
		$rtop = mysql_query("SELECT * FROM `admin_login` WHERE `u_name`='$uname' AND `password`='$pass'");
		return $rtop;
	}
	function sele($uname)

	{
		$var1="SELECT * FROM `admin_login` WHERE `uname`='$usernamw'";
		//echo $var1;
		$var2=mysql_query($var1);
		return $var2;
	}

}
?>