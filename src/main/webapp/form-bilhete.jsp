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
			<h3 class="page-header">${not empty bilhete ? 'Alterar' : 'Adicionar'} Bilhete</h3>

			<form action="${pageContext.request.contextPath}/bilhetes/${action}" method="POST">
				<input type="hidden" value="${bilhete.id}" name="bilheteId">
				<div class="row">
					<div class="form-group col-md-6">
						<label for="passageiroId">Passageiro</label>
						<select id="passageiroId" class="form-control" name="passageiroId" required>
							<option value="" disabled ${empty bilhete ? 'selected' : ''}>Selecione o passageiro</option>
							<c:forEach var="p" items="${passageiros}">
								<option value="${p.id}" ${bilhete.passageiroId == p.id ? 'selected' : ''}>${p.nome}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group col-md-6">
						<label for="viagemId">Viagem</label>
						<select id="viagemId" class="form-control" name="viagemId" required>
							<option value="" disabled ${empty bilhete ? 'selected' : ''}>Selecione a viagem</option>
							<c:forEach var="v" items="${viagens}">
								<option value="${v.id}" ${bilhete.viagemId == v.id ? 'selected' : ''}>${viagensDescricao[v.id]}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group col-md-6">
						<label for="numeroPoltrona">Número da poltrona</label>
						<input type="number" class="form-control" id="numeroPoltrona" name="numeroPoltrona" placeholder="Ex.: 12" required min="1" value="${bilhete.numeroPoltrona}">
					</div>
					<div class="form-group col-md-6">
						<label for="status">Status do pagamento</label>
						<select id="status" class="form-control" name="status" required>
							<option value="PENDENTE" ${empty bilhete or bilhete.status == 'PENDENTE' ? 'selected' : ''}>Pendente</option>
							<option value="PAGO" ${bilhete.status == 'PAGO' ? 'selected' : ''}>Pago</option>
							<option value="CANCELADO" ${bilhete.status == 'CANCELADO' ? 'selected' : ''}>Cancelado</option>
						</select>
					</div>
				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/bilhetes" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty bilhete ? 'Alterar' : 'Cadastrar'} Bilhete</button>
					</div>
				</div>
			</form>
		</div>

		<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	</body>
</html>
