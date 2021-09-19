package skillter.immersivedamage.gui;

import android.app.AlertDialog;
import android.view.Gravity;
import android.widget.TextView;

import skillter.immersivedamage.BuildConfig;
import skillter.immersivedamage.ContactInfo;
import skillter.immersivedamage.MainActivity;

public class Popup {

    public static void showAbout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.instance);

        TextView textView = new TextView(MainActivity.instance);
        textView.setTextSize(17);
        textView.setGravity(Gravity.CENTER);
        textView.setText("\nThis app was made by Skillter.\nTo make this app work as intended,\nyou'll need to install the mod on pc,\n which communicates with this app." +
                "\n\nThis app: " + ContactInfo.APP_PROJECT_LINK +
                "\nThe mod: " + ContactInfo.MOD_PROJECT_LINK +
                "\n\n Contact info:" +
                "\n[Discord] " + ContactInfo.DISCORD_USERNAME +
                "\n[Github] " + ContactInfo.GITHUB_ACCOUNT_LINK +
                "\n");

        String version;
        if (BuildConfig.VERSION_NAME != null) {
            version = BuildConfig.VERSION_NAME;
        } else {
            version = ". unknown";
        }
        builder.setTitle(MainActivity.instance.getTitle() + " v" + version);
        builder.setView(textView);

        builder.setNegativeButton("oki doki boomer", (dialog, which) -> {
            dialog.dismiss();
        });


        builder.show();


    }
}
