<%@ include file="/common/taglibs.jsp"%>
 
<head>
    <title><fmt:message key="diary.title"/></title>
    <meta name="menu" content="Diary"/>
</head>

<!--  heading -->
<h2><fmt:message key="diary.heading"/></h2>


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

<div>
	<h3>News</h3>
	There may be some news
</div>