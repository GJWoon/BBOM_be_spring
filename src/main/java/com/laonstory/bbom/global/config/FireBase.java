package com.laonstory.bbom.global.config;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class FireBase {

    private final PasswordEncoder passwordEncoder;

    @Value("${fireBase.path}")
    private String path;

/*    private void start() throws IOException {


        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(path))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

        System.out.println("Buckets:");
        Page<Bucket> buckets = storage.list();
        for (Bucket bucket : buckets.iterateAll()) {
            System.out.println(bucket.toString());
        }

        FirebaseApp firebaseApp = null;

        List<FirebaseApp> firebaseApps = FirebaseApp.getApps();

        if (firebaseApps != null && !firebaseApps.isEmpty()) {

            for (FirebaseApp app : firebaseApps) {
                if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
                    firebaseApp = app;
                }
            }

        } else {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options);
        }


    }*/

    @PostConstruct
    public void init() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(
                            GoogleCredentials
                                    .fromStream(new FileInputStream(path)))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase application has been initialized");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }






/*
    public void sendSubscribeTopic(PushModelRequest pushModel)
            throws FirebaseMessagingException, IOException {


        start();

        try {

            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(pushModel.getTitle())
                            .setBody(pushModel.getMessage())
                            .build())
                    .setTopic(pushModel.getTopic())
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Successfully sent message: " + response);

        }catch (Exception e){

        }
    }
*/

//    @Async("asyncTask")
//    public void sendMessage(FireBasePushModel model) throws IOException, FirebaseMessagingException {
//
//       // start();
//
//
//
//        try {
///*
//            if(model.getMode().equals("a")){
//
//                Message message = Message.builder()
//                        .putData("AndroidConfig_body", model.getContent())
//                        .putData("title", model.getTitle())
//                        .putData("body", model.getContent())
//                        .setToken(model.getToken())
//                        .build();
//
//                String response = FirebaseMessaging.getInstance().send(message);
//
//                log.info("Successfully sent message: " + response);
//*/
//
//            //}
//            //else{
//
//            Message message = Message.builder()
//                    .setNotification(Notification.builder()
//                            .setTitle(model.getTitle())
//                            .setBody(model.getContent())
//                            .build())
//                    .setToken(model.getToken())
//                    .build();
//
//
//            ApiFuture<String> response = FirebaseMessaging.getInstance().sendAsync(message);
//            log.info("Successfully sent message: " + response.get());
//
//            //  }
//
//        }
//
//        //}
//         catch (Exception e) {
//        }
//
//    }

    @Async("asyncTask")
    public void sendMessage(String token, String title, String content) throws IOException, FirebaseMessagingException {
       // start();
        log.info(token + "firebase token ");
        try {
/*        if(type.equals("a")){

            Message message = Message.builder()
                    .putData("AndroidConfig_body", content)
                    .putData("title", title)
                    .putData("body", content)
                    .setToken(token)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);

            log.info("Successfully sent message: " + response);

        }*/
            //else{
            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(content)
                            .build())
                    .setToken(token)
                    .build();


            ApiFuture<String> response = FirebaseMessaging.getInstance().sendAsync(message);
            log.info("Successfully sent message: " + response.get());

    //  }
        }
        catch (Exception e) {
        }

    }

//
//    public void sendAndroid(FireBasePushModel pushModel) throws IOException, FirebaseMessagingException {
//
//       // start();
//
//
//
//        try {
//
//            Message message = Message.builder()
//                    .putData("AndroidConfig_body", pushModel.getContent())
//                    .putData("title", pushModel.getTitle())
//                    .putData("body", pushModel.getContent())
//                    .setTopic(pushModel.getTopic())
//                    .build();
//
//            String response = FirebaseMessaging.getInstance().send(message);
//
//            log.info("Successfully sent message: " + response);
//
//        }
//
//
//
//        catch (Exception e) {
//        }
//
//    }


}
