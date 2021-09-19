package skillter.immersivedamage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import skillter.immersivedamage.communication.IPInfo;
import skillter.immersivedamage.communication.ReceivedPacketsHandler;
import skillter.immersivedamage.communication.UDPManager;
import skillter.immersivedamage.communication.client.UDPClient;
import skillter.immersivedamage.communication.packet.DamagePacket;
import skillter.immersivedamage.config.ConfigDefault;
import skillter.immersivedamage.gui.Popup;
import skillter.immersivedamage.gui.PopupInput;

public class MainActivity extends AppCompatActivity {


    public static SharedPreferences config = null;

    public static MainActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;

        // Set up config
        config = getApplicationContext().getSharedPreferences("config", MODE_PRIVATE);

        // Run UDP Client
        UDPManager.udpClient = new UDPClient();

        // Start up GUI
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Register the Toggle Switch event
        Switch toggleSwitch = (Switch) findViewById(R.id.toggle_switch);
        if (UDPManager.udpServer != null && UDPManager.udpServer.close == false)
            toggleSwitch.setChecked(true);
        else
            toggleSwitch.setChecked(false);

        toggleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked == true) {
                if (UDPManager.udpServer == null || (UDPManager.udpServer != null && UDPManager.udpServer.close == true) || (UDPManager.udpServer != null && UDPManager.udpServer.socket.isClosed() == true)) {
                    int port = config.getInt("port", ConfigDefault.PORT);
                    Toast.makeText(MainActivity.instance.getApplicationContext(), "Starting server on port: " + port, Toast.LENGTH_SHORT).show();
                    UDPManager.runUDPServerAsync(port);
                }
            } else {
                UDPManager.stopUDPServer();
            }


        });

        // Register the Test button event
        Button testButton = (Button) findViewById(R.id.test);
        testButton.setOnClickListener((button) -> {
            //UDPManager.udpClient.sendPacketAsync("localhost", config.getInt("port", ConfigDefault.PORT), new DamagePacket(500, 100));
            UDPManager.udpClient.sendPacketAsync("localhost", config.getInt("port", ConfigDefault.PORT), new DamagePacket(500, 100));
        });

        // Show current IPv4 of the device
        TextView ipv4 = (TextView) findViewById(R.id.ipv4_desc);
        ipv4.setText(getResources().getString(R.string.ipv4_desc) + " " + IPInfo.getIPAddress(true));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.port_menu) {
            PopupInput.changePort(); // Only ports 1025-65535 allowed
            return true;
        } else if (id == R.id.about_menu) {
            Popup.showAbout();
        }

        return super.onOptionsItemSelected(item);
    }

}