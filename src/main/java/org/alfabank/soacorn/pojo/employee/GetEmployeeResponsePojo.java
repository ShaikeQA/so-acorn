package org.alfabank.soacorn.pojo.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEmployeeResponsePojo {
    public int id;
    public boolean isActive;
    public Date createDateTime;
    public Date lastChangedDateTime;
    public String firstName;
    public String lastName;
    public String middleName;
    public String phone;
    public String email;
    public String birthdate;
    public String avatar_url;
    public int companyId;
}
