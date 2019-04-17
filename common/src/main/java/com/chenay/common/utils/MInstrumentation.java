package com.chenay.common.utils;

import android.app.Instrumentation;
import android.os.SystemClock;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;

public class MInstrumentation extends Instrumentation {
    @Override
    public void sendStringSync(String text) {
//        super.sendStringSync(text);

        if (text == null) {
            return;
        }
        KeyCharacterMap keyCharacterMap = KeyCharacterMap.load(KeyCharacterMap.VIRTUAL_KEYBOARD);

        final char[] chars = text.toCharArray();
        KeyEvent[] events = keyCharacterMap.getEvents(chars);

        if (events != null) {
            for (int i = 0; i < events.length; i++) {
                // We have to change the time of an event before injecting it because
                // all KeyEvents returned by KeyCharacterMap.getEvents() have the same
                // time stamp and the system rejects too old events. Hence, it is
                // possible for an event to become stale before it is injected if it
                // takes too long to inject the preceding ones.
                sendKeySync(KeyEvent.changeTimeRepeat(events[i], SystemClock.uptimeMillis(), 0));
//                sendKeySync(events[i]);
            }
        }
    }
}
