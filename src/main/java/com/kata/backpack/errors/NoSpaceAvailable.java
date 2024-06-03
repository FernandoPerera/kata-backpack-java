package com.kata.backpack.errors;

import com.kata.backpack.common.Error;

public class NoSpaceAvailable extends Error {
    public NoSpaceAvailable(String message) {
        super(message);
    }
}
