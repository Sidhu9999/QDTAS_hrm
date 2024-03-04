package com.qdtas.service;

import com.qdtas.dto.AddUserDto;
import com.qdtas.dto.JwtResponse;
import com.qdtas.dto.LoginDTO;
import com.qdtas.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    public User create(AddUserDto rdt);

    public JwtResponse login(LoginDTO ld);

    public User getByEmail(String email);

    void verifyEmail(String token);

    public User getAuthenticatedUser();

    public User getById(long uId);

    public void enableUser(long userId);

    public List<User> searchUser(String keyword , int pgn,int sizes);

    public void forgotPassword(String email);

    public void changePassword(String email,String oldP,String newP);

    public void changeTempPassword(String email,String oldP,String newP);



}
