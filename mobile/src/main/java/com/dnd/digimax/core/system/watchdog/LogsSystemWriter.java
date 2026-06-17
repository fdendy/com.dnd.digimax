package com.dnd.digimax.core.system.watchdog;

import android.util.Log;

public class LogsSystemWriter {
    public void systemWriter(String strTag, String strMsg){
        Log.e("DNDLOG["+strTag+"]", strMsg);
    }

}