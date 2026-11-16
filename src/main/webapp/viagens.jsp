<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@include file="base-head.jsp"%>
	</head>
	<body>
		<%@include file="modal.html"%>
		<%@include file="nav-menu.jsp"%>

		<div id="container" class="container-fluid">
			<div id="alert" style="${not empty message ? 'display: block;' : 'display: none;'}" class="alert alert-dismissable ${alertType eq 1 ? 'alert-success' : 'alert-danger'}">
			  <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
			  ${message}
			</div>

			<div id="top" class="row">
				<div class="col-md-3">
					<h3>Viagens</h3>
				</div>

				<div class="col-md-6">
					<div class="input-group h2">
						<input name="data[search]" class="form-control" id="search" type="text" placeholder="Pesquisar viagens">
						<span class="input-group-btn">
							<button class="btn btn-danger" type="submit">
								<span class="glyphicon glyphicon-search"></span>
							</button>
						</span>
					</div>
				</div>

				<div class="col-md-3">
					<a href="${pageContext.request.contextPath}/viagens/form" class="btn btn-danger pull-right h2"><span class="glyphicon glyphicon-plus"></span>&nbsp;Adicionar Viagem</a>
				</div>
			</div>

			<hr />

			<div id="list" class="row">
				<div class="table-responsive col-md-12">
					<table class="table table-striped table-hover" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<th>Empresa</th>
								<th>Origem</th>
								<th>Destino</th>
								<th>Saída</th>
								<th>Preço base</th>
								<th>Assentos</th>
								<th>Editar</th>
								<th>Excluir</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="viagem" items="${viagens}">
								<tr>
									<td>${empresasNomes[viagem.empresaId]}</td>
									<td>${localizacoesNomes[viagem.origemId]}</td>
									<td>${localizacoesNomes[viagem.destinoId]}</td>
									<td>${fn:replace(viagem.dataSaida, 'T', ' ')}</td>
									<td>R$ <fmt:formatNumber value="${viagem.precoBase}" minFractionDigits="2" maxFractionDigits="2" /></td>
									<td>${viagem.assentosTotais}</td>
									<td class="actions">
										<a class="btn btn-info btn-xs"
										   href="${pageContext.request.contextPath}/viagens/update?viagemId=${viagem.id}">
										   <span class="glyphicon glyphicon-edit"></span>
										</a>
									</td>
									<td class="actions">
										<a class="btn btn-danger btn-xs modal-remove"
										   data-id="${viagem.id}"
										   data-name="${localizacoesNomes[viagem.origemId]} para ${localizacoesNomes[viagem.destinoId]}" data-toggle="modal"
										   data-target="#delete-modal" href="#"><span
										   class="glyphicon glyphicon-trash"></span></a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

			<div id="bottom" class="row">
				<div class="col-md-12">
					<!-- os botões são montados pelo js/table-utils.js -->
					<ul class="pagination"></ul>
				</div>
			</div>
		</div>

		<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/table-utils.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				// ao clicar em excluir, envia o texto, o id e a ação (delete) para o modal
				$(".modal-remove").click(function () {
					var name = $(this).attr('data-name');
					var id = $(this).attr('data-id');
					$(".modal-body #hiddenValue").text("a viagem '" + name + "'");
					$("#id").val(id);
					$("#form").attr("action", "${pageContext.request.contextPath}/viagens/delete");
				});
			});
		</script>
	</body>
</html>
