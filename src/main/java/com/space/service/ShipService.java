package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class ShipService {

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
}
