package org.joget.gcoe;

import java.util.ArrayList;
import java.util.Collection;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    public final static String MESSAGE_PATH = "messages/gcoe";
    protected Collection<ServiceRegistration> registrationList;

    public void start(BundleContext context) {
        registrationList = new ArrayList<ServiceRegistration>();

        //Register plugin here
        registrationList.add(context.registerService(GcoeWebSupport.class.getName(), new GcoeWebSupport(), null));
        registrationList.add(context.registerService(GcoeDatalistBinder.class.getName(), new GcoeDatalistBinder(), null));
        registrationList.add(context.registerService(GcoeFormLoadBinder.class.getName(), new GcoeFormLoadBinder(), null));
        registrationList.add(context.registerService(GcoeFormOptionsBinder.class.getName(), new GcoeFormOptionsBinder(), null));
        registrationList.add(context.registerService(GcoeFormStoreBinder.class.getName(), new GcoeFormStoreBinder(), null));
        registrationList.add(context.registerService(GcoeProcessTool.class.getName(), new GcoeProcessTool(), null));
    }

    public void stop(BundleContext context) {
        for (ServiceRegistration registration : registrationList) {
            registration.unregister();
        }
    }
}