package com.jaherrera.takebread.user.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.mongodb.morphia.annotations.Entity;

@Entity
@XmlRootElement
public enum BreadTypeEnum implements Serializable{
	NORMAL, WHOLEMEAL;
}
