package tw.edu.fju.miniclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.fju.miniclinic.model.Appointment;
import tw.edu.fju.miniclinic.model.AppointmentRepository;
import tw.edu.fju.miniclinic.model.DoctorRepository;
import tw.edu.fju.miniclinic.model.PatientRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class StatsController {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @GetMapping("/api/stats")
    public Map<String, Object> getStats() {
        long totalDoctors = doctorRepo.count();
        long totalPatients = patientRepo.count();
        List<Appointment> appts = appointmentRepo.findAll();

        Map<String, Long> byStatus = new HashMap<>();
        byStatus.put("BOOKED", appts.stream().filter(a -> "BOOKED".equals(a.getStatus())).count());
        byStatus.put("COMPLETED", appts.stream().filter(a -> "COMPLETED".equals(a.getStatus())).count());
        byStatus.put("CANCELLED", appts.stream().filter(a -> "CANCELLED".equals(a.getStatus())).count());

        Map<String, Object> response = new HashMap<>();
        response.put("totalDoctors", totalDoctors);
        response.put("totalPatients", totalPatients);
        response.put("totalAppointments", (long) appts.size());
        response.put("byStatus", byStatus);

        return response;
    }
}