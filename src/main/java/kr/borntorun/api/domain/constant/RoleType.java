package kr.borntorun.api.domain.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    ADMIN("ROLE_ADMIN", "관리자"),
    MANAGER("ROLE_MANAGER", "운영진"),
    MEMBER("ROLE_MEMBER", "크루원"),
    GUEST("ROLE_GUEST", "신규가입자");

    private final String code;
    private final String description;
}
