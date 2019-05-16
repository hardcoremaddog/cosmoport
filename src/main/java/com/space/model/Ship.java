package com.space.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ship")
public class Ship {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPlanet(String planet) {
		this.planet = planet;
	}

	public void setShipType(ShipType shipType) {
		this.shipType = shipType;
	}

	public void setProdDate(Date prodDate) {
		this.prodDate = prodDate;
	}

	public void setUsed(Boolean used) {
		isUsed = used;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public void setCrewSize(Integer crewSize) {
		this.crewSize = crewSize;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	//redundant, can be deleted
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Ship ship = (Ship) o;
		return Objects.equals(id, ship.id) &&
				Objects.equals(name, ship.name) &&
				Objects.equals(planet, ship.planet) &&
				shipType == ship.shipType &&
				Objects.equals(new Date(prodDate.getTime()).getYear(), new Date(prodDate.getTime()).getYear()) &&
				Objects.equals(isUsed, ship.isUsed) &&
				Objects.equals(speed, ship.speed) &&
				Objects.equals(crewSize, ship.crewSize) &&
				Objects.equals(rating, ship.rating);
	}

	//redundant, can be deleted
	@Override
	public int hashCode() {
		return Objects.hash(id, name, planet, shipType, prodDate, isUsed, speed, crewSize, rating);
	}
}