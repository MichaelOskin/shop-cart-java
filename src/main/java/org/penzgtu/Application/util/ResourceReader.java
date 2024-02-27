package org.penzgtu.Application.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceReader {
    public String File;
    public ResourceReader(String configFile) {this.File = configFile;}

    public Properties loadFile() throws IOException {
        Properties props = new Properties();
        try (InputStream inputStream = getStream()) {
            if (inputStream == null) {
                throw new IOException("Unable to find " + this.File);
            }
            props.load(inputStream);
        }
        return props;
    }

    public InputStream getStream() {
        return ClassLoader.getSystemResourceAsStream(this.File);
    }
}