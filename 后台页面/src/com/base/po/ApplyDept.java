package com.base.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class ApplyDept {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int aid;//����id
	private String dept;//�������
	private int type;//��ʶλ��������ѧԺ������������
	
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public ApplyDept() {
		super();
	}
	
	

}
