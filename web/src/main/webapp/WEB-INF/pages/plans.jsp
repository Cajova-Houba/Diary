<%@ include file="/common/taglibs.jsp"%>
 
<head>
    <title><fmt:message key="planList.title"/></title>
    
</head>

<!--  heading -->
<h2><fmt:message key="planList.heading"/></h2>

<!-- buttons for switching between diary main page, plans a nd daily records
	this button group will be on every diary related page. 
-->
<div id="switch" class="btn-group">
	<!-- diary main page -->
	<a href='<c:url value="/diary"/>' class="btn btn-primary">
		<i class="icon-plus icon-white" ></i>
		<fmt:message key="button.diaryMain"/>
	</a>
	
	<!-- plans -->
	<a href='<c:url value="/plans"/>' class="btn btn-primary">
		<i class="icon-plus icon-white" ></i>
		<fmt:message key="button.diaryPlans"/>
	</a>
	
	<!-- daily records -->
	<a href='<c:url value="/dr"/>' class="btn btn-primary">
		<i class="icon-plus icon-white" ></i>
		<fmt:message key="button.diaryDR"/>
	</a>
</div>	

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
		var table = document.getElementById("planList");
		
		//get tbody of table
	    var tbody = table.getElementsByTagName("tbody")[0];
	    
		//get all rows of tbody
		var rows = tbody.getElementsByTagName("tr");
		
		//find the row with id=rowID
		for (var i = 0; i < rows.length; i++) {
			if (rows[i].getAttribute("id") == rowID) {
				//redirect to plan detail form
				//as a parameter the id of a plan will be send
				//there MUST be a validation in controller that logged member can access plan with this ID
				
				//the substring is used to separate the word 'row' from rowID so I can get the plan ID
				planID = rowID.substring(3,rowID.length);
				
				//create POST request on /plandetail
				post("/plan/detail",{pID: planID},"get");
				
				break;
			}
		}
		
	    
	}
</script>
<!-- content - table with displayed plans -->
<display:table name="planList" id="planList" requestURI="" class="table table-condensed table-striped table-hover" export="true"
			   decorator="com.diary.webapp.decorator.PlanRowDecorator">
	<display:column property="ID" sortable="true" titleKey="plan.id" />
	<display:column property="name" sortable="true" titleKey="plan.name"/>
	<display:column property="fromDate" sortable="true" titleKey="plan.fromDate" format="{0,date,yyyy-MM-dd}"/>
</display:table>

<!-- button for adding new plan -->
<div id="actions" class="btn-group">
    <a href='<c:url value="/plan/add"/>' class="btn btn-primary">
        <i class="icon-plus icon-white"></i> <fmt:message key="button.add"/></a>
</div>



<!-- old form for aading new plan DONT USE IT -->
<!--<form:form commandName="plan" method="post" action="addplan" id="planForm">
	
	<div class="well">
		<form:input cssClass="" path="name" id="name" maxlength="20"/>
		<input type="date" name="fromDate" id="fromDate" />
		<!-- <form:input path="fromDate" id="fromDate" maxlength="20"/> 
	</div>
	
	<!-- button for adding a new plan
	<div class="form-group">
		<button type="submit" class="btn btn-primary" id="add" name="add">
			<i class="icon-plus icon-white"></i> <fmt:message key="button.add"/>
		</button>
	</div>	
</form:form> -->