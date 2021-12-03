package com.laonstory.bbom.global.error.model;

public enum ErrorCode {
    INVALID_INPUT_VALUE(400, "C001", "올바르지 않은 형식입니다."),
    METHOD_NOT_ALLOWED(405, "C002", "지원하지 않는 메소드입니다."),
    ENTITY_NOT_FOUND(400, "C003", "해당 엔티티를 찾을 수가 없습니다."),
    INTERNAL_SERVER_ERROR(500, "C004", "알 수 없는 에러 (서버 에러)"),
    INVALID_TYPE_VALUE(400, "C005", "타입이 올바르지 않습니다."),
    HANDLE_ACCESS_DENIED(403, "C006", "권한이 없습니다."),
    HANDLE_INVALID_TOKEN(401, "C007", "토큰이 없거나 올바르지 않습니다."),
    NOT_MY_ENTITY(400, "C008", "내 작성물이 아닙니다."),
    DUPILCATE_EMAIL(500,"U001","중복된 이메일 입니다."),
    DUPILCATE_PHONE(500,"P002","중복된 전화번호 입니다."),
    DUPILCATE_ID(500,"U002","사용 불가능한 아이디 입니다."),
    DUPILCATE_NICKNAME(500,"U001","중복된 닉네임 입니다."),
    NOT_FOUND_EMAIL(500,"U002","존재하지 않는 이메일 입니다."),
    NOT_MATCH_PASSWORD(500,"U003","비밀번호를 확인 해 주세요"),
    NOT_FOUND_PRODUCT(500,"P001","해당 제품을 찾을 수 없습니다."),
    NOT_FOUND_ITEM(400,"I001","해당 게시글을 찾을 수 없습니다."),
    NOT_FOUND_CARE(400,"C001","해당 케어신청을 찾을 수 없습니다."),
    NOT_FOUND_ACTIVATION(400,"A001","해당 인증신청을 찾을 수 없습니다."),
    NOT_FOUND_CARE_RESULT(400,"CR001","해당 제품케어 결과를 찾을 수 없습니다."),
    NOT_FOUND_COUPON(400,"CP001","해당 쿠폰을 찾을 수 없습니다."),
    ALREADY_RESISTER_CODE(500,"C002","이미 초대코드를 입력하셨습니다."),
    NOT_FOUND_ACTIVATION_RESULT(400,"AR001","해당 정품인증 결과를 찾을 수 없습니다."),
    NOT_FOUND_USER(400,"U001","해당 유저를 찾을 수 없습니다"),
    MINE_CODE(500,"C003","본인 초대코드를 등록할 수 없습니다."),
    NOT_FOUND_CODEUSER(400,"U002","해당 초대코드를 갖은 유저를 찾을 수 없습니다"),
    ALREADY_RESISTER_COUPON(500,"C004","이미 해당 쿠폰을  등록하셨습니다."),
    NOT_FOUND_FAQ(400,"F001","해당 FaQ를 찾을 수 없습니다."),
    NOT_FOUND_CHANGE_USER_PRODUCT(400,"CU001","해당 제품 사용자 변경 내역을 찾을 수 없습니다."),
    OVER_POINT(500,"P001","소지하고 있는 적립금보다 많은 적림금을 사용할 수 없습니다."),
    RESISTER_PAGE(500,"R9721","회원가입 페이지로 이동"),
    COUPON_NOT_FOUND(400,"C0004","해당 코드의 쿠폰을 찾을 수 없습니다."),
    DUPLICATE_INVITE_CODE(409,"P003","이미 초대코드를 입력 하셨습니다."),
    DUPLICATE_CHANGE_PRODUCT(409,"CP001","해당 상품으로 제품 사용자 변경이 진행 중 입니다."),
    DUPLICATE_WITHDRAW_USER(409,"CP001","동일한 정보로 재가입한 회원입니다.\n이용 중으로 변경할 수 없습니다."),
    CHECK_TIME(501,"S001","30초 이내에 게시글을 작성할 수 없습니다."),
    WITHDRWA_USER(501,"CP001","탈퇴한 회원입니다."),
    NOT_CURRENT_USER(500,"CP001","회원탈퇴는 본안계정으로 진행 가능합니다."),
    NOT_CONTENT_USER(500,"CP003","본인이 작성한 게시글만 삭제 가능합니다."),

    FOLLOWING_ME(500,"F001","본인을 팔로우 할 수 없습니다.");
    private final int status;
    private final String code;
    private final String message;

   ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

}
