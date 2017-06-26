<head>
<meta http-equiv = "content-Type" content = "text/html" charset = "utf-8">
</head> <!-- 한글 지원(DB는 utf-8_unicode_ci로 만들어주세요)을 위한 소스 -->
<?
$connect = mysqli_connect("mysql.hostinger.kr", 'u377782305_user', "bitiotansehen", "u377782305_sql"); //webpageconnect

//connection check
if (!$connect) {
    echo "Error: Unable to connect to MySQL." . PHP_EOL;
    echo "Debugging errno: " . mysqli_connect_errno() . PHP_EOL;
    echo "Debugging error: " . mysqli_connect_error() . PHP_EOL;
    exit;
}

echo "Success: A proper connection to MySQL was made!" . PHP_EOL;
echo "Host information: " . mysqli_get_host_info($connect) . PHP_EOL;

mysqli_query($connect, "set names utf8"); //위와 마찬가지로 utf8을 지원하기

$look_id = $_GET['look_id'];	//look_id 가져오는부분
$qry1 = "select product_id from LOOKS_PRODUCT_TB where look_id = '$look_id';";	//confirm look_id
echo $qry1;

$result1 = mysqli_query($connect, $qry1);	//check the look_id from webpage ++row number

if(!$result1) {
	echo mysqli_errno() . ": " . mysqli_error() . PHP_EOL;
}

printf("row number returned : %d\n", mysqli_num_rows($result1));
$xmlcode = "<?xml version = \"1.0\" encoding = \"utf-8\"?>\n";		//XML declare

while($obj = mysqli_fetch_object($result1))	//++line number
{
	$product_id = $obj->product_id;
	
	$qry2 = "select * from PRODUCT_INFO_TB where product_id = '$product_id';";
	$result2 = mysqli_query($connect, $qry2);
	$row = mysqli_fetch_array($result2, MYSQLI_NUM);
	
	$xmlcode .= "<node>\n"; //xml에서 구분하기 쉽도록 node로 구분
	$xmlcode .= "<product_id>$row[0]</product_id>\n";
	$xmlcode .= "<name>$row[1]</name>\n";
	$xmlcode .= "<price>$row[2]</price>\n";
	$xmlcode .= "<comment>$row[3]</comment>\n";
	$xmlcode .= "<category>$row[4]</category>\n";
	$xmlcode .= "<product_image>$row[5]</product_image>\n";
	$xmlcode .= "<material>$row[6]</material>\n";	//새로 추가된 정보:구성
	$xmlcode .= "<laundry>$row[7]</laundry>\n";	//새로 추가된 정보:세탁 주의사항
	$xmlcode .= "</node>\n";
}

$dir = "/home/u366220461/public_html/"; //파일이 있을 디렉토리
$filename = $dir."product_search_result.xml"; //파일 이름

$valid = file_put_contents($filename, $xmlcode);

//file writing check
if($valid == FALSE)
	echo "file writing failed." . PHP_EOL;
?>
