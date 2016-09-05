<head>
<meta http-equiv = "content-Type" content = "text/html" charset = "utf-8">
</head> <!-- 한글 지원(DB는 utf-8_unicode_ci로 만들어주세요)을 위한 소스 -->
<?
$connect = mysql_connect("127.0.0.1", "badcode", "1234");
mysql_selectdb("o2");
mysql_query("set names utf8"); //위와 마찬가지로 utf8을 지원하기 위한 소스

$beacon_id = $_REQUEST['beacon_id'];
$qry = "select * from LOOKS_INFO_TB where beacon_id = '$beacon_id';";
$result = mysql_query($qry);

$xmlcode = "<?xml version = \"1.0\" encoding = \"utf-8\"?>\n";

while($obj = mysql_fetch_object($result))
{
	$look_id = $obj->look_id;
	$look_image = $obj->look_image;

	$xmlcode .= "<node>\n"; //xml에서 구분하기 쉽도록 node로 구분
	$xmlcode .= "<look_id>$look_id</look_id>\n";
	$xmlcode .= "<look_image>$look_image</look_image>\n";
	$xmlcode .= "</node>\n";
}

$dir = "C:/APM_Setup/htdocs"; //파일이 있을 디렉토리
$filename = $dir."/src/look_search_result.xml"; //파일 이름

file_put_contents($filename, $xmlcode);
?>