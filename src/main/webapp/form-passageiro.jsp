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
			<h3 class="page-header">${not empty passageiro ? 'Alterar' : 'Adicionar'} Passageiro</h3>

			<form action="${pageContext.request.contextPath}/passageiros/${action}" method="POST">
				<input type="hidden" value="${passageiro.id}" name="passageiroId">
				<div class="row">
					<div class="form-group col-md-6">
						<label for="nome">Nome</label>
						<input type="text" class="form-control" id="nome" name="nome" placeholder="Nome do passageiro" required autofocus="autofocus" value="${passageiro.nome}">
					</div>
					<div class="form-group col-md-6">
						<label for="cpf">CPF</label>
						<input type="text" class="form-control" id="cpf" name="cpf" placeholder="Somente números" required maxlength="11" pattern="[0-9]{11}" title="Informe os 11 dígitos do CPF, sem pontos ou traço" value="${passageiro.cpf}">
					</div>
					<div class="form-group col-md-6">
						<label for="email">E-mail</label>
						<input type="email" class="form-control" id="email" name="email" placeholder="E-mail do passageiro" required  value="${passageiro.email}">
					</div>
					<div class="form-group col-md-6">
						<label for="senha">Senha</label>
						<input type="password" class="form-control" id="senha" name="senha" placeholder="${not empty passageiro ? 'Deixe em branco para manter a senha atual' : 'Senha'}" ${empty passageiro ? 'required' : ''}>
					</div>
				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/passageiros" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty passageiro ? 'Alterar' : 'Cadastrar'} Passageiro</button>
					</div>
				</div>
			</form>
		</div>

		<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	</body>
</html>
