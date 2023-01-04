package org.joget.gcoe;

import org.joget.apps.app.service.AppPluginUtil;
import org.joget.apps.form.model.Element;
import org.joget.apps.form.model.FormAjaxOptionsBinder;
import org.joget.apps.form.model.FormBinder;
import org.joget.apps.form.model.FormData;
import org.joget.apps.form.model.FormLoadOptionsBinder;
import org.joget.apps.form.model.FormRowSet;

public class GcoeFormOptionsBinder extends FormBinder implements FormLoadOptionsBinder, FormAjaxOptionsBinder {
    protected FormBinder plugin = null;

    protected FormBinder getPlugin() {
        if (plugin == null) {
            plugin = (FormBinder) GcoeUtil.getPlugin(getProperties(), getPropertyString("url"), getPropertyString("key"), getPropertyString("cid"));
        }
        return plugin;
    }
    
    @Override
    public String getName() {
        return "GovernedFormOptionsDataStore";
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
        return GcoeUtil.getPropertiesOptions(getClassName(), "org.joget.apps.form.model.FormLoadOptionsBinder");
    }

    @Override
    public FormRowSet load(Element arg0, String arg1, FormData arg2) {
        if (getPlugin() != null) {
            return ((FormLoadOptionsBinder) getPlugin()).load(arg0, arg1, arg2);
        }
        return null;
    }

    @Override
    public boolean useAjax() {
        if (getPlugin() != null && getPlugin() instanceof FormAjaxOptionsBinder) {
            return ((FormAjaxOptionsBinder) getPlugin()).useAjax();
        }
        return false;
    }

    @Override
    public FormRowSet loadAjaxOptions(String[] arg0) {
        if (getPlugin() != null && getPlugin() instanceof FormAjaxOptionsBinder) {
            return ((FormAjaxOptionsBinder) getPlugin()).loadAjaxOptions(arg0);
        }
        return null;
    }
}