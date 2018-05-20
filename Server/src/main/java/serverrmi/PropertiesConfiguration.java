package serverrmi;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesConfiguration {
    private static final PropertiesConfiguration instance = new PropertiesConfiguration();
    private static Properties props;

    private PropertiesConfiguration() {
        props = new Properties();
    }

    public static PropertiesConfiguration getInstance() {
        return instance;
    }

    public Properties getProps() {
        try {
            props.load(new FileReader("db.config"));
            return props;
        } catch (IOException e) {
            System.out.println("Err " + e);
            return null;
        }
    }
}
