package tw.edu.fju.miniclinic.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import tw.edu.fju.miniclinic.model.Doctor;
import tw.edu.fju.miniclinic.model.DoctorRepository;
import tw.edu.fju.miniclinic.model.PasswordForm;

@Controller
public class PasswordController {

    @Autowired
    private DoctorRepository doctorRepo;

    @GetMapping("/password")
    public String passwordForm(HttpSession session, Model model) {
        model.addAttribute("loggedInDoctorName", session.getAttribute("loggedInDoctorName"));
        model.addAttribute("passwordForm", new PasswordForm());
        return "password";
    }

    @PostMapping("/password")
    public String updatePassword(
            @Valid @ModelAttribute("passwordForm") PasswordForm form,
            BindingResult result,
            HttpSession session,
            Model model) {

        model.addAttribute("loggedInDoctorName", session.getAttribute("loggedInDoctorName"));

        // 1. 基本 Bean Validation (如空白、長度)
        if (result.hasErrors()) {
            return "password";
        }

        // 2. 以 Session 中的 loggedInDoctorId 查詢醫師
        String doctorId = (String) session.getAttribute("loggedInDoctorId");
        Doctor doctor = doctorRepo.findById(doctorId).orElse(null);

        if (doctor == null) {
            return "redirect:/login";
        }

        // 3. BCrypt.checkpw(oldPassword, doctor.getPasswordHash()) 若為 false，顯示「舊密碼錯誤」
        if (!BCrypt.checkpw(form.getOldPassword(), doctor.getPasswordHash())) {
            model.addAttribute("errorMessage", "舊密碼錯誤");
            return "password";
        }

        // 4. 新密碼與確認密碼不一致，顯示「兩次密碼不相符」
        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            model.addAttribute("errorMessage", "兩次密碼不相符");
            return "password";
        }

        // 5. 滿足需求：驗證通過後加密並儲存
        String hashed = BCrypt.hashpw(form.getNewPassword(), BCrypt.gensalt());
        doctor.setPasswordHash(hashed);
        doctorRepo.save(doctor);

        model.addAttribute("successMessage", "密碼修改成功！");
        return "password";
    }
}