package com.jaspercloud.shipkeeper.service;

import com.jaspercloud.shipkeeper.dto.LocationInfoDTO;
import com.jaspercloud.shipkeeper.support.geohash.WGS84Point;

public interface LocationService {

    LocationInfoDTO getLocationInfo(WGS84Point wgs84Point);
}
