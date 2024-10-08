package techfair_comon.entity.comment;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IsAnoymous {
    ANON("익명"),
    REAL("실명");

    private final String description;

    @JsonValue // description 에 들어간 값으로 사용자에게 반환
    public String getDescription() {
        return description;
    }
}
