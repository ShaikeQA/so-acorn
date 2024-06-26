package org.alfabank.soacorn.pojo.employeeClient;

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
