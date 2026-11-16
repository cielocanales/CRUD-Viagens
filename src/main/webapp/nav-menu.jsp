<jsp:directive.page contentType="text/html; charset=UTF-8" />
<nav id="menu" class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}">
				<strong>Passagens</strong>
			</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${pageContext.request.contextPath}"><span class="glyphicon glyphicon-home"></span><strong>&nbsp;Início</strong></a></li>
				<li><a href="${pageContext.request.contextPath}/passageiros"><span class="glyphicon glyphicon-user"></span><strong>&nbsp;Passageiros</strong></a></li>
				<li><a href="${pageContext.request.contextPath}/empresas"><span class="glyphicon glyphicon-briefcase"></span><strong>&nbsp;Empresas</strong></a></li>
				<li><a href="${pageContext.request.contextPath}/localizacoes"><span class="glyphicon glyphicon-map-marker"></span><strong>&nbsp;Localizações</strong></a></li>
				<li><a href="${pageContext.request.contextPath}/viagens"><span class="glyphicon glyphicon-road"></span><strong>&nbsp;Viagens</strong></a></li>
				<li><a href="${pageContext.request.contextPath}/bilhetes"><span class="glyphicon glyphicon-tag"></span><strong>&nbsp;Bilhetes</strong></a></li>
			</ul>
		</div>
	</div>
</nav>
<br /><br /><br />
