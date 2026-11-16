<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@include file="base-head.jsp"%>
	</head>
	<body>
		<%@include file="nav-menu.jsp"%>

		<div id="container" class="container-fluid">
			<h3 class="page-header">${not empty empresa ? 'Alterar' : 'Adicionar'} Empresa</h3>

			<form action="${pageContext.request.contextPath}/empresas/${action}" method="POST">
				<input type="hidden" value="${empresa.id}" name="empresaId">
				<div class="row">
					<div class="form-group col-md-6">
						<label for="nome">Nome</label>
						<input type="text" class="form-control" id="nome" name="nome" placeholder="Nome da empresa" required autofocus="autofocus" value="${empresa.nome}">
					</div>
					<div class="form-group col-md-6">
						<label for="tipo">Tipo</label>
						<select id="tipo" class="form-control" name="tipo" required>
							<option value="" disabled ${empty empresa ? 'selected' : ''}>Selecione o tipo</option>
							<option value="ONIBUS" ${empresa.tipo == 'ONIBUS' ? 'selected' : ''}>Ônibus</option>
							<option value="AEREA" ${empresa.tipo == 'AEREA' ? 'selected' : ''}>Aérea</option>
						</select>
					</div>
				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/empresas" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty empresa ? 'Alterar' : 'Cadastrar'} Empresa</button>
					</div>
				</div>
			</form>
		</div>

		<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	</body>
</html>
