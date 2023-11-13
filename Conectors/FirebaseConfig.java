package com.guiame.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.projectId}")
    private String projectId;

    @Value("${firebase.credentials}")
    private String credentials;

    @Bean
    public FirebaseApp firebaseApp() throws Exception {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId(projectId)
                .setCredentials(GoogleCredentials.fromStream(new java.io.FileInputStream(credentials)))
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
