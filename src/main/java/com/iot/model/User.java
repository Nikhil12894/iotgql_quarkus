package com.iot.model;

import io.smallrye.common.constraint.NotNull;

public class User {

    @NotNull
    public String userId;

    @NotNull
    public String firstName;

    @NotNull
    public String lastName;

    @NotNull
    public String password;

    @NotNull
    public String email;

}
