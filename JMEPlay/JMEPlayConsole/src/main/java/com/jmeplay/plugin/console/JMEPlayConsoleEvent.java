/*
 * Copyright (c) 2017, 2018, VP-BYTE (http://www.vp-byte.de/) and/or its affiliates. All rights reserved.
 */
package com.jmeplay.plugin.console;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * Events for JMEPlayConsole
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class JMEPlayConsoleEvent extends Event {

    public static final EventType<JMEPlayConsoleEvent> CLOSE_CONSOLE = new EventType<>("CLOSE_CONSOLE");
    public static final EventType<JMEPlayConsoleEvent> UPDATE_TOOLBAR_BUTTONS = new EventType<>("UPDATE_TOOLBAR_BUTTONS");

    JMEPlayConsoleEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }

}
