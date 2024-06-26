package org.alfabank.soacorn.pojo.employeeClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PostEmployeeRequestPojo {
    public String firstName;
    public String lastName;
    public String middleName;
    public int companyId;
    public String email;
    public String url;
    public String phone;
    public Date birthdate;
    public boolean isActive;
}
