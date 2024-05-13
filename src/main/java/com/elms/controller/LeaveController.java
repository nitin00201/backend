package com.elms.controller;

import com.elms.entities.Leave;
import com.elms.entities.User;
import com.elms.repository.LeaveRepository;
import com.elms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

@CrossOrigin("*")
@RequestMapping("/api/leave")
@RestController
public class LeaveController {
    @Autowired
    private LeaveRepository  leaveRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/")
    public Leave createLeave(@RequestBody Leave leave){
        leaveRepository.save(leave);
        return leave;
    }

    @GetMapping("/count/{id}")
    public int leavesTaken(@PathVariable Long id){
        int count = 0;
        User user = userRepository.findById(id).orElse(null);
        if(user != null){
            System.out.println(user.getDays());
            System.out.println(user);
        }
        List<Leave>allMatchedLeave =leaveRepository.findAll().stream().filter(e -> Objects.equals(e.getUId(), id) && e.getStatus().equals("approve")).toList();
        System.out.println(allMatchedLeave);
        for(Leave l :allMatchedLeave){
            LocalDate l1 = LocalDate.from(l.getStartDate().toInstant().atZone(java.time.ZoneId.systemDefault()));
            LocalDate l2 = LocalDate.from(l.getEndDate().toInstant().atZone(java.time.ZoneId.systemDefault()));

            Period period = Period.between(l1,l2);

            int l3 = period.getDays()+1;
            System.out.println(l3);
            count = count+l3;
        }
        return count;
    }

    @GetMapping("/count/sick/{id}")
    public int sickLeavesTaken(@PathVariable Long id){
        int count = 0;
        User user = userRepository.findById(id).orElse(null);
        if(user != null){
            System.out.println(user.getDays());
            System.out.println(user);
        }
        List<Leave>allMatchedLeave =leaveRepository.findAll().stream().filter(e -> Objects.equals(e.getUId(), id) && e.getStatus().equals("approve")).toList();
        List<Leave>allSickLeave = allMatchedLeave.stream().filter((e -> Objects.equals(e.getLeaveType(), "sick"))).toList();
        System.out.println(allSickLeave);
        for(Leave l :allSickLeave){
            LocalDate l1 = LocalDate.from(l.getStartDate().toInstant().atZone(java.time.ZoneId.systemDefault()));
            LocalDate l2 = LocalDate.from(l.getEndDate().toInstant().atZone(java.time.ZoneId.systemDefault()));

            Period period = Period.between(l1,l2);

            int l3 = period.getDays()+1;
            System.out.println(l3);
            count = count+l3;
        }
        return count;
    }

    @GetMapping("/count/vacation/{id}")
    public int vacationLeavesTaken(@PathVariable Long id){
        int count = 0;
        User user = userRepository.findById(id).orElse(null);
        if(user != null){
            System.out.println(user.getDays());
            System.out.println(user);
        }
        List<Leave>allMatchedLeave =leaveRepository.findAll().stream().filter(e -> Objects.equals(e.getUId(), id) && e.getStatus().equals("approve")).toList();
        List<Leave>allSickLeave = allMatchedLeave.stream().filter((e -> Objects.equals(e.getLeaveType(), "vacation"))).toList();
        System.out.println(allSickLeave);
        for(Leave l :allSickLeave){
            LocalDate l1 = LocalDate.from(l.getStartDate().toInstant().atZone(java.time.ZoneId.systemDefault()));
            LocalDate l2 = LocalDate.from(l.getEndDate().toInstant().atZone(java.time.ZoneId.systemDefault()));

            Period period = Period.between(l1,l2);

            int l3 = period.getDays()+1;
            System.out.println(l3);
            count = count+l3;
        }
        return count;
    }

    @GetMapping("/")
    public List<Leave> getLeaves(){
        return leaveRepository.findAll();
    }

    @GetMapping("/{id}")
    public Leave getLeave(@PathVariable Long id){
        return leaveRepository.findById(id).orElse(null);
    }

    @PatchMapping("/{id}")
    public Leave updateLeave(@PathVariable Long id, @RequestBody Leave leave){
        Leave l = leaveRepository.findById(id).orElse(null);
        if(l != null){
            l.setStartDate(leave.getStartDate());
            l.setEndDate(leave.getEndDate());
            l.setLeaveCause(leave.getLeaveCause());
            l.setLeaveType(leave.getLeaveType());
            leaveRepository.save(l);

        }
        return l;
    }

    @PutMapping("/approve/{id}")
    public Leave approve(@PathVariable Long id, @RequestBody String adminComment){
       Leave l = leaveRepository.findById(id).orElse(null);
       if(l != null){
           l.setStatus("approve");
           l.setAdminComment(adminComment);
           leaveRepository.save(l);
       }
       return l;
    }

    @PutMapping("/reject/{id}")
    public Leave reject(@PathVariable Long id,@RequestBody String adminComment){
        Leave l = leaveRepository.findById(id).orElse(null);
        if(l != null){
            l.setStatus("reject");
            l.setAdminComment(adminComment);
            leaveRepository.save(l);
        }
        return l;
    }

    @DeleteMapping("/{id}")
    public void deleteLeave(@PathVariable Long id){
        leaveRepository.deleteById(id);
    }

    @DeleteMapping("/")
    public void deleteLeaves(){
        leaveRepository.deleteAll();
    }



}
