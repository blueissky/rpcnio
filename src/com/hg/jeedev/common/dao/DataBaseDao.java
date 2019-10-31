package com.hg.jeedev.common.dao;

import java.util.List;
public interface DataBaseDao {
	public List executeQuery(String sql);
//	public int executeQueryProc(String s, List list);
	public int executeQueryProc2(String s, List list);
	public int save(String sql) throws Exception;
	public int saveWater(List list);
}
