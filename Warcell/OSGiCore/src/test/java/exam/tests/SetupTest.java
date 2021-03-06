package exam.tests;

import java.io.IOException;

import javax.inject.Inject;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import static org.junit.Assert.assertNotNull;
import static org.ops4j.pax.exam.CoreOptions.cleanCaches;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.vmOption;
import org.osgi.framework.Bundle;

/**
 * @author madsfalken
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class SetupTest {

    @Inject
    protected BundleContext ctx;

    @Configuration
    public Option[] configuration() throws IOException {
        return new Option[]{
            cleanCaches(),
            vmOption("-ea"),
            junitBundles(),
            mavenBundle("org.apache.felix", "org.apache.felix.scr", "2.1.2"),
            mavenBundle("org.apache.felix", "org.apache.felix.configadmin", "1.9.4"),
            mavenBundle("warcell.build", "OSGiLibGDX", "1.0-SNAPSHOT"),
            mavenBundle("warcell.build", "OSGiCore", "1.0-SNAPSHOT")
        };
    }

    @Test
    public void startupWorks() throws BundleException {
        assertNotNull("Bundle context should be applied", ctx);
    }

    @Test
    public void testBundleStarted() {
        final Bundle bundle = getBundle(ctx, "OSGiCore");
        assertNotNull("Expecting OSGiCore bundle to be installed", bundle);
        if (getJavaVersion() <= 8) {
            assertEquals("Expecting OSGiCore bundle to be active", Bundle.INSTALLED, bundle.getState());
        } else {
            assertEquals("Expecting OSGiCore bundle to be active", Bundle.ACTIVE, bundle.getState());
        }
    }

    private Bundle getBundle(BundleContext ctx, String symbolicName) {
        for (Bundle b : ctx.getBundles()) {
            if (symbolicName.equals(b.getSymbolicName())) {
                return b;
            }
        }
        return null;
    }
    
    private static int getJavaVersion() {
        String version = System.getProperty("java.specification.version");
        final int idx = version.indexOf('.');
        if (idx > 0)
                version = version.substring(idx + 1);
        return Integer.parseInt(version); 
    }

}
