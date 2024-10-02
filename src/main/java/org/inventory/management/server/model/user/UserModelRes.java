package org.inventory.management.server.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.inventory.management.server.model.base.BaseModel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModelRes extends BaseModel {
    private String username;
    private Role role;
    private String accessToken;
}
