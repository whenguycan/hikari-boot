package com.lepus.hikariboot.framework.build;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author whenguycan
 * @date 2017年11月6日 下午9:28:41
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(columnDefinition = "varchar(32)")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(strategy = "uuid", name = "system-uuid")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
