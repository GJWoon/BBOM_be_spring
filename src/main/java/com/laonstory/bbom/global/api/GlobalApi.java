package com.laonstory.bbom.global.api;



import com.laonstory.bbom.global.application.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GlobalApi {

    @Value("${app.filePath}")
    private String PATH;
    private final MailService mailService;


    @GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@RequestParam String path) throws IOException {

        File file = new File(PATH + path);
        InputStream in = new FileInputStream(file);
        return IOUtils.toByteArray(in);

    }

    @GetMapping("/test")
    public String sendE(@RequestParam String email){
        mailService.sendMail(email);
        return "Y";
    }


}
