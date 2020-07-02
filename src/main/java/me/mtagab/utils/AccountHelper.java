package me.mtagab.utils;

import lombok.extern.slf4j.Slf4j;
import me.mtagab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountHelper implements CommandLineRunner {

    @Autowired
    UserRepository repository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Total user size: " + this.repository.findAll().size());
    }
}
