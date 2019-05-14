package com.space.controller;

import com.space.exceptions.BadRequestException;
import com.space.exceptions.NotFoundException;
import com.space.model.Ship;
import com.space.repository.ShipRepository;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MainController {

	@Autowired
	private ShipRepository shipRepository;

	@Autowired
	private ShipService shipService;

	@RequestMapping(path = "/rest/ships", method = RequestMethod.GET)
	public List<Ship> getSortedShipsList(@RequestParam(required = false) String name,
										 @RequestParam(required = false) String planet,
										 @RequestParam(required = false) String shipType,
										 @RequestParam(required = false) Long after,
										 @RequestParam(required = false) Long before,
										 @RequestParam(required = false) Boolean isUsed,
										 @RequestParam(required = false) Double minSpeed,
										 @RequestParam(required = false) Double maxSpeed,
										 @RequestParam(required = false) Integer minCrewSize,
										 @RequestParam(required = false) Integer maxCrewSize,
										 @RequestParam(required = false) Double minRating,
										 @RequestParam(required = false) Double maxRating,
										 @RequestParam(required = false) Integer pageNumber,
										 @RequestParam(required = false) Integer pageSize,
										 @RequestParam(required = false) String order) {

		//no params
		if (pageNumber == null || pageSize == null || order == null) {
			return shipService.getFilteredShipsList(name, planet, shipType, after, before,
					isUsed, minSpeed, maxSpeed, maxCrewSize, minCrewSize, minRating, maxRating).stream()
					.limit(3) //default pageSize
					.collect(Collectors.toList());
		}

		Comparator<Ship> orderComparator = shipService.getComparatorByOrder(order);

		return shipService.getFilteredShipsList(name,
				planet, shipType, after, before,  isUsed, minSpeed, maxSpeed,
				maxCrewSize, minCrewSize, minRating, maxRating).stream()
				.sorted(orderComparator)
				.skip(pageNumber * pageSize)
				.limit(pageSize)
				.collect(Collectors.toList());

	}

	@RequestMapping(path = "rest/ships/count", method = RequestMethod.GET)
	public Integer count(@RequestParam(required = false) String name,
					  @RequestParam(required = false) String planet,
					  @RequestParam(required = false) String shipType,
					  @RequestParam(required = false) Long after,
					  @RequestParam(required = false) Long before,
					  @RequestParam(required = false) Boolean isUsed,
					  @RequestParam(required = false) Double minSpeed,
					  @RequestParam(required = false) Double maxSpeed,
					  @RequestParam(required = false) Integer minCrewSize,
					  @RequestParam(required = false) Integer maxCrewSize,
					  @RequestParam(required = false) Double minRating,
					  @RequestParam(required = false) Double maxRating,
					  @RequestParam(required = false) Integer pageNumber,
					  @RequestParam(required = false) Integer pageSize,
					  @RequestParam(required = false) String order) { //TODO we need Integer
		return shipService.getFilteredShipsList(name,
				planet, shipType, after, before, isUsed, minSpeed, maxSpeed,
				maxCrewSize, minCrewSize, minRating, maxRating).size();
	}

	@RequestMapping(path = "/rest/ships/{id}", method = RequestMethod.GET)
	public Ship getShip(@PathVariable Long id,
						@RequestParam(required = false) String name,
						@RequestParam(required = false) Integer pageNumber,
						@RequestParam(required = false) Integer pageSize,
						@RequestParam(required = false) String order) {
		if (id == 0) {
			throw new BadRequestException();
		}

		return shipRepository.findAll().stream()
				.filter(ship -> ship.getId().equals(id))
				.findFirst()
				.orElseThrow(NotFoundException::new);
	}
}
