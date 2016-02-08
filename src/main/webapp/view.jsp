<%@ include file="init.jsp"%>

<% List<PropertiesFile> propertiesFiles = (List<PropertiesFile>)request.getAttribute("propertiesFiles"); %>

<liferay-ui:tabs 
     names="<%= StringUtil.merge(propertiesFiles) %>"
     param="selectedTab" 
     refresh="false"
>
	<c:forEach items="${propertiesFiles}" var="propertiesFile">
		<liferay-ui:section>
		
			<portlet:actionURL var="actionURL">
				 <portlet:param name="selectedTab" value="${propertiesFile.displayName}" /> 
			</portlet:actionURL>
		
        	<form action="${actionURL}" method="post">
        		
        		<input name="<portlet:namespace />filename" type="hidden" value="${propertiesFile.fullPath}" />
        		
				<c:choose>
					<c:when test="${propertiesFile.existant}">
						<p><b>${propertiesFile.fullPath}</b></p>
					</c:when>
					<c:otherwise>
						<p><i><b>${propertiesFile.filename}</b> does not exist in ${propertiesFile.inLRHome ? "Liferay Home" : "Classpath" }</i></p>
					</c:otherwise>
				</c:choose>
				
				<c:if test="${propertiesFile.existant || propertiesFile.inWriteableDirectory}">
					<liferay-ui:input-textarea 
							param='content' 
							defaultValue='${propertiesFile.content}' />
							
					<c:choose>
						<c:when test="${propertiesFile.editable && propertiesFile.existant}">
							<aui:button type="submit" value="save" />
						</c:when> 
						<c:when test="${propertiesFile.inWriteableDirectory}">				
							<aui:button type="submit" value="create" />
						</c:when>
					</c:choose>
				</c:if>

			</form>
			
			<liferay-ui:success key="success" message="File saved succesfully" />
			<liferay-ui:error key="error" message="An error occured while saving file" />
			
    	</liferay-ui:section>	
	</c:forEach>
</liferay-ui:tabs>