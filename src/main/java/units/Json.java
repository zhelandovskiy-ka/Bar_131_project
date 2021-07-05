package units;

import bar.Component;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class Json {
    public static List<Component> getComponentsFromJson(String json) {
        Type componentListType = new TypeToken<List<Component>>(){}.getType();

        return new Gson().fromJson(json, componentListType);
    }
}
