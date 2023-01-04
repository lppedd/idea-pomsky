package com.github.lppedd.idea.pomsky;

import com.github.lppedd.idea.pomsky.process.PomskyCompileListener;
import com.github.lppedd.idea.pomsky.settings.PomskySettingsListener;
import com.intellij.util.messages.Topic;

/**
 * Topics to which subscribe to be notified of various events.
 *
 * @author Edoardo Luppi
 */
public class PomskyTopics {
  public static final Topic<PomskyCompileListener> TOPIC_COMPILE = Topic.create("Pomsky Compile", PomskyCompileListener.class);
  public static final Topic<PomskySettingsListener> TOPIC_SETTINGS = Topic.create("Pomsky Settings", PomskySettingsListener.class);
}
