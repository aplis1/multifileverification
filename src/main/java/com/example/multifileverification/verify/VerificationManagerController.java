package com.example.multifileverification.verify;

import com.example.multifileverification.model.FileAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VerificationManagerController {
    private VerificationManager verificationManager;

    @Autowired
    public VerificationManagerController(VerificationManager verificationManager) {
        this.verificationManager = verificationManager;
    }

    @PostMapping("/verify")
    public void verifyFilesAndNotifyPackageAsync(
            @RequestBody List<FileAnalysis> fileAnalyses,
            @RequestParam String packageId,
            @RequestParam String recipient
    ) {
        verificationManager.verifyFilesAndNotifyPackageAsync(fileAnalyses, packageId, recipient);
    }
}
