package org.joget.gcoe;

import java.util.Map;
import org.joget.apps.app.service.AppPluginUtil;
import org.joget.apps.datalist.model.DataList;
import org.joget.apps.datalist.model.DataListBinder;
import org.joget.apps.datalist.model.DataListBinderDefault;
import org.joget.apps.datalist.model.DataListCollection;
import org.joget.apps.datalist.model.DataListColumn;
import org.joget.apps.datalist.model.DataListFilterQueryObject;

public class GcoeDatalistBinder extends DataListBinderDefault {
    protected DataListBinder plugin = null;

    protected DataListBinder getPlugin() {
        if (plugin == null) {
            plugin = (DataListBinder) GcoeUtil.getPlugin(getProperties(), getPropertyString("url"), getPropertyString("key"), getPropertyString("cid"));
        }
        return plugin;
    }
    
    @Override
    public String getName() {
        return "GovernedListDataStore";
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
        return GcoeUtil.getPropertiesOptions(getClassName(), "org.joget.apps.datalist.model.DataListBinder");
    }

    @Override
    public DataListColumn[] getColumns() {
        if (getPlugin() != null) {
            return getPlugin().getColumns();
        }
        return null;
    }

    @Override
    public String getPrimaryKeyColumnName() {
        if (getPlugin() != null) {
            return getPlugin().getPrimaryKeyColumnName();
        }
        return null;
    }

    @Override
    public DataListCollection getData(DataList arg0, Map arg1, DataListFilterQueryObject[] arg2, String arg3, Boolean arg4, Integer arg5, Integer arg6) {
        if (getPlugin() != null) {
            return getPlugin().getData(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
        }
        return null;
    }

    @Override
    public int getDataTotalRowCount(DataList arg0, Map arg1, DataListFilterQueryObject[] arg2) {
        if (getPlugin() != null) {
            return getPlugin().getDataTotalRowCount(arg0, arg1, arg2);
        }
        return 0;
    }
}
