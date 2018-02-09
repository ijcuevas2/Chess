package Chess.tests;

import Chess.Main;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.testng.Assert.*;

/**
 * Created by ismael on 2/8/17.
 */
public class GameTest extends Arquillian {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Chess.internals.Game.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    // @Test
    // public void should_not_greeting(){
    //     Assert.assertEquals("Not yet implemented",
    //             "No");
    // }


}
