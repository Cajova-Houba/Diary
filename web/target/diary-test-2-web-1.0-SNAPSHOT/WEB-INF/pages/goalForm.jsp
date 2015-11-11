<%@ include file="/common/taglibs.jsp"%>
 
 <!-- This form is used for editing an existing goal -->
 
<head>
    <title>${goal.name }</title>
</head>

<form:form commandName="goal" method="post" action="/goal/add" cssClass="well" id="goalForm">
	<div class="well">
		<form:hidden path="ID" id="ID"/>
		<form:hidden path="planID" id="planID"/>
		<form:input cssClass="" path="name" id="name" maxlength="20"/>
		<form:select path="actType" id="actType">
			<form:options items="${activityType }"/>
		</form:select>
		<form:input cssClass="" path="value" id="value" maxlength="20"/>
		<form:select path="unit" id="unit">
			<form:options items="${activityUnit }"/>
		</form:select>
	</div>
	
	<!-- button for saving new activity or cancel editing new activity -->
	<div class="form-group">
		<button type="submit" class="btn btn-primary" id="save" name="save">
			<i class="icon-plus icon-white"></i> <fmt:message key="button.save"/>
		</button>
		<button type="submit" class="btn btn-default" id="cancel" name="cancel">
                <i class="icon-remove"></i> <fmt:message key="button.cancel"/>
        </button>
	</div>	
</form:form>