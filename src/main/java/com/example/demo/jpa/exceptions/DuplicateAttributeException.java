package com.example.demo.jpa.exceptions;

import lombok.Getter;

public class DuplicateAttributeException extends RuntimeException {
    public DuplicateAttributeException(String attribute) {
        super(attribute);
    }

}
