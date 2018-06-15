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

<title>movie show</title>

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
				<a class="navbar-brand" href="#">一起看电影</a>
			</div>
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="javascript:void(0)"  onclick="searchMovie('国产')">国内电影</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				 <form class="navbar-form navbar-right"  id="search1" action="indexSearch" method="post"
>
					<div class="form-group">
						<input type="text" id="keyWord"  name="keyWord" placeholder="电影名 主演"
							class="form-control">
					</div>
					<button type="submit" class="btn btn-success">search</button>
				</form> 
			</div>
			<!--/.navbar-collapse -->
		</div>
	</nav>
	<div class="jumbotron">
		<div class="container">
			<form class="form-inline"  id="search2" action="indexSearch"  method="POST">
				<div class="form-group">
					<input type="text" id="keyWord" name="keyWord" value="${keyWord}" placeholder="电影名 主演" style="width:500px" class="form-control">
				</div>
				<button type="submit" class="btn btn-default">搜索</button>
			</form>
		</div>
	</div>

	<div class="container">
		<!-- Example row of columns -->
		<h3>万部海量电影信息：搜索出${num}部</h3>
		</br>
		<table class="table table-striped">
			<tr><td>海报</td><td>名称</td><td>评分</td><td>更新时间</td><td>下载地址</td></tr>
			<c:forEach items="${mList}" var="li">
				<tr>
					<td  width="10%"> <a href="movieDetails?id=${li.id}"> <img src="${li.img}" height="100" width="100"></a></td>
					<td width="20%"> <a href="movieDetails?id=${li.id}">${li.movie_name}</a></td>
					<td width="5%">${li.socre}</td>
					<td width="10%">${li.update_time}</td>
					<td  width="40%">
					${fn:split(li.down_url,",")[0]}
                   <a href="movieDetails?id=${li.id}"> 
					更多下载地址
					</a>
					</td>
					
				<tr />
			</c:forEach>
		</table>
		<hr>

		<footer>
			<p>&copy; Company 2014</p>
		</footer>
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
