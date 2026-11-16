<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<meta charset="UTF-8">
		<title>Sistema de Passagens</title>
		<%@include file="base-head.jsp"%>
	</head>
	<body>
		<%@include file="nav-menu.jsp"%>

		<main class="container text-center">
			<h1 class="my-4">Bem-vindo ao Sistema de Passagens</h1>
			<p>
				<a href="${pageContext.request.contextPath}/passageiros" class="btn btn-default btn-lg">Passageiros</a>
				<a href="${pageContext.request.contextPath}/empresas" class="btn btn-default btn-lg">Empresas</a>
				<a href="${pageContext.request.contextPath}/localizacoes" class="btn btn-default btn-lg">Localizações</a>
				<a href="${pageContext.request.contextPath}/viagens" class="btn btn-default btn-lg">Viagens</a>
				<a href="${pageContext.request.contextPath}/bilhetes" class="btn btn-default btn-lg">Bilhetes</a>
			</p>
		</main>
	</body>
</html>
