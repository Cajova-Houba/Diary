<%@ include file="/common/taglibs.jsp"%>
 
<head>
    <title><fmt:message key="dailyRecords.title"/></title>
</head>

<!--  heading -->
<h2><fmt:message key="dailyRecords.heading"/></h2>


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
		var table = document.getElementById("activities");
		
		//get tbody of table
	    var tbody = table.getElementsByTagName("tbody")[0];
	    
		//get all rows of tbody
		var rows = tbody.getElementsByTagName("tr");
		
		//find the row with id=rowID
		for (var i = 0; i < rows.length; i++) {
			if (rows[i].getAttribute("id") == rowID) {
				//redirect to activity detail form
				//as a parameter the id of a activity will be send
				//there MUST be a validation in controller that logged member can access activity with this ID
				
				//the substring is used to separate the word 'row' from rowID so I can get the activity ID
				actID = rowID.substring(3,rowID.length);
				
				//create POST request on /activity/detail
				post("/activity/detail",{aID: actID},"get");
				
				break;
			}
		}
		
	    
	}
</script>
<!-- display the daily record -->
<display:table name="activities" id="activities" class="table table-condensed table-striped table-hover" requestURI="" 
			   decorator="com.diary.webapp.decorator.ActivityRowDecorator">
	<display:caption>${dr.getDate().getDate()}. ${dr.getDate().getMonth()}. ${dr.getDate().getYear() + 1900}</display:caption>
	<display:column property="name" sortable="true" />
	<display:column property="actType" sortable="true" />
	<display:column property="value" sortable="true" />
	<display:column property="unit" sortable="true" />
	<display:column href="/activity/delete" paramId="activityID" paramProperty="ID" value="delete"/>
</display:table>

<!-- form for adding new activity -->
<form:form commandName="activity" method="post" action="activity/add" id="activityForm">
	
	<input type="hidden" id="id" name="id" value="${dr.getID() }" />
	
	<div class="well">
		<form:input cssClass="" path="name" id="name" maxlength="20"/>
		<form:select path="actType" id="actType">
			<form:options items="${activityType }"/>
		</form:select>
		<form:input cssClass="" path="value" id="value" maxlength="20"/>
		<form:select path="unit" id="unit">
			<form:options items="${activityUnit }"/>
		</form:select>
	</div>
	
	<!-- button for adding a new activity -->
	<div class="form-group">
		<button type="submit" class="btn btn-primary" id="add" name="add">
			<i class="icon-plus icon-white"></i> <fmt:message key="button.add"/>
		</button>
	</div>	
</form:form>

