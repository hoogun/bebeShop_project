package com.bebe.spring.login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bebe.spring.login.dao.LoginDAO;
import com.bebe.spring.vo.UsersVO;


@Service("loginService")
public class LoginServiceImpl implements LoginService{

	@Autowired
	private LoginDAO loginDao;
	
	
	@Override
	public UsersVO selectUsers(UsersVO usersVO) {
		
		return loginDao.selectUsers(usersVO);
	}
	
	@Override
	public int insertUsers(UsersVO usersVO) {
		
		return loginDao.insertUsers(usersVO);
	}	
	
	
	@Override
	public UsersVO find_passwd(UsersVO usersVO) {
		
		return loginDao.find_passwd(usersVO);
	}
	
	@Override
	public List<UsersVO> find_id(UsersVO usersVO) {
		 
		return loginDao.find_id(usersVO);
	}

	@Override
	public int idCheck(UsersVO usersVO) {
	
		return loginDao.idCheck(usersVO);
	}

	
	@Override
	public int insertSocialLogin(UsersVO usersVO) {
		return loginDao.insertSocialLogin(usersVO);
	}
	
	@Override
	public int selectSocialLogin(UsersVO usersVO) {
		return loginDao.selectSocialLogin(usersVO);
	}
	
	
}

