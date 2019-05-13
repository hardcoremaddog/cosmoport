package com.space.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Ship {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "planet")
	private String planet;
	@Enumerated(EnumType.STRING)
	@Column(name = "shipType")
	private ShipType shipType;
	@Column(name = "prodDate")
	private Date prodDate;
	@Column(name = "isUsed")
	private Boolean isUsed;
	@Column(name = "speed")
	private Double speed;
	@Column(name = "crewSize")
	private Integer crewSize;
	@Column(name = "rating")
	private Double rating;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPlanet() {
		return planet;
	}

	public ShipType getShipType() {
		return shipType;
	}

	public Date getProdDate() {
		return prodDate;
	}

	public Boolean isUsed() {
		return isUsed;
	}

	public Double getSpeed() {
		return speed;
	}

	public Integer getCrewSize() {
		return crewSize;
	}

	public Double getRating() {
		return rating;
	}

}
