package com.jaspercloud.shipkeeper.controller;

import com.jaspercloud.shipkeeper.dto.LocationInfoDTO;
import com.jaspercloud.shipkeeper.service.LocationService;
import com.jaspercloud.shipkeeper.support.geohash.WGS84Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/info")
    public LocationInfoDTO getLocationInfo(@RequestParam("lat") Double lat, @RequestParam("lng") Double lng) {
        LocationInfoDTO locationInfo = locationService.getLocationInfo(new WGS84Point(lat, lng));
        return locationInfo;
    }
}
