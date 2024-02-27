package org.penzgtu.Application.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil<T> {

    private String pathJson = null;
    private ObjectMapper objectMapper;

    private ResourceReader configReader;

    @Getter
    private List<T> models;

    public JsonUtil() {
        this.objectMapper = new ObjectMapper();
        this.models = new ArrayList<>();
        this.configReader = new ResourceReader("config.properties");
    }

    private void initPath() throws IOException {
        if (pathJson == null) {
            pathJson = configReader.loadFile().getProperty("json.path");
        }
    }
    public void jsonToList(Class<T> valueType) throws IOException {
        initPath();
        this.models = objectMapper.readValue(new ResourceReader(this.pathJson).getStream().readAllBytes(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, valueType));
    }

}
