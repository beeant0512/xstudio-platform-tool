package com.xstudio.tool.location.amap;

import com.xstudio.tool.location.amap.convert.ConvertResponse;
import com.xstudio.tool.location.amap.convert.Coordsys;
import com.xstudio.tool.location.amap.geocode.geo.GeoResponse;
import com.xstudio.tool.location.amap.weather.WeatherResponse;
import com.xstudio.tool.utils.Msg;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AmapWebApiTest {

    @BeforeEach
    public void before() throws Exception {
        AmapWebApi.setKey("219238adb7877e62dc85cfbaf07d1883");
    }

    @Test
    public void testGeo() throws Exception {
        Msg<GeoResponse> msg = AmapWebApi.geo("北京市朝阳区阜通东大街6号", "北京", null, null, null);
        Assertions.assertTrue(msg.getSuccess());
    }

    @Test
    void weather() {
        Msg<WeatherResponse> msg = AmapWebApi.weather("110101", "all", null);
        Assertions.assertTrue(msg.getSuccess());
    }

    @Test
    void weatherFailed() {
        Msg<WeatherResponse> msg = AmapWebApi.weather("110101", "asdfasdf", null);
        Assertions.assertFalse(msg.getSuccess());
    }

    @Test
    void convert() {
        Msg<ConvertResponse> msg = AmapWebApi.convert("116.481499,39.990475", Coordsys.gps);
        Assertions.assertFalse(msg.getSuccess());
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme