package techfair_comon.bg.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorMessage {
    public static void DataAccessExceptionLog(Exception e) {
        log.error("데이터베이스 연결에 실패했습니다. 오류: {}", e.getMessage());
    }
    public static void ExceptionLog(Exception e) {
        log.error("알수없는오류가 발생했습니다. 오류 : {}", e.getMessage());
    }
    public static void DataIntegrityViolationExceptionLog(Exception e) {
        log.error("데이터 무결성 위반: {}", e.getMessage());
    }
    public static void EntityNotFoundExceptionLog(Exception e) {
        log.error("요청한 엔티티에 해당하는 데이터가 없습니다. 오류: {}", e.getMessage());
    }
    public static void TransactionRequiredExceptionLog(Exception e) {
        log.error("데이터베이스 작업을 수행하기 위해 필요한 트랜잭션이 활성화되지 않았습니다. 오류: {}", e.getMessage());
    }
    public static void IllegalArgumentExceptionLog(Exception e) {
        log.error("메서드에 전달된 인수가 유효하지 않습니다. 오류: {}", e.getMessage());
    }
    public static void EmptyResultDataAccessExceptionLog(Exception e) {
        log.error("데이터베이스 쿼리 실행 결과가 기대한 값(예: 1개의 결과)이 없습니다.");
    }
}

