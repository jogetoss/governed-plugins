package org.joget.gcoe;

import org.joget.apps.app.service.AppPluginUtil;
import org.joget.apps.form.model.Element;
import org.joget.apps.form.model.FormBinder;
import org.joget.apps.form.model.FormData;
import org.joget.apps.form.model.FormDeleteBinder;
import org.joget.apps.form.model.FormRowSet;
import org.joget.apps.form.model.FormStoreBinder;
import org.joget.apps.form.model.FormStoreElementBinder;
import org.joget.apps.form.model.FormStoreMultiRowElementBinder;

public class GcoeFormStoreBinder extends FormBinder implements FormStoreBinder, FormStoreElementBinder, FormDeleteBinder, FormStoreMultiRowElementBinder {
    protected FormStoreBinder plugin = null;

    protected FormStoreBinder getPlugin() {
        if (plugin == null) {
            plugin = (FormStoreBinder) GcoeUtil.getPlugin(getProperties(), getPropertyString("url"), getPropertyString("key"), getPropertyString("cid"));
        }
        return plugin;
    }
    
    @Override
    public String getName() {
        return "GovernedFormSaveDataStore";
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
        return GcoeUtil.getPropertiesOptions(getClassName(), "org.joget.apps.form.model.FormStoreBinder");
    }

    @Override
    public FormRowSet store(Element arg0, FormRowSet arg1, FormData arg2) {
        if (getPlugin() != null) {
            return getPlugin().store(arg0, arg1, arg2);
        }
        return null;
    }

    @Override
    public void delete(Element arg0, FormRowSet arg1, FormData arg2, boolean arg3, boolean arg4, boolean arg5, boolean arg6) {
        if (getPlugin() != null && getPlugin() instanceof FormDeleteBinder) {
            ((FormDeleteBinder) getPlugin()).delete(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
        }
    }
}
