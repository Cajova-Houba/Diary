<%@ include file="/common/taglibs.jsp"%>
 
<head>
    <title><fmt:message key="plan.title"/></title>
    
</head>

<!-- display errors if there are any -->
${ParseError}
${AccesDenied}

<!-- plan detail form -->
<form:form commandName="plan" method="post" action="/plan/edit" cssClass="well" id="planForm">

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



<script type="text/javascript">

	function post(path, params, method) {
	    method = method || "post"; // Set method to post by default if not specified.
	
	    // The rest of this code assumes you are not using a library.
	    // It can be made less wordy if you use one.
	    var form = document.createElement("form");
	    form.setAttribute("method", method);
	    form.setAttribute("action", path);
	
	    for(var key in params) {
	        if(params.hasOwnProperty(key)) {
	            var hiddenField = document.createElement("input");
	            hiddenField.setAttribute("type", "hidden");
	            hiddenField.setAttribute("name", key);
	            hiddenField.setAttribute("value", params[key]);
	
	            form.appendChild(hiddenField);
	         }
	    }
	
	    document.body.appendChild(form);
	    form.submit();
	}

	function onRowClick(rowID) {
		//get table
		var table = document.getElementById("goalList");
		
		//get tbody of table
	    var tbody = table.getElementsByTagName("tbody")[0];
	    
		//get all rows of tbody
		var rows = tbody.getElementsByTagName("tr");
		
		//find the row with id=rowID
		for (var i = 0; i < rows.length; i++) {
			if (rows[i].getAttribute("id") == rowID) {
				//redirect to goal detail form
				//as a parameter the id of a goal will be send
				//there MUST be a validation in controller that logged member can access goal with this ID
				
				//the substring is used to separate the word 'row' from rowID so I can get the goal ID
				goalID = rowID.substring(3,rowID.length);
				
				//create POST request on /goal/detail
				post("/goal/detail",{gID: goalID},"get");
				
				break;
			}
		}
		
	    
	}
</script>

<div class="well">
<!-- list of goals -->
<appfuse:label key="goal.title" styleClass="control-label"/>
<display:table name="goalList" class="table table-condensed table-striped table-hover" requestURI=""
              id="goalList" decorator="com.diary.webapp.decorator.GoalRowDecorator">
              <display:column property="ID" titleKey="goal.id"/>
              <display:column property="name" titleKey="goal.name"/>
              <display:column property="actType" titleKey="goal.actType"/>
              <display:column property="value" titleKey="goal.value"/>
              <display:column property="unit" titleKey="goal.actUnit"/>
              <display:column property="progress" titleKey="goal.progress" format="{0,number,##.##%}"/>
              <display:column href="/goal/delete" paramId="goalId" paramProperty="ID" value="delete"/>
</display:table>

<!-- form for adding new goal -->
<form:form commandName="goal" method="post" action="/goal/add" cssClass="well" id="goalForm">

		<input type="hidden" id="planID" name="planID" value="${planID }">
		<form:input cssClass="" path="name" id="goalName" maxlength="20"/>
		<form:select path="actType" id="goalActType">
			<form:options items="${activityType }"/>
		</form:select>
		<form:input cssClass="" path="value" id="goalValue" maxlength="20"/>
		<form:select path="unit" id="goalUnit">
			<form:options items="${activityUnit }"/>
		</form:select> 

		<!-- button for adding new goals -->
	<div class="form-group">
		<button type="submit" class="btn btn-primary" id="addGoal" name="addGoal">
			<i class="icon-plus icon-white"></i> <fmt:message key="button.newGoal"/>
		</button>
	</div>	

</form:form>
</div>