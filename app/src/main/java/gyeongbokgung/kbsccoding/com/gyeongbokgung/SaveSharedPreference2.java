package gyeongbokgung.kbsccoding.com.gyeongbokgung;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference2 {
    static String numTutorial;

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // numTutorial

    public static void setNumTutorial(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(numTutorial, DBHandler.numTutorial);
        editor.commit();
    }

    public static int getNumTutorial(Context ctx)
    {
        return Integer.parseInt(getSharedPreferences(ctx).getString(numTutorial, "0"));
    }
}
