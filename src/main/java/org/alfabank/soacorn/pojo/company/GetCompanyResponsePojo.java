package org.alfabank.soacorn.pojo.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCompanyResponsePojo {

    public int id;
    public boolean isActive;
    public Date createDateTime;
    public Date lastChangedDateTime;
    public String name;
    public String description;
    public Date deletedAt;
}

