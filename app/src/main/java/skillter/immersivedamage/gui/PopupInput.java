package skillter.immersivedamage.gui;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import skillter.immersivedamage.MainActivity;
import skillter.immersivedamage.communication.UDPManager;
import skillter.immersivedamage.config.ConfigDefault;
import skillter.immersivedamage.util.Multithreading;

public class PopupInput {

    public static void changePort() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.instance);
        builder.setTitle("Change the port to:");

        final EditText input = new EditText(MainActivity.instance);

        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setText(String.valueOf(MainActivity.config.getInt("port", ConfigDefault.PORT)));
        builder.setView(input);


        // Handle popup buttons
        builder.setPositiveButton("Okay", (dialog, which) -> {
            int newPort;
            boolean stopSecondToast = false;
            try {
                newPort = Integer.valueOf(input.getText().toString());
            } catch (NumberFormatException | NullPointerException ex) {
                newPort = ConfigDefault.PORT;
                Toast.makeText(MainActivity.instance.getApplicationContext(), "Resetting port to the default value " + newPort, Toast.LENGTH_LONG).show();
                stopSecondToast = true;
            }
            if (newPort < 1025 || newPort > 65535) {
                Toast.makeText(MainActivity.instance.getApplicationContext(), "Error: Only ports from 1025 to 65535 are allowed", Toast.LENGTH_LONG).show();
                return;
            }
            if (UDPManager.udpServer != null && UDPManager.udpServer.socket != null && !UDPManager.udpServer.close && !UDPManager.udpServer.socket.isClosed()) {
                if (stopSecondToast == false) Toast.makeText(MainActivity.instance.getApplicationContext(), "Restarting server on port: " + newPort, Toast.LENGTH_SHORT).show();
            }
            final int finalPort = newPort;
            // Code above has to be outside of Async, because Toasts need to be in Sync or smth I donno
            Multithreading.runAsync(() -> {
                if (UDPManager.udpServer != null && UDPManager.udpServer.socket != null && !UDPManager.udpServer.close && !UDPManager.udpServer.socket.isClosed()) {
                    UDPManager.stopUDPServer();
                    UDPManager.runUDPServerAsync(finalPort);
                }
                SharedPreferences.Editor editor = MainActivity.config.edit();
                editor.putInt("port", finalPort);
                editor.commit();
            });
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
