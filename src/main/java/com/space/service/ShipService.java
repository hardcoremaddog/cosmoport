package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShipService {

	@Autowired
	ShipRepository shipRepository;

	public Comparator<Ship> getComparatorByOrder(String order) {
		Comparator<Ship> comparator = null;
		switch (ShipOrder.valueOf(order)) {
			case ID: {
				comparator = Comparator.comparing(Ship::getId);
				break;
			}
			case SPEED: {
				comparator = Comparator.comparing(Ship::getSpeed);
				break;
			}
			case DATE: {
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
										   Integer minCrewSize,
										   Integer maxCrewSize,
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

	public List<Ship> getSortedShipsList(List<Ship> list,
										 Integer pageNumber,
										 Integer pageSize,
										 String order) {
		//SORTING

		//no param
		if (pageSize == null && pageNumber == null && order == null) {
			return list.stream()
					.limit(3) //default pageSize
					.collect(Collectors.toList());
		}

		//ONLY pageSize != null
		if (pageSize != null && pageNumber == null && order == null) {
			return list.stream()
					.limit(pageSize) //pageSize
					.collect(Collectors.toList());
		}

		//ONLY pageSize != null
		if (pageSize == null && pageNumber != null && order == null) {
			return list.stream()
					.skip(pageNumber * 3)
					.limit(3) //default pageSize
					.collect(Collectors.toList());
		}

		//ONLY order != null
		if (pageSize == null && pageNumber == null) {
			return list.stream()
					.sorted(getComparatorByOrder(order))
					.limit(3) //default pageSize
					.collect(Collectors.toList());
		}

		//never happens
		return list;
	}


//	public Ship JSONtoJava(String jsonBody) {
//
//		Ship ship = new Ship();
//
//		ObjectMapper mapper = new ObjectMapper();
//		try {
//			ship = mapper.readValue(jsonBody, Ship.class);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
////		Мы не можем создать корабль, если:
////		- указаны не все параметры из Data Params (кроме isUsed);
//		if (ship.getName() == null
//				|| ship.getPlanet() == null
//				|| ship.getShipType() == null
//				|| ship.getProdDate() == null
//				|| ship.getSpeed() == null
//				|| ship.getCrewSize() == null) {
//			throw new BadRequestException();
//		}
//
//// 		Если в запросе на создание корабля нет параметра “isUsed”, то считаем,
////		что пришло значение “false”
//		Boolean isUsed = false;
//		if (ship.isUsed() != null) {
//			isUsed = ship.isUsed();
//		}
//
//		String name = ship.getName();
//		String planet = ship.getPlanet();
//		ShipType shipType = ship.getShipType();
//		Long prodDate = ship.getProdDate();
//		Double speed = ship.getSpeed();
//		Integer crewSize = ship.getCrewSize();
//
//
////		- длина значения параметра “name” или “planet” превышает
////		размер соответствующего поля в БД (50 символов);
//		if (name.length() > 50 || planet.length() > 50) {
//			throw new BadRequestException();
//		}
////		- значение параметра “name” или “planet” пустая строка;
//		if (name.equals("") || planet.equals("")) {
//			throw new BadRequestException();
//		}
//
////		- скорость или размер команды находятся вне заданных
////		пределов;
//		if (speed < 0.01 || speed > 0.99
//		|| crewSize < 1 || crewSize > 9999) {
//			throw new BadRequestException();
//		}
//
////		- “prodDate”:[Long] < 0;
//		if (prodDate < 0) {
//			throw new BadRequestException();
//		}
//
////		- год производства находятся вне заданных пределов.;
//		Calendar dateMinCal = Calendar.getInstance();
//		Calendar dateMaxCal = Calendar.getInstance();
//
//		dateMinCal.set(2800, Calendar.JANUARY, 1);
//		dateMaxCal.set(3019, Calendar.DECEMBER, 31);
//
//
//		if (prodDate < dateMinCal.getTimeInMillis() || prodDate > dateMaxCal.getTimeInMillis()) {
//			throw new BadRequestException();
//		}
//
//		//calculateRating
//		double k;
//		if (isUsed) {
//			k = 0.5;
//		} else {
//			k = 1D;
//		}
//
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(prodDate);
//		Integer prodYear = calendar.get(Calendar.YEAR);
//
//		Double rating = (80 * speed * k) / (3019 - prodYear + 1);
//
//		ship.setRating(rating);
//
//		//OK:
//		return ship;
//	}
}