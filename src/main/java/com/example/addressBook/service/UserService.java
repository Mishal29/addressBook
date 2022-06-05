package com.example.addressBook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.addressBook.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepo;

	//ユーザー認証用id,pwが正しい組み合わせか判定
	public boolean verifyUser(String userId, String argPw) {
		String returnPw = (String) userRepo.getPw(userId).get("pw"); 
		
		return argPw.equals(returnPw);
	}
}
