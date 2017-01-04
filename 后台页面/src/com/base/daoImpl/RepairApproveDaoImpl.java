package com.base.daoImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.base.dao.RepairApproveDao;
import com.base.po.Layout_InfoView;
import com.base.po.MaintainApplys;
import com.base.po.MaintainList;
import com.base.po.MaintenanceList;
import com.base.po.Prabaseinfo;
import com.base.utils.SqlConnectionUtils;

@Repository("repairApproveDao")
public class RepairApproveDaoImpl implements RepairApproveDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public MaintainList getRepairInfo(String baseid, String userid, int pageIndex,
			int size, String orderColumn, String orderDir, String searchValue,
			int status) {
		MaintainList ma=new MaintainList();
		List<MaintainApplys> list=new ArrayList<MaintainApplys>();
		MaintainApplys mta=null;
		int recordsTotal = 0;
		Connection conn = null;
		CallableStatement sp = null;
		ResultSet rs = null;
		
		try
		{
			conn = (Connection) SessionFactoryUtils.getDataSource(
					sessionFactory).getConnection();
			sp = (CallableStatement) conn.prepareCall("{call baseweb.query_maintainrecord(?,?,?,?,?,?,?,?,?)}");		
			sp.setInt(1, size);
			sp.setInt(2, pageIndex);
			sp.setString(3, orderColumn);
			sp.setString(4, orderDir);
			sp.setString(5, searchValue);
			sp.setInt(6, status);
			sp.setString(7, userid);
			sp.setString(8, baseid);			
			System.out.println(pageIndex+" "+size+"  "+orderColumn+"  "+orderDir+"  "+searchValue+"  "+status);
			sp.registerOutParameter(9, java.sql.Types.INTEGER);			
			sp.execute();
			System.out.println("haha,weixiu2");
			recordsTotal = sp.getInt(9);
			rs = sp.getResultSet();
			while (rs.next())
			{
			    mta = new MaintainApplys();
				mta.setId(rs.getInt("id"));
				mta.setBasename(rs.getString("basename"));
				mta.setPro_name(rs.getString("pro_name"));
				mta.setUsername(rs.getString("username"));
				mta.setAddress(rs.getString("address"));
				mta.setApply_time(rs.getString("apply_time"));
				mta.setFile(rs.getString("file"));
				mta.setMoney(rs.getDouble("money"));
				mta.setReason(rs.getString("reason"));
				mta.setUserid(rs.getString("userid"));
				list.add(mta);
			}
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			SqlConnectionUtils.free(conn, sp, rs);
		}
		ma.setRecordsTotal(recordsTotal);
		ma.setData(list);	
		return ma;
		
	}

	@Override
	public List<Map<String, String>> getUser(int status) {
		Connection conn = null;
		CallableStatement sp = null;
		ResultSet rs = null;
		
		Session session=sessionFactory.openSession();			
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		HashMap<String, String> map=null;
		
		try {
			conn = (Connection)SessionFactoryUtils.getDataSource(sessionFactory).getConnection();			
			sp= (CallableStatement) conn.prepareCall("{CALL baseweb.query_maintainname(?)}");  //发送存储过程
			sp.setInt(1, status);			
			sp.execute();   //执行存储过程
			rs=sp.getResultSet();  //获得结果集
			
			while(rs.next())    //遍历结果集，赋值给list
			{
				map=new HashMap<String, String>();
				map.put("username", rs.getString("username"));
				list.add(map);				
			}
	    	 
			
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			session.close();
		}
		return list;
	}

	@Override
	public List<Map<String, String>> getBase(int status) {
		Connection conn = null;
		CallableStatement sp = null;
		ResultSet rs = null;
		
		Session session=sessionFactory.openSession();			
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		HashMap<String, String> map=null;
		
		try {
			conn = (Connection)SessionFactoryUtils.getDataSource(sessionFactory).getConnection();			
			sp= (CallableStatement) conn.prepareCall("{CALL baseweb.query_maintainbasename(?)}");  //发送存储过程
			sp.setInt(1, status);			
			sp.execute();   //执行存储过程
			rs=sp.getResultSet();  //获得结果集
			
			while(rs.next())    //遍历结果集，赋值给list
			{
				map=new HashMap<String, String>();
				map.put("id", rs.getString("id"));
				map.put("basename", rs.getString("basename"));
				list.add(map);				
			}
	    	 
			
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			session.close();
		}
		return list;
	}

}
