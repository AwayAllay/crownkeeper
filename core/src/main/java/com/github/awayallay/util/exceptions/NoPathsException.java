package com.github.awayallay.util.exceptions;

/**Thrown if a specific layer does not contain any paths for the npcs to walk on. */
public class NoPathsException extends RuntimeException {

    public NoPathsException(String message) {
        super(message);
    }
}
