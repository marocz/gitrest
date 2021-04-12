package git.gitrest.api.controller;

import git.gitrest.api.dto.NotificationDTO;
import git.gitrest.api.repository.UserRepository;
import git.gitrest.api.entities.User;
import git.gitrest.api.model.Notification;
import git.gitrest.api.services.CreateNewAccount;
import git.gitrest.api.services.NotificationServices;
import git.gitrest.api.dto.Model1DTO;
import git.gitrest.api.dto.String2DTO;
import org.apache.http.client.methods.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/notification")
public class NotificationController {


    @Autowired
    NotificationServices notificationServices;

    @Autowired
    CreateNewAccount createNewAccount;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRepository userService;

    @GetMapping("")
    @ResponseBody
    public List<Notification> getAllNotification(){
        return notificationServices.getNotification();
    }
    @PostMapping("/email")
    @ResponseBody
    public List<Notification> getAllProfiles(@Valid @RequestBody Model1DTO profileDTO){

        return notificationServices.getNotifications(profileDTO.getEmail());
    }

    @PostMapping("/addone")
    @ResponseBody
    public Notification addProfile(@Valid @RequestBody NotificationDTO notificationDTO){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String logname = loggedInUser.getName();

        User users = userRepository.findByUsername(logname);
        return notificationServices.addNotification(notificationDTO, users.getEmail());
    }


    @PostMapping("/delete")
    @ResponseBody
    public Notification deleteNotification(@Valid @RequestBody String2DTO idDTO){
        long Id = Long.parseLong(idDTO.getId());
        return notificationServices.deleteNotification(Id);
    }


}
