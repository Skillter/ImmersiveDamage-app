package skillter.immersivedamage.communication.packet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DamagePacket extends Packet implements Serializable  {

    public int duration;
    public int strength;

    public DamagePacket(int duration, int strength) {
        this.duration = duration;
        this.strength = strength;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("duration", duration);
            jsonObject.put("strength", strength);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return jsonObject.toString();
    }

}
