package com.liao.service;

import com.liao.pojo.Hotel;

import java.util.List;

public interface HotelService {

     Hotel getHotel(Integer id);

     List<Hotel> getHotelList();
}
