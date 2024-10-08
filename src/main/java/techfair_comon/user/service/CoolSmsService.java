package techfair_comon.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CoolSmsService {

    @Value("${coolsms.api-key}")
    private String apiKey;

    @Value("${coolsms.api-secret}")
    private String apiSecret;

    @Value("${coolsms.sender-number}")
    private String senderNumber;

    @Value("${coolsms.pf-id}")
    private String pfId;

    @Value("${coolsms.template-id}")
    private String templateId;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    // 인증번호 저장을 위한 ConcurrentHashMap (전화번호를 키로 사용)
    private final Map<String, String> certificationCodes = new ConcurrentHashMap<>();

    // 6자리 랜덤 인증 코드 생성 메서드
    public String generateRandomCode() {
        return String.valueOf(100000 + secureRandom.nextInt(900000));
    }

    // 16바이트 랜덤 SALT 생성 메서드 (32자리 16진수 문자열)
    private String generateSalt() {
        byte[] saltBytes = new byte[16];
        secureRandom.nextBytes(saltBytes);
        return bytesToHex(saltBytes);
    }

    // HMAC-SHA256 서명 생성 메서드 (16진수 인코딩)
    private String createSignature(String date, String salt) throws Exception {
        String message = date + salt;
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec signingKey = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(rawHmac);
    }

    // 바이트 배열을 16진수 문자열로 변환하는 유틸리티 메서드
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // 알림톡 메시지 전송 메서드
    public String sendCertificationCode(String phoneNumber) {
        String certificationCode = generateRandomCode();
        RestTemplate restTemplate = new RestTemplate();

        // 인증번호를 해당 전화번호와 함께 저장
        certificationCodes.put(phoneNumber, certificationCode);

        // 현재 시간을 UTC의 ISO 8601 형식으로 사용, 밀리초 제거
        String date = OffsetDateTime.now(ZoneOffset.UTC)
                .format(DATE_FORMATTER);
        String salt = generateSalt();

        try {
            // HMAC-SHA256 서명 생성 (16진수)
            String signature = createSignature(date, salt);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            // Authorization 헤더 설정
            headers.set("Authorization", String.format("HMAC-SHA256 apiKey=%s, date=%s, salt=%s, signature=%s",
                    apiKey, date, salt, signature));

            // AlimTalk 메시지 요청 데이터 생성
            Map<String, Object> kakaoOptions = new HashMap<>();
            kakaoOptions.put("pfId", pfId);
            kakaoOptions.put("templateId", templateId);
            Map<String, String> variables = new HashMap<>();
            variables.put("#{certification}", certificationCode);  // #{certification} 변수로 인증번호 설정
            kakaoOptions.put("variables", variables);

            Map<String, Object> messageData = new HashMap<>();
            messageData.put("to", phoneNumber);
            messageData.put("from", senderNumber);
            messageData.put("type", "ATA");
            messageData.put("kakaoOptions", kakaoOptions);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("message", messageData);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            String url = "https://api.coolsms.co.kr/messages/v4/send";

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("인증번호: " + certificationCode);
                return "인증번호가 발송되었습니다.";
            } else {
                return "인증번호 발송 실패: " + response.getBody();
            }
        } catch (Exception e) {
            return "오류 발생: " + e.getMessage();
        }
    }

    // 인증번호 검증 메서드
    public boolean verifyCertificationCode(String phoneNumber, String inputCode) {
        String storedCode = certificationCodes.get(phoneNumber);
        if (storedCode != null && storedCode.equals(inputCode)) {
            // 인증번호 일치 시, 인증번호 삭제 (재사용 방지)
            certificationCodes.remove(phoneNumber);
            return true;
        }
        return false;
    }
}
