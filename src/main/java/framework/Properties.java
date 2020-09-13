package framework;

import java.io.InputStream;

public class Properties {

    private void initialize() {
        InputStream resource = getClass().getResourceAsStream("automation.properties");

    }
}
