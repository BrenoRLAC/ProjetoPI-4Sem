package com.projeto.watchflix.controller;


import com.projeto.watchflix.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/channel")
@RequiredArgsConstructor
public class ChannelController {

    @Autowired
    private final UserService userService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestParam(value="file") MultipartFile file,
                         @RequestParam(value="firstName", required = true) String firstName,
                         @RequestParam(value="lastName", required = true)String lastName,
                         @RequestParam(value="fullName", required = true)String fullName,
                         @RequestParam(value="email", required = true)String email,
                         @RequestParam(value="password", required = true)String password) throws IOException {

        userService.registerChannel(file, firstName,lastName,fullName,email,password);

  }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> findChannel() {
        return userService.findChannel();
   }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/id")
    public Object findChannelById(@RequestParam(value="id", required= true) Long id) {
        return userService.findChannelById(id);
    }


    @DeleteMapping("/id")
    @ResponseStatus(HttpStatus.OK)
    public void deleteChannel(@RequestParam(value="id", required= true) Long id){
             userService.deleteChannel(id);
    }

}

