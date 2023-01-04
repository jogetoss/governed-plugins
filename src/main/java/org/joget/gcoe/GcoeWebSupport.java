package org.joget.gcoe;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joget.apps.app.service.AppPluginUtil;
import org.joget.commons.util.SecurityUtil;
import org.joget.plugin.base.ExtDefaultPlugin;
import org.joget.plugin.base.PluginWebSupport;
import org.joget.workflow.model.service.WorkflowUserManager;
import org.joget.workflow.util.WorkflowUtil;

public class GcoeWebSupport extends ExtDefaultPlugin implements PluginWebSupport {

    @Override
    public String getName() {
        return "GovernedWebSupport";
    }

    @Override
    public String getVersion() {
        return "7.0-SNAPSHOT";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public void webService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isAdmin = WorkflowUtil.isCurrentUserInRole(WorkflowUserManager.ROLE_ADMIN);
        if (!isAdmin) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        String url = request.getParameter("url");
        String key = request.getParameter("key");
        String type = SecurityUtil.validateStringInput(request.getParameter("type"));
        
        response.getWriter().write(GcoeUtil.getOptions(url, key, type));
    }
}
