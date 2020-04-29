package warcell.osgicollision;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import warcell.common.services.IPostEntityProcessingService;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
       System.out.println( "STARTING LAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" );
        context.registerService(IPostEntityProcessingService.class.getName(), new CollisonProcessor(), null);
    }

    public void stop(BundleContext context) throws Exception {
        System.out.println("STOOOOOOP COLLISION");
    }

}
