package com.kt.userapi.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
public class UserForm {
    @NotNull(message = "Name can not be null.")
    private String userFormName;
    @NotNull(message = "Password can not be null.")
    private String userFormPassword;
    @NotNull
    @Email(message = "Email can not be null.")
    private String userFormEmail;
    @NotNull(message = "Age can not be null.")
    private int userFormAge;
}
