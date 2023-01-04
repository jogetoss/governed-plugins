package org.joget.gcoe;

import java.util.Map;
import org.joget.apps.app.service.AppPluginUtil;
import org.joget.plugin.base.ApplicationPlugin;
import org.joget.plugin.base.DefaultApplicationPlugin;
import org.joget.plugin.property.model.PropertyEditable;

public class GcoeProcessTool extends DefaultApplicationPlugin {
    protected ApplicationPlugin plugin = null;

    protected ApplicationPlugin getPlugin() {
        if (plugin == null) {
            plugin = (ApplicationPlugin) GcoeUtil.getPlugin(getProperties(), getPropertyString("url"), getPropertyString("key"), getPropertyString("cid"));
        }
        return plugin;
    }
    
    @Override
    public String getName() {
        return "GorvenedProcessTool";
    }

    @Override
    public String getVersion() {
        return "7.0-SNAPSHOT";
    }

    @Override
    public String getDescription() {
        return AppPluginUtil.getMessage(getName() + ".desc", getClassName(), Activator.MESSAGE_PATH);
    }

    @Override
    public String getLabel() {
        return AppPluginUtil.getMessage(getName() + ".label", getClassName(), Activator.MESSAGE_PATH);
    }

    @Override
    public String getClassName() {
        return getClass().getName();
    }

    @Override
    public String getPropertyOptions() {
        return GcoeUtil.getPropertiesOptions(getClassName(), "org.joget.plugin.base.ApplicationPlugin");
    }

    @Override
    public Object execute(Map arg0) {
        if (getPlugin() != null) {
            return getPlugin().execute(((PropertyEditable)getPlugin()).getProperties());
        }   
        return null;
    }
}
