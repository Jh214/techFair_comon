package techfair_comon.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;


public enum Grade {
    QUIT("QUIT", "탈퇴 회원", 0),
    ROLE_SUSPENDED("ROLE_SUSPENDED", "영구 정지", 1),
    ROLE_ANONYMOUS("ROLE_ANONYMOUS", "게스트", 1),
    ROLE_HOT_DEAL_SUSPENDED("ROLE_HOT_DEAL_SUSPENDED", "핫딜 정지", 2),
    ROLE_OPENTALK_SUSPENDED("ROLE_OPENTALK_SUSPENDED", "오픈톡 정지", 2),
    ROLE_MEMBER("ROLE_MEMBER", "유저", 3),
    ROLE_PLANNER("ROLE_PLANNER", "플래너", 4),
    ROLE_GUIDE("ROLE_GUIDE", "가이드", 4),
    ROLE_AGENCY("ROLE_AGENCY", "여행사", 4),
    ROLE_ETC_SELLER("ROLE_ETC_SELLER", "기타 판매자", 4),
    ROLE_ADMIN("ROLE_ADMIN", "관리자", 99),
    ;


    private final String label;
    @Getter
    private final String name;
    private final int level;

    Grade(String label, String name, int level) {
        this.label = label;
        this.name = name;
        this.level = level;
    }

    private int level() {
        return level;
    }

    public String label() {
        return label;
    }

    @JsonCreator
    public static Grade codeOf(String label) {
        return Arrays.stream(Grade.values())
                .filter(a -> a.label.equals(label))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(label + "변환에 실패 했습니다"));
    }
}



