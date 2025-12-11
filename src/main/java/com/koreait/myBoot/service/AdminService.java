package com.koreait.myBoot.service;

import com.koreait.myBoot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final UserRepository userRepository;

    public long getTotalUsers() { return userRepository.count(); }

    public long getAdminCount() { return userRepository.countByRole("ROLE_ADMIN"); }

    public long getUserCount() { return userRepository.countByRole("ROLE_USER"); }
}
