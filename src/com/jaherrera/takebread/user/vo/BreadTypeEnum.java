package com.jaherrera.takebread.user.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.mongodb.morphia.annotations.Entity;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public enum BreadTypeEnum implements Serializable{
	NORMAL, WHOLEMEAL;
}
