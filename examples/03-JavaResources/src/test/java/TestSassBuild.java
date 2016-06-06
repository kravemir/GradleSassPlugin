import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TestSassBuild {

    @Test
    public void checkResourceOnClasspath() throws Exception {
        assertNotNull(getClass().getResource("main.css"));
    }
}
