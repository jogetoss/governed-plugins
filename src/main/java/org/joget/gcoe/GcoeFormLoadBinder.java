package org.joget.gcoe;

import org.joget.apps.app.service.AppPluginUtil;
import org.joget.apps.form.model.Element;
import org.joget.apps.form.model.FormBinder;
import org.joget.apps.form.model.FormData;
import org.joget.apps.form.model.FormLoadBinder;
import org.joget.apps.form.model.FormLoadElementBinder;
import org.joget.apps.form.model.FormLoadMultiRowElementBinder;
import org.joget.apps.form.model.FormRowSet;

public class GcoeFormLoadBinder extends FormBinder implements FormLoadBinder, FormLoadElementBinder, FormLoadMultiRowElementBinder {
    protected FormLoadBinder plugin = null;

    protected FormLoadBinder getPlugin() {
        if (plugin == null) {
            plugin = (FormLoadBinder) GcoeUtil.getPlugin(getProperties(), getPropertyString("url"), getPropertyString("key"), getPropertyString("cid"));
        }
        return plugin;
    }
    
    @Override
    public String getName() {
        return "GovernedFormLoadDataStore";
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
        return GcoeUtil.getPropertiesOptions(getClassName(), "org.joget.apps.form.model.FormLoadElementBinder");
    }

    @Override
    public FormRowSet load(Element arg0, String arg1, FormData arg2) {
        if (getPlugin() != null) {
            return getPlugin().load(arg0, arg1, arg2);
        }
        return null;
    }
}
