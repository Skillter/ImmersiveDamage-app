package skillter.immersivedamage.communication.packet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class MessagePacket extends Packet implements Serializable {

    public String message;

    public MessagePacket(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("message", message);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return jsonObject.toString();
    }

}
