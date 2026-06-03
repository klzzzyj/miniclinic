package tw.edu.fju.miniclinic.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        // 檢查 Session 中是否有登入資訊
        Object doctorName = session.getAttribute("loggedInDoctorName");
        model.addAttribute("isLoggedIn", doctorName != null);
        model.addAttribute("doctorName", doctorName);
        return "home";
    }
}