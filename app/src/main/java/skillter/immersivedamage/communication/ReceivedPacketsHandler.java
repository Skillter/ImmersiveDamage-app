package skillter.immersivedamage.communication;

import android.widget.Toast;

import skillter.immersivedamage.MainActivity;
import skillter.immersivedamage.Vibrator;
import skillter.immersivedamage.communication.packet.DamagePacket;
import skillter.immersivedamage.communication.packet.MessagePacket;
import skillter.immersivedamage.communication.packet.Packet;

public class ReceivedPacketsHandler {


    public static void handle(Packet packet) {

        //MainActivity.instance.runOnUiThread(() -> Toast.makeText(MainActivity.instance.getApplicationContext(), "Packet received", Toast.LENGTH_SHORT).show());

        if (packet instanceof DamagePacket) {
            DamagePacket damagePacket = (DamagePacket) packet;
            System.out.println(damagePacket.toString());
            MainActivity.instance.runOnUiThread(() -> Toast.makeText(MainActivity.instance.getApplicationContext(), Vibrator.constrainStrength(damagePacket.strength) + "/255 for " + Vibrator.constrainDuration(damagePacket.duration) + "ms", Toast.LENGTH_SHORT).show());
            try {
                Vibrator.doVibration(damagePacket.duration, damagePacket.strength);
            } catch (UnsupportedOperationException ex) {
                ex.printStackTrace();
            }
        } else if (packet instanceof MessagePacket) {
            MessagePacket messagePacket = (MessagePacket) packet;
            System.out.println("Received message from the MessagePacket: " + messagePacket.message);
        }

    }

}
