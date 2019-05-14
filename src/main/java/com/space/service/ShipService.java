package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipService {

	@Autowired
	ShipRepository shipRepository;

	public Comparator<Ship> getComparatorByOrder(String order) {
		Comparator<Ship> comparator = null;
		switch (ShipOrder.valueOf(order)) {
			case ID   : {
				comparator = Comparator.comparing(Ship::getId);
				break;
			}
			case SPEED: {
				comparator = Comparator.comparing(Ship::getSpeed);
				break;
			}
			case DATE : {
				comparator = Comparator.comparing(Ship::getProdDate);
				break;
			}
			case RATING: {
				comparator = Comparator.comparing(Ship::getRating);
			}
		}
		return comparator;
	}

	public List<Ship> getFilteredShipsList(String name,
									  String planet,
									  String shipType,
									  Long after,
									  Long before,
									  Boolean isUsed,
									  Double minSpeed,
									  Double maxSpeed,
									  Integer maxCrewSize,
									  Integer minCrewSize,
									  Double minRating,
									  Double maxRating) {

		List<Ship> filteredShipsList = shipRepository.findAll(); //no filter

		if (name != null) {
			filteredShipsList = filteredShipsList.stream()
					.filter(ship -> ship.getName().toLowerCase()
					.contains(name.toLowerCase()))
					.collect(Collectors.toList());
		}

		if (planet != null) {
			filteredShipsList = filteredShipsList.stream()
					.filter(ship -> ship.getPlanet().toLowerCase()
					.contains(planet.toLowerCase()))
					.collect(Collectors.toList());
		}

		if (shipType != null) {
			filteredShipsList = filteredShipsList.stream()
					.filter(ship -> ship.getShipType().equals(ShipType.valueOf(shipType)))
					.collect(Collectors.toList());
		}

		if (after != null) {
			filteredShipsList = filteredShipsList.stream()
					.filter(ship -> ship.getProdDate().after(new Date(after)))
					.collect(Collectors.toList());
		}

		if (before != null) {
			filteredShipsList = filteredShipsList.stream()
					.filter(ship -> ship.getProdDate().before(new Date(before)))
					.collect(Collectors.toList());
		}

		if (isUsed != null) {
			filteredShipsList = filteredShipsList.stream()
					.filter(ship -> ship.isUsed().equals(isUsed))
					.collect(Collectors.toList());
		}

		if (minSpeed != null) {
			filteredShipsList = filteredShipsList.stream()
					.filter(ship -> ship.getSpeed() >= minSpeed)
					.collect(Collectors.toList());
		}

		if (maxSpeed != null) {
			filteredShipsList = filteredShipsList.stream()
					.filter(ship -> ship.getSpeed() <= maxSpeed)
					.collect(Collectors.toList());
		}

		if (minCrewSize != null) {
			filteredShipsList = filteredShipsList.stream()
					.filter(ship -> ship.getCrewSize() >= minCrewSize)
					.collect(Collectors.toList());
		}

		if (maxCrewSize != null) {
			filteredShipsList = filteredShipsList.stream()
					.filter(ship -> ship.getCrewSize() <= maxCrewSize)
					.collect(Collectors.toList());
		}

		if (minRating != null) {
			filteredShipsList = filteredShipsList.stream()
					.filter(ship -> ship.getRating() >= minRating)
					.collect(Collectors.toList());
		}

		if (maxRating != null) {
			filteredShipsList = filteredShipsList.stream()
					.filter(ship -> ship.getRating() <= maxRating)
					.collect(Collectors.toList());
		}
		return filteredShipsList;
	}
}
