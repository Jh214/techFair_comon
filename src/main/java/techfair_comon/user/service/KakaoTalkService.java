package techfair_comon.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoTalkService {

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Value("${kakao.api.secret}")
    private String kakaoApiSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    // 알림톡 API 호출 메소드
    public boolean sendCertificationCode(String phoneNumber) {
        String url = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + kakaoApiKey);

        // 요청 바디 설정 (PFID 및 템플릿 ID 사용)
        Map<String, Object> templateObject = new HashMap<>();
        templateObject.put("pfid", "KA01PF240930145539248iUN6bVyplGB"); // 채널 PFID
        templateObject.put("template_id", "KA01TP2410021835293785uUYjwfQnNJ"); // 템플릿 ID
        templateObject.put("object_type", "text");
        templateObject.put("text", "인증번호: #{certification}\n해당 인증번호를 입력해 주세요.");
        templateObject.put("link", new HashMap<>()); // 링크는 선택 사항으로 비워두거나 추가 가능
        templateObject.put("from", "01024799363"); // 발신자 번호 설정

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("template_object", templateObject);

        // HttpEntity 생성
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // API 요청
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
