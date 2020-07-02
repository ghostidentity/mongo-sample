package me.mtagab.service;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final SimpleMailMessage smm;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
        this.smm = new SimpleMailMessage();
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<ExecutorService> deleteEmail(String email, String username) {
        return CompletableFuture.completedFuture(userDeleted(email, username));
    }

    private ExecutorService userDeleted(String toEmail, String username) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Runnable runnable = () -> {
            smm.setTo(toEmail);
            smm.setSubject("User deleted");
            smm.setText(username);
            smm.setFrom("proxyshadow@gmail.com");
            javaMailSender.send(smm);
        };
        executor.execute(runnable);
        return executor;
    }

    @Bean("threadPoolTaskExecutor")
    public TaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(1000);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix("emailer");
        return executor;
    }


}
