package com.movie.common.logging;

public final class LogConstants {

    private LogConstants() {
    }

    public static final String REQUEST_RECEIVED =
            "Incoming Request : {} {}";

    public static final String RESPONSE_SENT =
            "Outgoing Response : Status={} Duration={} ms";

    public static final String SERVICE_STARTED =
            "{} started successfully";

    public static final String SERVICE_STOPPED =
            "{} stopped";
}