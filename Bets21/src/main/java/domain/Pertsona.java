package domain;

import java.io.Serializable;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSeeAlso;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Admin.class,Bezero.class,Langilea.class})
public abstract class Pertsona implements Serializable{

	/**
	  * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@XmlID
	private String erabiltzailea;
	private String pasahitza;
	
	public Pertsona(String erabiltzailea, String pasahitza) {
		super();
		this.erabiltzailea = erabiltzailea;
		this.pasahitza = pasahitza;
	}
	
	public Pertsona() {
		super(); 
	}
	public String getErabiltzailea() {
		return erabiltzailea;
	}
	public void setErabiltzailea(String erabiltzailea) {
		this.erabiltzailea = erabiltzailea;
	}
	public String getPasahitza() {
		return pasahitza;
	}
	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}
	
	@Override
	public boolean equals (Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;
		Pertsona bestea = (Pertsona) o;
		if (!this.erabiltzailea.equals(bestea.erabiltzailea))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
	
}
