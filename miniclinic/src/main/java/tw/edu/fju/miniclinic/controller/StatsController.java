package tw.edu.fju.miniclinic.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import tw.edu.fju.miniclinic.model.Appointment;
import tw.edu.fju.miniclinic.model.AppointmentRepository;
import tw.edu.fju.miniclinic.model.Doctor;
import tw.edu.fju.miniclinic.model.DoctorRepository;
import tw.edu.fju.miniclinic.model.PatientRepository;

@Controller
public class StatsController {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @GetMapping("/stats")
    public String stats(Model model) {
        model.addAttribute("doctorCount", doctorRepo.count());
        model.addAttribute("patientCount", patientRepo.count());
        model.addAttribute("appointmentCount", appointmentRepo.count());

        Map<String, Long> apptCountByDepartment = new LinkedHashMap<>();
        for (Appointment appointment : appointmentRepo.findAll()) {
            Doctor doctor = appointment.getDoctor();
            if (doctor == null) {
                continue;
            }
            String department = doctor.getDepartment();
            if (department == null || department.isBlank()) {
                continue;
            }
            apptCountByDepartment.merge(department, 1L, Long::sum);
        }

        model.addAttribute("apptCountByDepartment", apptCountByDepartment);
        return "stats";
    }
}
