package models.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginBodyModel {
    String email, password;
}