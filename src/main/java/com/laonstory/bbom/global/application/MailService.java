package com.laonstory.bbom.global.application;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Async
    public Boolean sendMail(String sendEmail){
        StringBuilder mailHtml = new StringBuilder();
        mailHtml.append("<!DOCTYPE html>\n" +
                "<html lang=\"ko\">\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <title></title>\n" +
                "</head>\n" +
                "<body style=\"margin:0; padding:0; width: 500px;height: 500px\">\n" +
                "<div class=\"wrap\" style=\"width: 360px; height: 640px; float: left;\">\n" +
                "    <section style=\"width: 360px; height: 500px;\">\n" +
                "        <div style=\"width: 360px; height: auto; position: relative; left:50%; transform: translateX(-50%);\">\n" +
                "            <div class=\"img_box\" style=\"padding-left: 20px; width:140px; height:21px;margin-bottom: 30px\">\n" +
                "                <img style=\"width: 100%; height: auto;\" src=\"cid:my_image1\" alt=\"\" />\n" +
                "            </div>\n" +
                "            <div class=\"text_box\" style=\"margin-top: 24px;text-align:center;background-color: #742DDD\">\n" +
                "                <ul style=\"padding: 0; font-size: 16px; list-style: none; margin-left: 0;\">\n" +
                "                    <h3 style=\"font-size: 20px; color: #333;\"><img  style=\"width: 150px; height: 150px;\" src=\"http://192.168.100.216:7585/api/bbom/image?path=/images/profile/dlwlrma/스크린샷 2021-11-16 오후 4.14.29.png\"></h3>\n" +
                "                    <li style=\"margin-top: 18px; color:#666; list-style: none; font-weight: bold;color: white\">비밀번호 재설정을 도와드리겠습니다.</li>\n" +
                "\n" +
                "\n" +
                "                    <li style=\"margin-top: 20px; color:#742DDD; list-style: none; font-weight: 900; font-size: xx-large;;color: white\">\n" +
                "                        여기야여기<br/>\n" +
                "                    </li>\n" +
                "\n" +
                "                    <li style=\"padding-bottom:20px; margin-top: 20px; color:#666; list-style: none; font-weight: bold;color: white\">\n" +
                "                        아래의 일렬번호를 입력해주세요<br/>\n" +
                "                    </li>\n" +
                "                </ul>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </section>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
            mimeMessageHelper.setFrom("test@test.com","BBOM");
            mimeMessageHelper.setTo(sendEmail);
            mimeMessageHelper.setSubject("BBOM 비밀번호 인증번호 입니다.");
            mimeMessageHelper.setText(mailHtml.toString(), true);
            javaMailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    return true;
    }

}
