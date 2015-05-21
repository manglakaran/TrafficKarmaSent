<?php

//include "connect_db.php";
function curl($url) {
	$ch = curl_init();  // Initialising cURL
	curl_setopt($ch, CURLOPT_URL, $url);    // Setting cURL's URL option with the $url variable passed into the function
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, TRUE); // Setting cURL's option to return the webpage data
	$data = curl_exec($ch); // Executing the cURL request and assigning the returned data to the $data variable
	curl_close($ch);    // Closing cURL
	return $data;   // Returning the data from the function
}
// Defining the basic scraping function
function scrape_between($data, $start, $end){
	$data = stristr($data, $start); // Stripping all data from before $start
	$data = substr($data, strlen($start));  // Stripping $start
	$stop = stripos($data, $end);   // Getting the position of the $end of the data to scrape
	$data = substr($data, 0, $stop);    // Stripping all data from after and including the $end of the data to scrape
	return $data;   // Returning the scraped data from the function
}
$var= curl("https://aprescott.com/posts/hangouts-emoji");
$count = 0;
while($var!="")
{	//if($count > 2)
	//	break ;	
        $str = scrape_between($var, "<code>", "</code>");
		echo $str."\n";
		//$count+=1;
	//	if($count % 2 == 0 )
	//	{	echo "\n"; }
        //$show = scrape_between($str, '>', '<');
//	if($show != ""){}
		//echo $show.",";
//	else
//		$count += 1;
//	if($count == 1){
		//echo "android,";
//		$show = "android";
//		$count += 1;
//}	
    //    $str = scrape_between($var, "7 days", "/a>");
    //  $hits = scrape_between($str, '>', '<');
		
//	if($show != "" && $hits != "")	
//		$query = "INSERT INTO records (Hits, Tech) VALUES ('" . $hits  . "','". $show . ',' . "')";	
//		if(mysql_query($query))
//			echo "added successfully"."\n";
//		else
//			echo "select unique id";	
//	else{
        	$pos = stripos($var, $str."</code>");
        	$var = substr($var, $pos + strlen($str."</code>"));
//	} 
		
}

?>
