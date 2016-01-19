package com.jaherrera.takebread.user.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity(value="users", noClassnameStored = true)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UserVo implements Serializable{

	private static final long serialVersionUID = 689649549545351051L;
	
	@Id private String id;
	private String name;
	private Boolean isBread;
	
	@Embedded
	private BreadTypeEnum breadType;
	
	private Integer userCode;
	private Integer totalBread;
	
	/** Getters and Setters **/
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsBread() {
		return isBread;
	}
	public void setIsBread(Boolean isBread) {
		this.isBread = isBread;
	}
	public BreadTypeEnum getBreadType() {
		return breadType;
	}
	public void setBreadType(BreadTypeEnum breadType) {
		this.breadType = breadType;
	}
	public Integer getUserCode() {
		return userCode;
	}
	public void setUserCode(Integer userCode) {
		this.userCode = userCode;
	}
	public Integer getTotalBread() {
		return totalBread;
	}
	public void setTotalBread(Integer totalBread) {
		this.totalBread = totalBread;
	}
	
	/** HashCode and Equals **/
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((breadType == null) ? 0 : breadType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isBread == null) ? 0 : isBread.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((totalBread == null) ? 0 : totalBread.hashCode());
		result = prime * result + ((userCode == null) ? 0 : userCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserVo other = (UserVo) obj;
		if (breadType != other.breadType)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isBread == null) {
			if (other.isBread != null)
				return false;
		} else if (!isBread.equals(other.isBread))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (totalBread == null) {
			if (other.totalBread != null)
				return false;
		} else if (!totalBread.equals(other.totalBread))
			return false;
		if (userCode == null) {
			if (other.userCode != null)
				return false;
		} else if (!userCode.equals(other.userCode))
			return false;
		return true;
	}
	
	/** ToString **/
	
	@Override
	public String toString() {
		return "UserVo [id=" + id + ", name=" + name + ", isBread=" + isBread + ", breadType=" + breadType
				+ ", userCode=" + userCode + ", totalBread=" + totalBread + "]";
	}
	
}
