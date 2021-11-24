package com.laonstory.bbom.global.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SmsResponse {

    private Integer result_code;
    private String message;
    private Integer msg_id;
    private Integer success_cnt;
    private Integer error_cnt;
    private String msg_type;


}



