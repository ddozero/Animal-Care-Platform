package com.animal.api.animal.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.user.service.UserService;

@Service
@Primary
public class UserAnimalServiceImple implements UserService, UserAnimalService {

}
