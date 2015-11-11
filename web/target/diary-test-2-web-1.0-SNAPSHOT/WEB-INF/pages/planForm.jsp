<%@ include file="/common/taglibs.jsp"%>
 
<head>
    <title><fmt:message key="plan.title"/></title>
</head>

<!-- plan detail form -->
<form:form commandName="plan" method="post" action="/plan/add" cssClass="well" id="planForm">

	<!-- cancel and confirm buttons -->
	<div class="form-group">
            <button type="submit" class="btn btn-primary" id="save" name="save">
                <i class="icon-ok icon-white"></i> <fmt:message key="button.save"/>
            </button>
            <c:if test="${not empty plan.ID}">
                <button type="submit" class="btn btn-danger" id="delete" name="delete">
                    <i class="icon-trash icon-white"></i> <fmt:message key="button.delete"/>
                </button>
            </c:if>
 
            <button type="submit" class="btn btn-default" id="cancel" name="cancel">
                <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
            </button>
        </div>

	<form:hidden path="ID"/>
	
	<spring:bind path="plan.name">
	</spring:bind>
	<appfuse:label key="plan.name" styleClass="control-label"/>
	<form:input path="name" cssClass="form-control" id="name" maxlength="20"/>
	
	<spring:bind path="fromDate">
	</spring:bind>
	<appfuse:label key="plan.fromDate" styleClass="control-label"/>
	<fmt:formatDate value="${plan.fromDate }" var="fromDateFormated" pattern="yyyy-MM-dd"/>
	<form:input path="fromDate" cssClass="form-control" id="fromDate" maxlength="20" value="${fromDateFormated }"/>
	<!--  <input type="date" id="date" class="from-control" /> -->
	
</form:form>