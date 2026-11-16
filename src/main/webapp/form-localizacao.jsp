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
			<h3 class="page-header">${not empty localizacao ? 'Alterar' : 'Adicionar'} Localização</h3>

			<form action="${pageContext.request.contextPath}/localizacoes/${action}" method="POST">
				<input type="hidden" value="${localizacao.id}" name="localizacaoId">
				<div class="row">
					<div class="form-group col-md-8">
						<label for="cidade">Cidade</label>
						<input type="text" class="form-control" id="cidade" name="cidade" placeholder="Ex.: Machado" required autofocus="autofocus" value="${localizacao.cidade}">
					</div>
					<div class="form-group col-md-4">
						<label for="estado">Estado (UF)</label>
						<input type="text" class="form-control" id="estado" name="estado" placeholder="Ex.: MG" required maxlength="2" pattern="[A-Za-z]{2}" title="Informe a sigla do estado com 2 letras" value="${localizacao.estado}">
					</div>
				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/localizacoes" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty localizacao ? 'Alterar' : 'Cadastrar'} Localização</button>
					</div>
				</div>
			</form>
		</div>

		<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	</body>
</html>
