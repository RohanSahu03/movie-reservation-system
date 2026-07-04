package com.movie.common.enums;

public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR,
    INVALID_REQUEST,
    RESOURCE_NOT_FOUND,
    BAD_REQUEST,
    UNAUTHORIZED,
    FORBIDDEN,

    // Authentication
    INVALID_CREDENTIALS,
    USER_NOT_FOUND,
    USER_ALREADY_EXISTS,
    INVALID_TOKEN,
    TOKEN_EXPIRED,

    // Movie
    MOVIE_NOT_FOUND,

    // Theatre
    THEATRE_NOT_FOUND,

    // Show
    SHOW_NOT_FOUND,

    // Booking
    BOOKING_NOT_FOUND,
    SEAT_ALREADY_BOOKED,

    // Payment
    PAYMENT_FAILED,
    PAYMENT_NOT_FOUND
}