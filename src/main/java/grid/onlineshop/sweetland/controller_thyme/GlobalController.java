package grid.onlineshop.sweetland.controller_thyme;


import grid.onlineshop.sweetland.dto.request.AddUserDto;
import grid.onlineshop.sweetland.dto.request.ContactForm;
import grid.onlineshop.sweetland.util.EmailUtil;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class GlobalController {

    private EmailUtil emailUtil;

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        ContactForm contactForm = new ContactForm();

        model.addAttribute("contactForm",contactForm);

        return "contact";
    }

//    @GetMapping("/home")
//    public String home(Model model) {
//        return "prod";
//    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/card")
    public String card(){
        return "card-2";
    }

    @GetMapping("/login")
    public String myAccount(Model model) {
//        model.addAttribute("classicActiveLogin", true);
        model.addAttribute("user", new AddUserDto());
        return "login";
    }

    @RequestMapping("/")
    public String back() {
        return "welcome";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "welcome";
    }
//    @GetMapping("/logout")
//    public String logout() {
//        return "welcome";
//    }

    @GetMapping("/success")
    public String success(){
        return "success";
    }



    @PostMapping("/contact")
    public String submitContactForm(@ModelAttribute ContactForm form,Model model) {
//        SimpleMailMessage message = new SimpleMailMessage();
        sendEmail(form.getEmail(),form.getMessage(),form.getName());

        model.addAttribute("sent", true);


        return "contact";
    }

    private void sendEmail(String from, String text,String name) {
        emailUtil.send(from, text,name);
    }



}

