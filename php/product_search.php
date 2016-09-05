<head>
<meta http-equiv = "content-Type" content = "text/html" charset = "utf-8">
</head> <!-- 한글 지원(DB는 utf-8_unicode_ci로 만들어주세요)을 위한 소스 -->
<?
$connect = mysql_connect("127.0.0.1", "badcode", "1234");
mysql_selectdb("o2");
mysql_query("set names utf8"); //위와 마찬가지로 utf8을 지원하기 위한 소스

$look_id = $_REQUEST['look_id'];
$qry1 = "select product_id from LOOKS_PRODUCT_TB where look_id = '$look_id';";
$result1 = mysql_query($qry1);

$xmlcode = "<?xml version = \"1.0\" encoding = \"utf-8\"?>\n";

while($obj = mysql_fetch_object($result1))
{
	$product_id = $obj->product_id;
	
	$qry2 = "select * from PRODUCT_INFO_TB where product_id = '$product_id';";
	$result2 = mysql_query($qry2);
	$row=mysql_fetch_array($result2);
	
	$xmlcode .= "<node>\n"; //xml에서 구분하기 쉽도록 node로 구분
	$xmlcode .= "<product_id>$row[product_id]</product_id>\n";
	$xmlcode .= "<name>$row[name]</name>\n";
	$xmlcode .= "<price>$row[price]</price>\n";
	$xmlcode .= "<comment>$row[comment]</comment>\n";
	$xmlcode .= "<product_image>$row[product_image]</product_image>\n";
	$xmlcode .= "<material>$row[material]</material>\n";	//새로 추가된 정보:구성
	$xmlcode .= "<laundry>$row[laundry]</laundry>\n";	//새로 추가된 정보:세탁 주의사항
	$xmlcode .= "</node>\n";
}

$dir = "C:/APM_Setup/htdocs"; //파일이 있을 디렉토리
$filename = $dir."/src/product_search_result.xml"; //파일 이름

file_put_contents($filename, $xmlcode);
?>