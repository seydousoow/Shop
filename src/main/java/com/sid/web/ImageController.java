package com.sid.web;

import com.sid.service.impl.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.apache.commons.lang3.ArrayUtils.isEmpty;
import static org.springframework.http.HttpHeaders.CACHE_CONTROL;
import static org.springframework.http.MediaType.*;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageServiceImpl imageServiceImpl;

    @GetMapping(value = "/images/by-name", produces = {IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE})
    public @ResponseBody
    byte[] getImage(@RequestParam String name,
                    @RequestParam(required = false, defaultValue = "true") boolean scale,
                    @RequestParam(required = false, defaultValue = "500") int w,
                    @RequestParam(required = false, defaultValue = "500") int h,
                    HttpServletResponse response) {
        var result = imageServiceImpl.getImage(name, scale, w, h);
        if (isEmpty(result)) throw new ResourceNotFoundException("Not valid image found.");
        else {
            response.setHeader(CACHE_CONTROL, "max-age={}, must-revalidate, no-transform"
                    .replace("{}", String.valueOf(Duration.of(30, ChronoUnit.DAYS).getSeconds())));
            response.setDateHeader(HttpHeaders.DATE, System.currentTimeMillis());
            return result;
        }
    }

}
