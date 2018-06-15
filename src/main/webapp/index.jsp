<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "https://hm.baidu.com/hm.js?cb23d22e2c35cb51fd14ac0d30ecd04f";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>

<title>敏感词识别及飘红</title>

<!-- Bootstrap core CSS -->
<link href="${dmw}/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="${dmw}/bootstrap/theme/jumbotron.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="${dmw}/bootstrap/assets/js/ie-emulation-modes-warning.js"></script>

</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">敏感词识别</a>
			</div>
			
			<!--/.navbar-collapse -->
		</div>
	</nav>
	<div class="jumbotron">
		<div class="container">
			<form class="form-inline"  id="search2" action="${dmw}/filter/filterWeb"  method="POST">
				<div class="form-group">
					<textarea rows="10" cols="160" id="word" name="word" >${word}</textarea>
				</div>
				<button type="submit" class="btn btn-default">检测</button>
			</form>
		</div>
		<br>
		<br>
		<br>
		<div class="container">
					${result}
		</div>
	</div>

	<!-- /container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="${dmw}/bootstrap/js/jquery-2.1.1.min.js"></script>
	<script src="${dmw}/bootstrap/js/bootstrap.min.js"></script>
	<script src="${dmw}/bootstrap/js/movieList.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script
		src="${dmw}/bootstrap/assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
