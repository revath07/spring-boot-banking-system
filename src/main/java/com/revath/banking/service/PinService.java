package com.revath.banking.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revath.banking.entity.User;
import com.revath.banking.repository.UserRepository;

@Service
public class PinService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean validateTransactionPin(User user, String pin) {

        System.out.println("User ID: " + user.getId());
        System.out.println("Attempts before: " + user.getFailedPinAttempts());

        if(user.getPinLockedUntil()!=null &&
           user.getPinLockedUntil().isAfter(LocalDateTime.now()))
        {
            throw new RuntimeException("Account locked due to multiple wrong PIN attempts. Try later.");
        }

        if(!passwordEncoder.matches(pin, user.getTransactionPin()))
        {
            int attempts = user.getFailedPinAttempts() + 1;
            System.out.println("Attempts:::::"+attempts);
            user.setFailedPinAttempts(attempts);

            if(attempts >= 3)
            {
                user.setPinLockedUntil(LocalDateTime.now().plusMinutes(30));
                user.setFailedPinAttempts(0);
            }

            userRepository.save(user);

            return false;
        }

        user.setFailedPinAttempts(0);
        user.setPinLockedUntil(null);
        userRepository.save(user);

        return true;
    }
}