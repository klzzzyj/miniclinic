package tw.edu.fju.miniclinic.model;

import jakarta.validation.constraints.NotBlank;

public class LoginForm {
    @NotBlank(message = "請輸入醫師編號")
    private String doctorId;

    @NotBlank(message = "請輸入密碼")
    private String password;

    public String getDoctorId() { return doctorId; }
    public void setDoctorId(String doctorId) { this.doctorId = doctorId; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}