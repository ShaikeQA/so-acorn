package org.alfabank.soacorn.pojo.authController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginResponse {
    public String userToken;
    public String role;
    public String displayName;
    public String login;
}
