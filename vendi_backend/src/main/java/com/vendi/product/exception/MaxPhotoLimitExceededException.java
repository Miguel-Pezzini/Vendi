package com.vendi.product.exception;

public class MaxPhotoLimitExceededException extends RuntimeException {
    public MaxPhotoLimitExceededException(int maxAllowed) {
        super("The maximum number of photos allowed is " + maxAllowed + ".");
    }
}
