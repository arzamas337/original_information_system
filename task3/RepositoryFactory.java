package org.example.model.factory;

import org.example.model.adapter.MyClientDbAdapter;
import org.example.model.repository.*;

public class RepositoryFactory {

    public static MyClientRep create(DataSourceType type) {
        return switch (type) {
            case JSON -> new MyClientRepJson();
            case YAML -> new MyClientRepYaml();
            case DB -> new MyClientDbAdapter(new MyClientRepDb());
        };
    }
}
