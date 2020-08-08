package com.project.Veterinary.entities;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "promociones")
public class Promocion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private UUID foto;
	private Integer descuento;
	public Integer getDescuento() {
		return descuento;
	}
	public void setDescuento(Integer descuento) {
		this.descuento = descuento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public UUID getFoto() {
		return foto;
	}
	public void setFoto(UUID foto) {
		this.foto = foto;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	

}
