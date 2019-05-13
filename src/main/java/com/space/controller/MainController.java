package com.space.controller;

import com.space.exceptions.BadRequestException;
import com.space.exceptions.NotFoundException;
import com.space.model.Ship;
import com.space.repository.ShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MainController {

	@Autowired
	private ShipRepository shipRepository;

	@RequestMapping(path = "/rest/ships", method = RequestMethod.GET)
	public List<Ship> findShipsByFilter(@RequestParam(required = false) String name,
										@RequestParam(required = false) Integer pageNumber,
										@RequestParam(required = false) Integer pageSize,
										@RequestParam(required = false) String order) {

		if (pageNumber == null || pageSize == null || order == null) {
			return shipRepository.findAll();
		}

		if (name != null) {
			return shipRepository.findAll().stream()
					.filter(ship -> ship.getName().toLowerCase().contains(name.toLowerCase()))
					.skip(pageNumber * pageSize)
					.limit(pageSize)
					.collect(Collectors.toList());
		}

		return shipRepository.findAll().stream()
				.skip(pageNumber * pageSize)
				.limit(pageSize)
//				.sorted() TODO configure enum
				.collect(Collectors.toList());
	}

	@RequestMapping(path = "rest/ships/count", method = RequestMethod.GET)

	public Integer count() {
		return shipRepository.findAll().size();
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

		return findShipsByFilter(name, pageNumber, pageSize, order).stream()
				.filter(ship -> ship.getId().equals(id))
				.findFirst()
				.orElseThrow(NotFoundException::new);
	}
}
