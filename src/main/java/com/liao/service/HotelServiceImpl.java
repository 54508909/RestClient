package com.liao.service;

import com.liao.mapper.HotelMapper;
import com.liao.pojo.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl implements HotelService{

    @Autowired
    private HotelMapper hotelMapper;

    @Override
    public Hotel getHotel(Integer id) {
        return hotelMapper.getHotel(id);
    }

    @Override
    public List<Hotel> getHotelList() {
        return hotelMapper.getHotelList();
    }

    @Override
    public List<Hotel> getHotelList2() {
        return null;
    }
}
