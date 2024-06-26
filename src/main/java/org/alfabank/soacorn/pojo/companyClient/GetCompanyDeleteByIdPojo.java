package org.alfabank.soacorn.pojo.companyClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCompanyDeleteByIdPojo {
    public int id;
    public boolean isActive;
    public Date createDateTime;
    public Date lastChangedDateTime;
    public String name;
    public String description;
    public Object deletedAt;
}
