package me.mtagab.controllers;

import lombok.extern.slf4j.Slf4j;
import me.mtagab.models.User;
import me.mtagab.repository.UserRepository;
import me.mtagab.service.EmailService;
import me.mtagab.service.TextService;
import me.mtagab.service.WikiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
@Slf4j
public class UsersController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WikiService wiki;

    @Autowired
    EmailService email;

    @Autowired
    TextService text;

    @GetMapping("/all")
    public List<User> getUsers() {
        String raw =  wiki.getPageResult();
        List<String> result = wiki.extract(raw);

        List<User> userlist = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            String[] demo = result.get(i).split(" ");
            User user = new User();
            user.setFirstname(demo[0]);
            user.setLastname(demo[1]);
            userlist.add(user);
        }

        //registered users, comment out to test delete function..
       //return userRepository.findAllByOrderByFirstnameAsc();

        return userlist;
    }

    @PutMapping(value = "/edit/{id}", produces = "application/json")
    public ResponseEntity<String>  updateUser(@PathVariable("id") String id, String firstname) {
        Optional<User> search = userRepository.findById(id);
        if(search.isPresent()) {
            User user = search.get();
            user.setFirstname(firstname);
            userRepository.save(user);
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        }

    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json")
    public ResponseEntity<String> deleteUser(@PathVariable("id") String id) {

        log.info("Attempting to delete..." + id);

        Optional<User> search = userRepository.findById(id);

        if(search.isPresent()) {

            search.ifPresent(user -> userRepository.delete(user));

            //send an email
            search.ifPresent(user ->  email.deleteEmail("proxyshadow@gmail.com", user.getUsername()));
            log.info("Deleted");

            //send text
            text.sendText("+639155140113", "User deleted");

            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } else {
            log.info("Failed");
            return new ResponseEntity<>("Failed", HttpStatus.OK);
        }

    }

}
