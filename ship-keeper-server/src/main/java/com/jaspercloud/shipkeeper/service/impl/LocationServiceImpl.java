package com.jaspercloud.shipkeeper.service.impl;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.jaspercloud.shipkeeper.dto.LocationInfoDTO;
import com.jaspercloud.shipkeeper.exception.HttpException;
import com.jaspercloud.shipkeeper.service.LocationService;
import com.jaspercloud.shipkeeper.support.geohash.WGS84Point;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class LocationServiceImpl implements LocationService {

    @Value("${tencent.map.key}")
    private String key;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    @Override
    public LocationInfoDTO getLocationInfo(WGS84Point wgs84Point) {
        String url = UriComponentsBuilder.fromHttpUrl("https://apis.map.qq.com/ws/geocoder/v1")
                .queryParam("key", key)
                .queryParam("location", String.format("%f,%f", wgs84Point.getLatitude(), wgs84Point.getLongitude()))
                .queryParam("output", "json").build().toUriString();
        ResponseEntity<String> entity = restTemplate.getForEntity(url, String.class);
        if (HttpStatus.OK.value() != entity.getStatusCodeValue()) {
            throw new HttpException(url);
        }
        String body = entity.getBody();
        ResponseJson responseJson = gson.fromJson(body, ResponseJson.class);
        if (0 != responseJson.getStatus()) {
            throw new HttpException(responseJson.toString());
        }
        Result result = responseJson.getResult();
        FormattedAddresses formattedAddresses = result.getFormattedAddresses();
        AdInfo adInfo = result.getAdInfo();
        String address;
        if (null != formattedAddresses && StringUtils.isNotEmpty(formattedAddresses.getRecommend())) {
            address = formattedAddresses.getRecommend();
        } else {
            address = result.getAddress();
        }
        LocationInfoDTO locationInfoDTO = new LocationInfoDTO();
        locationInfoDTO.setLat(wgs84Point.getLatitude());
        locationInfoDTO.setLng(wgs84Point.getLongitude());
        locationInfoDTO.setAddress(address);
        locationInfoDTO.setProvince(adInfo.getProvince());
        locationInfoDTO.setCity(adInfo.getCity());
        return locationInfoDTO;
    }

    private static class ResponseJson {

        private Integer status;
        private String message;
        private String address;
        private Result result;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }
    }

    private static class Result {

        private String address;
        @SerializedName("formatted_addresses")
        private FormattedAddresses formattedAddresses;
        @SerializedName("ad_info")
        private AdInfo adInfo;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public FormattedAddresses getFormattedAddresses() {
            return formattedAddresses;
        }

        public void setFormattedAddresses(FormattedAddresses formattedAddresses) {
            this.formattedAddresses = formattedAddresses;
        }

        public AdInfo getAdInfo() {
            return adInfo;
        }

        public void setAdInfo(AdInfo adInfo) {
            this.adInfo = adInfo;
        }
    }

    private static class FormattedAddresses {

        private String recommend;
        private String rough;

        public String getRecommend() {
            return recommend;
        }

        public void setRecommend(String recommend) {
            this.recommend = recommend;
        }

        public String getRough() {
            return rough;
        }

        public void setRough(String rough) {
            this.rough = rough;
        }
    }

    private static class AdInfo {

        private String nation;
        private String province;
        private String city;
        private String district;

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }
    }

}
