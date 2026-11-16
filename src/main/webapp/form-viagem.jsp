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
			<h3 class="page-header">${not empty viagem ? 'Alterar' : 'Adicionar'} Viagem</h3>

			<form action="${pageContext.request.contextPath}/viagens/${action}" method="POST">
				<input type="hidden" value="${viagem.id}" name="viagemId">
				<div class="row">
					<div class="form-group col-md-4">
						<label for="empresaId">Empresa</label>
						<select id="empresaId" class="form-control" name="empresaId" required>
							<option value="" disabled ${empty viagem ? 'selected' : ''}>Selecione a empresa</option>
							<c:forEach var="e" items="${empresas}">
								<option value="${e.id}" ${viagem.empresaId == e.id ? 'selected' : ''}>${e.nome}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group col-md-4">
						<label for="origemId">Origem</label>
						<select id="origemId" class="form-control" name="origemId" required>
							<option value="" disabled ${empty viagem ? 'selected' : ''}>Selecione a origem</option>
							<c:forEach var="l" items="${localizacoes}">
								<option value="${l.id}" ${viagem.origemId == l.id ? 'selected' : ''}>${l.cidade} - ${l.estado}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group col-md-4">
						<label for="destinoId">Destino</label>
						<select id="destinoId" class="form-control" name="destinoId" required>
							<option value="" disabled ${empty viagem ? 'selected' : ''}>Selecione o destino</option>
							<c:forEach var="l" items="${localizacoes}">
								<option value="${l.id}" ${viagem.destinoId == l.id ? 'selected' : ''}>${l.cidade} - ${l.estado}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group col-md-4">
						<label for="dataSaida">Data e hora de saída</label>
						<input type="datetime-local" class="form-control" id="dataSaida" name="dataSaida" placeholder="" required  value="${viagem.dataSaida}">
					</div>
					<div class="form-group col-md-4">
						<label for="precoBase">Preço base (R$)</label>
						<input type="number" class="form-control" id="precoBase" name="precoBase" placeholder="0,00" required min="0" step="0.01" value="${viagem.precoBase}">
					</div>
					<div class="form-group col-md-4">
						<label for="assentosTotais">Assentos totais</label>
						<input type="number" class="form-control" id="assentosTotais" name="assentosTotais" placeholder="Ex.: 44" required min="1" value="${viagem.assentosTotais}">
					</div>
				</div>
				<hr />
				<div id="actions" class="row pull-right">
					<div class="col-md-12">
						<a href="${pageContext.request.contextPath}/viagens" class="btn btn-default">Cancelar</a>
						<button type="submit" class="btn btn-primary">${not empty viagem ? 'Alterar' : 'Cadastrar'} Viagem</button>
					</div>
				</div>
			</form>
		</div>

		<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	</body>
</html>
