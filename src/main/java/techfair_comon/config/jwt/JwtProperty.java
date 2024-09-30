package techfair_comon.config.jwt;

public class JwtProperty {

    // JWT의 서명에 사용할 비밀키 HS512
    public final String SECRET = "NiOeyFbN1Gqo10bPgUyTFsRMkJpGLXSvGP04eFqj5B30r5TcrtlSXfQ7TndvYjNvfkEKLqILn0j1SmKODO1Yw3JpBBgI3nVPEahqxeY8qbPSFGyzyEVxnl4AQcrnVneI";

    // 발급된 토큰의 만료시간
    public final int EXPIRATION_TIME = 60000 * 10;

    // 토큰 발급자
    public final String ISSUER = "jwt";
    
}
