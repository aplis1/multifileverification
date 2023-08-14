package com.example.multifileverification.verify;

import com.example.multifileverification.model.FileAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class VerificationManager {

    @Autowired
    FileVerifier fileVerifier;
    @Autowired
    EmailService emailService;

    public void verifyFilesAndNotifyPackageAsync(List<FileAnalysis> fileAnalyses, String packageId, String recipient) {
        List<CompletableFuture<FileAnalysis>> verificationFutures = fileAnalyses.stream()
                .map(fileAnalysis -> CompletableFuture.supplyAsync(() -> {
                    String result = fileVerifier.verifyFile(fileAnalysis.getFilePath());
                    fileAnalysis.setAnalysisResult(result);
                    return fileAnalysis;
                }))
                .collect(Collectors.toList());

        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(verificationFutures.toArray(new CompletableFuture[0]));

        CompletableFuture<Void> finalResult = allOfFuture.thenAcceptAsync(ignored -> {
            List<String> analysisResults = verificationFutures.stream()
                    .map(verificationFuture -> {
                        try {
                            FileAnalysis fileAnalysis = verificationFuture.get();
                            return fileAnalysis.getAnalysisResult();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                            return "Error occurred during analysis";
                        }
                    })
                    .collect(Collectors.toList());

            String packageAnalysis = String.join("\n", analysisResults);
            sendPackageVerificationNotification(packageId, recipient, packageAnalysis);
        });

        // Introduce a delay to allow asynchronous computation to complete.
        try {
            finalResult.get(); // Wait for verification and notification to complete
            TimeUnit.SECONDS.sleep(2); // Delay for demonstration
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendPackageVerificationNotification(String packageId, String recipient, String packageAnalysis) {
        String subject = "Package Verification Complete";
        String content = "Package ID: " + packageId + "\n\n" + packageAnalysis;

        try {
            emailService.sendEmail(recipient, subject, content);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
