package com.vlad.controller;

import com.vlad.entity.MinifierUrl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UrlMinifierController {

    private Map<String, MinifierUrl> shortenUrlList = new HashMap<>();

    @RequestMapping(value = "/shortenurl", method = RequestMethod.POST)
    public ResponseEntity<Object> getShortenUrl(@RequestBody MinifierUrl minifierUrl) throws MalformedURLException {
        String randomChar = getRandomChars();
        setShortUrl(randomChar, minifierUrl);
        return new ResponseEntity<Object>(minifierUrl, HttpStatus.OK);
    }

    @RequestMapping(value = "/s/{randomstring}", method = RequestMethod.GET)
    public void getFullUrl(HttpServletResponse response, @PathVariable("randomstring") String randomString) throws IOException {
        response.sendRedirect(shortenUrlList.get(randomString).getFull_url());
    }

    private void setShortUrl(String randomChar, MinifierUrl minifierUrl) throws MalformedURLException {
        minifierUrl.setShort_url("http://localhost:8080/s/" + randomChar);
        shortenUrlList.put(randomChar, minifierUrl);
    }

    private String getRandomChars() {
        String randomStr = "";
        String possibleChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < 5; i++)
            randomStr += possibleChars.charAt((int) Math.floor(Math.random() * possibleChars.length()));
        return randomStr;
    }

}