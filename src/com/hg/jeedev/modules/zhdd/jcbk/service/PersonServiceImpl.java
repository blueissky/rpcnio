package com.hg.jeedev.modules.zhdd.jcbk.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hg.jeedev.common.dao.DataBaseDao;

import top.rpc.anno.RpcService;

@RpcService(PersonService.class)
@Component
public class PersonServiceImpl implements PersonService{

	@Autowired
	private DataBaseDao dataBaseDao;
	public Person getInfo() {
		Person person = new Person();
		person.setAge(22);
		person.setName("qjm");
		person.setSex("男");
		System.out.println(222);
		return person;
	}

	public boolean printInfo(Person person) {
		System.out.println(111);
		String sql=" select * from GCLLTJ t where rownum <100 ";
		List obj = dataBaseDao.executeQuery(sql);
		System.out.println(obj);
		if(person != null){
			System.out.println(person);
			return true;
		}
		return false;
	}

}
