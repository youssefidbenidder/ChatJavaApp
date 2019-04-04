package common;

import org.json.simple.JSONObject;

public abstract class PayloadBody {

    public abstract JSONObject toJSON();

    public abstract PayloadBody fromJSON(JSONObject jsonObj);
}
