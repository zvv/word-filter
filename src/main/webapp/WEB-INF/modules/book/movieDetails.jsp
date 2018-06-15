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
			<!-- <div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="javascript:void(0)"
					onclick="searchMovie('国产')">国内电影</a>
			</div> -->
			<div id="navbar" class="navbar-collapse collapse">
				 <form class="navbar-form navbar-right"  id="search1" action="indexSearch" method="post">
					<div class="form-group">
						<input type="text" id="keyWord" name="keyWord"
							placeholder="电影名 主演" class="form-control">
					</div>
					<button type="submit" class="btn btn-success">search</button>
				</form>
			</div>
			<!--/.navbar-collapse -->
		</div>
	</nav>
	<div class="container">
		<!-- Example row of columns -->
		<h3></h3>
		</br>
		<table class="table table-striped">
			<tr>
				<td>海报</td>
				<td>名称</td>
				<td>评分</td>
				<td>更新时间</td>
			</tr>
				<tr>
					<td width="10%"><img src="${img}" height="100" width="100"></td>
					<td width="20%">${movie_name}</td>
					<td width="5%">${socre}</td>
					<td width="10%">${update_time}</td>
				<tr />
		</table>
		<br>
		<h4>下载地址</h4>
		<c:forEach items="${urlList}" var="li">
			<h5><a href="${li}">${li}</a></h5>
		</c:forEach>
		<br>
		<h4>欢迎分享</h4>
        <div class="bdsharebuttonbox" data-tag="share_1">
			<a class="bds_mshare" data-cmd="mshare"></a> <a class="bds_qzone"
				data-cmd="qzone" href="#"></a> <a class="bds_tsina" data-cmd="tsina"></a>
			<a class="bds_baidu" data-cmd="baidu"></a> <a class="bds_renren"
				data-cmd="renren"></a> <a class="bds_tqq" data-cmd="tqq"></a> 
			 <a class="bds_weixin" data-cmd="weixin"></a> 
			<a
				class="bds_more" data-cmd="more">更多</a> <a class="bds_count"
				data-cmd="count"></a>
		</div>
		<h4>下载教程</h4>
		<p>1、打卡迅雷，点击添加任务</p>
		<p>2、把下载URL粘贴到任务里面点击下载</p>
		<p>3、资源抓取来着互联网</p>
		<img src="http://www.17lookmovie.com/imager/jc.png" height="500" width="700">
		
		<br><br><br><br><br><br><br><br><br><br><br><br><br>
		<p>Warning! 本站纯属免费资源欣赏网站,所有资源均收集于互联网,如有侵犯版权或犯规请来信告知,我们将立即更正。联系邮箱2470394828@qq.com 另,欢迎站长联系互加友情链接!
		  </p>
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

<script>
	window._bd_share_config = {
		common : {
			bdText : '${movie_name}',	
			bdDesc : '内有下载链接,创建迅雷任务下载',	
			bdUrl : 'http://www.17lookmovie.com/search/movieDetails?id=${id}', 	
			bdPic : '${img}'
		},
		share : [{
			"bdSize" : 16
		}],
		slide : [{	   
			bdImg : 0,
			bdPos : "right",
			bdTop : 100
		}],
		image : [{
			viewType : 'list',
			viewPos : 'top',
			viewColor : 'black',
			viewSize : '16',
			viewList : ['qzone','tsina','huaban','tqq','renren']
		}],
		selectShare : [{
			"bdselectMiniList" : ['qzone','tqq','kaixin001','bdxc','tqf']
		}]
	}
	with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src='http://bdimg.share.baidu.com/static/api/js/share.js?cdnversion='+~(-new Date()/36e5)];
</script>
</html>