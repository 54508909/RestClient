package com.liao.mapper;

import com.liao.pojo.Hotel;
import com.liao.service.HotelService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface HotelMapper {

    Hotel getHotel(@Param("id") Integer id);

    List<Hotel> getHotelList();

}
