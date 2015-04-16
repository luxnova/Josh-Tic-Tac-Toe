package applicotest.android.com.android_tic_tac_toe.Utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

/**
 * Created by JoshuaWilliams on 4/16/15.
 */
public class Util {

    private static final String LOG_TAG = "Utilities";
    public static Context context;

    /**
     * Method that allows simple fonts to be set just by passing the font. It checks to see if the font has the proper format,
     * if it does not, it will return the default font and log error.
     *
     * @param font - the font that needs to be applied.
     * @return Typeface - the new typeface created from the passed font.
     */
    public static Typeface getTypfaceText(String font)
    {
        Log.i(LOG_TAG, "GET TYPE FACE - " + font);
        if(!font.substring(font.length() - 4, font.length()).equals(".ttf"))
        {
            Log.e(LOG_TAG, "GetTypeFace - Your font does not contain the proper '.ttf' suffix.");
            return Typeface.DEFAULT;
        }
        return Typeface.createFromAsset(context.getAssets(), "fonts/" + font);
    }
}
