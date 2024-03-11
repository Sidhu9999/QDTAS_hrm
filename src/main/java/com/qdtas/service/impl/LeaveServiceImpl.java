package com.qdtas.service.impl;

import com.qdtas.dto.LeaveDTO;
import com.qdtas.entity.Leave;
import com.qdtas.entity.User;
import com.qdtas.exception.ResourceNotFoundException;
import com.qdtas.repository.LeaveRepository;
import com.qdtas.repository.UserRepository;
import com.qdtas.service.LeaveService;
import com.qdtas.service.UserService;
import com.qdtas.utility.LeaveStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {
    @Autowired
    private LeaveRepository leaveRequestRepository;

    @Autowired
    private UserService usr;

    public List<Leave> getAllLeaveRequests(int pgn, int size) {
        return leaveRequestRepository.findAll(   PageRequest.of(pgn, size, Sort.by(Sort.Direction.ASC, "startDate") )  )
                .stream().toList();
    }

    public Leave createLeaveRequest(long empId,LeaveDTO leaveRequest) {
        Leave l =new Leave();
        l.setStatus(LeaveStatus.PENDING.name());
        l.setReason(leaveRequest.getReason());
        l.setType(leaveRequest.getType());
        l.setStartDate(leaveRequest.getStartDate());
        l.setEndDate(leaveRequest.getEndDate());
        User u = usr.getById(empId);
        l.setEmployee(u);
        return leaveRequestRepository.save(l);
    }

    public Leave updateLeaveRequest(Long id, LeaveDTO updatedLeaveRequest) {
        Leave existingLeaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        existingLeaveRequest.setEmployee(usr.getById(updatedLeaveRequest.getEmployeeId()));
        existingLeaveRequest.setStartDate(updatedLeaveRequest.getStartDate());
        existingLeaveRequest.setReason(updatedLeaveRequest.getReason());
        existingLeaveRequest.setEndDate(updatedLeaveRequest.getEndDate());
        existingLeaveRequest.setType(updatedLeaveRequest.getType());
        return leaveRequestRepository.save(existingLeaveRequest);
    }

    public Leave getLeaveById(long lId){
        return leaveRequestRepository.findById(lId).orElseThrow(()-> new ResourceNotFoundException("LeaveRequest","LeaveID",String.valueOf(lId)));
    }

    public void deleteLeaveRequest(Long id) {
        Leave l = getLeaveById(id);
        leaveRequestRepository.delete(l);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Leave approveLeaveRequest(Long id) {
        Leave leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        leaveRequest.setStatus(LeaveStatus.APPROVED.name());

        return leaveRequestRepository.save(leaveRequest);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Leave rejectLeaveRequest(Long id) {
        Leave leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        leaveRequest.setStatus(LeaveStatus.REJECTED.name());
        return leaveRequestRepository.save(leaveRequest);
    }
}
