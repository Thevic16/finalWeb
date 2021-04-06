package edu.pucmm.eict.models;

import io.javalin.core.security.Role;

public enum MyRole implements Role {
    DEFAULT, ADMIN, POLLSTER;
}
