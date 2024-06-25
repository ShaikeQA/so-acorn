package org.alfabank.soacorn.pojo.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostEmployeErrorResponsePojo {
    public String statusCode;
    public String message;
}
