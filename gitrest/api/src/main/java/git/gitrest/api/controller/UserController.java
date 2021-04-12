package git.gitrest.api.controller;

import git.gitrest.api.repository.ConfirmationTokenRepository;
import git.gitrest.api.repository.UserRepository;
import git.gitrest.api.entities.User;
import git.gitrest.api.model.ConfirmationToken;
import git.gitrest.api.services.EmailSenderService;
import git.gitrest.api.services.SecurityService;
import git.gitrest.api.services.UserService;
import git.gitrest.api.services.UserValidator;
import org.slf4j.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;


@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    @Autowired
    private UserRepository userService;
    @Autowired
    private UserService userServiceorig;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private EmailSenderService emailSenderService;


    public static String removelastcharacter(String str) {
        String result = null;
        if((str != null) && (str.length() > 0)) {
            result = str.substring(0, str.length() - 1);
        }
        return result;
    }

    @GetMapping("/404")
    public String four(Model model) {

        return "404";
    }

    @GetMapping(value = {"/index","/", "landingpage"})
    public String welcome(Model model) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String logname = loggedInUser.getName();

         User users = userService.findByUsernameIgnoreCase(logname);

        return "landingpage";
    }

    @RequestMapping(value="/forgot-password", method=RequestMethod.POST)
    public String forgotUserPassword(ModelAndView modelAndView, @RequestBody User user, @RequestParam(value = "url", defaultValue = "") String url) {
        User existingUser = userRepository.findByEmailIgnoreCase(user.getEmail());
        if(existingUser != null) {
            // create token
            ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);

            // save it
            confirmationTokenRepository.save(confirmationToken);

            // create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existingUser.getEmail());
            mailMessage.setSubject("Complete Password Reset!");
            mailMessage.setFrom("nairobley@gmail.com");
            mailMessage.setText("To complete the password reset process, please click here: "
                    +url+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

            return "Request to reset password received. Check your inbox for the reset link.";


        } else {
            return "This email does not exist!";

        }


    }

    @RequestMapping(value="/sendtokentomail", method=RequestMethod.POST)
    public int[] sendtokentomail(ModelAndView modelAndView, @RequestBody User user, @RequestParam(value = "url", defaultValue = "") String url) {
        User existingUser = userRepository.findByEmailIgnoreCase(user.getEmail());
        if(existingUser != null) {
            int[] numbers = new int[3];

            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = (int) (Math.random() * 9);
            }

            // create token
            ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);

            // save it
            confirmationTokenRepository.save(confirmationToken);

            // create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existingUser.getEmail());
            mailMessage.setSubject("Complete Password Reset!");
            mailMessage.setFrom("nairobley@gmail.com");
            mailMessage.setText("Your token is : "
                    + numbers[0] + numbers[1] + numbers[2] + numbers[3] );

            emailSenderService.sendEmail(mailMessage);

            return numbers;


        } else {
            int[] numbers2 = new int[3];
            return numbers2;

        }


    }

    public void authWithoutPassword(User user) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @RequestMapping(value="/confirm-reset", method= {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, String> validateResetToken(Model modelAndView, @RequestParam("token")String confirmationToken) {
        try {
            ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

            if (token != null) {
                User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
                user.setStatus("Active");
                userRepository.save(user);
                modelAndView.addAttribute("user", user);
                modelAndView.addAttribute("emailId", user.getEmail());
                HashMap<String, String> map = new HashMap<>();
                map.put("email", user.getEmail());
                map.put("name", user.getUsername());
                map.put("message", "resetPassword");


                return map;

            }
        } catch (NullPointerException e) {

            HashMap<String, String> map = new HashMap<>();
            map.put("message", "The link is invalid or broken!");
            return map;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("message", "nothing is done yet!");
        return map;


    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public String resetUserPassword(Model modelAndView, @RequestBody User user, @RequestParam("token") String confirmationToken) {
        try {
            ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);


            if(token != null) {
                User users = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());

                System.out.println(users.getEmail());
                if (user.getEmail() != null) {
                    // use email to find user
                    if(users.getEmail().equalsIgnoreCase(user.getEmail())) {
                        User tokenUser = userRepository.findByEmailIgnoreCase(user.getEmail());
                        tokenUser.setStatus("Active");
                        tokenUser.setPassword(encoder.encode(user.getPassword()));
                        // System.out.println(tokenUser.getPassword());
                        userRepository.save(tokenUser);
                        modelAndView.addAttribute("message", "Password successfully reset. You can now log in with the new credentials.");
                        return "password updated successfully";
                    }

                } else {
                    modelAndView.addAttribute("message", "The link is invalid or broken!");
                    return "The link is invalid or broken";
                }
            }
            else{
                return "Invalid token";
            }
            return "password updated successfully";
        }
        catch(NullPointerException e){
            return "Something went wrong";
        }

    }


    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ConfirmationTokenRepository getConfirmationTokenRepository() {
        return confirmationTokenRepository;
    }

    public void setConfirmationTokenRepository(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public EmailSenderService getEmailSenderService() {
        return emailSenderService;
    }

    public void setEmailSenderService(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }


}