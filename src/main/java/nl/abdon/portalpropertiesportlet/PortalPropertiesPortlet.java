package nl.abdon.portalpropertiesportlet;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

public class PortalPropertiesPortlet extends MVCPortlet
{

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException
	{
		List<PropertiesFile> propertiesFiles = new ArrayList<PropertiesFile>();

		propertiesFiles.add(new PropertiesFile("portal-ext (LRH)", "portal-ext.properties", true));
		propertiesFiles.add(new PropertiesFile("portal-ext (CLP)", "portal-ext.properties", false));
		propertiesFiles.add(new PropertiesFile("portal-setup-wizard (LRH)", "portal-setup-wizard.properties", true));
		propertiesFiles.add(new PropertiesFile("portal-setup-wizard (CLP)", "portal-setup-wizard.properties", false));
		propertiesFiles.add(new PropertiesFile("portal-developer (LRH)", "portal-developer.properties", true));
		propertiesFiles.add(new PropertiesFile("portal-developer (CLP)", "portal-developer.properties", false));
		propertiesFiles.add(new PropertiesFile("portal-ide (LRH)", "portal-ide.properties", true));
		propertiesFiles.add(new PropertiesFile("portal-ide (CLP)", "portal-ide.properties", false));

		renderRequest.setAttribute("propertiesFiles", propertiesFiles);

		super.doView(renderRequest, renderResponse);
	}

	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException,
			PortletException
	{
		String filename = actionRequest.getParameter("filename");
		String content = actionRequest.getParameter("content");

		try
		{
			Files.write(Paths.get(filename), content.getBytes());
			SessionMessages.add(actionRequest, "success");
		}
		catch (Exception e)
		{
			SessionErrors.add(actionRequest, "error");
		}

		actionResponse.setRenderParameter("selectedTab", actionRequest.getParameter("selectedTab"));

		super.processAction(actionRequest, actionResponse);
	}
}
