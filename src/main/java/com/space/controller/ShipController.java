package com.space.controller;

import com.space.model.Ship;
import com.space.repository.ShipRepository;
import com.space.service.ShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShipController {

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

		List<Ship> filteredList = shipService.getFilteredShipsList(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed,
				minCrewSize, maxCrewSize, minRating, maxRating);

		return shipService.getSortedShipsList(filteredList, pageNumber, pageSize, order);
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
					  @RequestParam(required = false) Double maxRating) {

		return shipService.getFilteredShipsList(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed,
				minCrewSize, maxCrewSize, minRating, maxRating).size();
	}

	@RequestMapping(path = "/rest/ships/{id}", method = RequestMethod.GET)
	public Ship getShip(@PathVariable Long id) {
		return shipService.getShipById(id);
	}

	@RequestMapping(path = "/rest/ships/", method = RequestMethod.POST)
	public @ResponseBody Ship createShip
			(@RequestBody Ship jsonBody) {
		Ship ship = shipService.prepareBodyToCreate(jsonBody);
		shipRepository.save(ship);
		return ship;
	}

	@RequestMapping(path = "/rest/ships/{id}", method = RequestMethod.POST)
	public @ResponseBody Ship updateShip
			(@RequestBody Ship jsonBody, @PathVariable Long id) {
		Ship shipToUpdate = shipService.getShipById(id);
		if (shipService.updateShip(shipToUpdate, jsonBody)) {
			shipRepository.save(shipToUpdate);
		}
		return shipToUpdate;
	}

	@RequestMapping(path = "/rest/ships/{id}", method = RequestMethod.DELETE)
	public void deleteShip(@PathVariable Long id) {
		shipRepository.delete(getShip(id));
	}
}
