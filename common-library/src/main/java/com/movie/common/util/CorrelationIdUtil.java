package com.movie.common.util;

import java.util.UUID;

public final class CorrelationIdUtil {

    private CorrelationIdUtil() {
    }

    public static String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

}