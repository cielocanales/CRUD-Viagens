<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
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
					<h3>Empresas</h3>
				</div>

				<div class="col-md-6">
					<div class="input-group h2">
						<input name="data[search]" class="form-control" id="search" type="text" placeholder="Pesquisar empresas">
						<span class="input-group-btn">
							<button class="btn btn-danger" type="submit">
								<span class="glyphicon glyphicon-search"></span>
							</button>
						</span>
					</div>
				</div>

				<div class="col-md-3">
					<a href="${pageContext.request.contextPath}/empresas/form" class="btn btn-danger pull-right h2"><span class="glyphicon glyphicon-plus"></span>&nbsp;Adicionar Empresa</a>
				</div>
			</div>

			<hr />

			<div id="list" class="row">
				<div class="table-responsive col-md-12">
					<table class="table table-striped table-hover" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
								<th>Nome</th>
								<th>Tipo</th>
								<th>Editar</th>
								<th>Excluir</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="empresa" items="${empresas}">
								<tr>
									<td>${empresa.nome}</td>
									<td>${empresa.tipo eq 'AEREA' ? 'Aérea' : 'Ônibus'}</td>
									<td class="actions">
										<a class="btn btn-info btn-xs"
										   href="${pageContext.request.contextPath}/empresas/update?empresaId=${empresa.id}">
										   <span class="glyphicon glyphicon-edit"></span>
										</a>
									</td>
									<td class="actions">
										<a class="btn btn-danger btn-xs modal-remove"
										   data-id="${empresa.id}"
										   data-name="${empresa.nome}" data-toggle="modal"
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
					$(".modal-body #hiddenValue").text("a empresa '" + name + "'");
					$("#id").val(id);
					$("#form").attr("action", "${pageContext.request.contextPath}/empresas/delete");
				});
			});
		</script>
	</body>
</html>
