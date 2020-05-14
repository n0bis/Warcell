
import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
 
import javax.inject.Inject;
 
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerMethod;
import org.ops4j.pax.swissbox.tracker.ServiceLookup;
import org.osgi.framework.BundleContext;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerMethod.class)
public class SetupTest {
    
    @Inject
    protected BundleContext bundleContext;
 
    @Configuration
    public Option[] config() {
 
        return options(
            /*mavenBundle()
                .groupId("warcell.build")
                .artifactId("OSGiCore")
                .versionAsInProject(),*/
            junitBundles()
        );
    }
    
    @Test
    public void tester() {
        assertEquals(1, 1);
    }
 
    /*@Test
    public void getHelloService() {
        Object service = ServiceLookup.getService(bundleContext,
            "gethay");
        assertThat(service, is(notNullValue()));
    }*/
    
}
