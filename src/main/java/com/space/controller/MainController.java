package com.space.controller;

import com.space.exceptions.BadRequestException;
import com.space.exceptions.NotFoundException;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipRepository;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Date;
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
	public List<Ship> findShipsByFilter(@RequestParam(required = false) String name,
										@RequestParam(required = false) String planet,
										@RequestParam(required = false) ShipType shipType,
										@RequestParam(required = false) Date after,
										@RequestParam(required = false) Date before,
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

		if (pageNumber == null || pageSize == null || order == null) {
			return shipRepository.findAll().stream()
					.limit(3) //default pageSize
					.collect(Collectors.toList());
		}

		Comparator<Ship> orderComparator = shipService.getComparatorByOrder(order);
		if (name != null) {
			return shipRepository.findAll().stream()
					.sorted(orderComparator)
					.filter(ship -> ship.getName().toLowerCase().contains(name.toLowerCase()))
					.skip(pageNumber * pageSize)
					.limit(pageSize)
					.collect(Collectors.toList());
		}

		return shipRepository.findAll().stream()
				.sorted(orderComparator)
				.skip(pageNumber * pageSize)
				.limit(pageSize)
				.collect(Collectors.toList());

	}

	@RequestMapping(path = "rest/ships/count", method = RequestMethod.GET)
	public Long count() {
		return shipRepository.count();
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
