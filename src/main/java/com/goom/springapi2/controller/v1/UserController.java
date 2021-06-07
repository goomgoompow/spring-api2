package com.goom.springapi2.controller.v1;

import com.goom.springapi2.entity.User;
import com.goom.springapi2.repo.UserJpaRepo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = {"1. User"})

//class 상단에 선언하면 class 내부에 final로 선언된 객체에 대해서 contrucrtor Injection을 수행.
//해당 annotation을 사요하지 않고 선언된 객체에 @Autowired 를 사용해도 됨
@RequiredArgsConstructor
@RestController // 결과값을 JSON으로 출력합니다.
@RequestMapping(value = "/v1")
public class UserController {
    private final UserJpaRepo userJpaRepo;

    @ApiOperation(value = "Select user" , notes = "searches all users.")
    @GetMapping(value = "/user")
    public List<User> findAllUser(){
        return userJpaRepo.findAll(); // select msrl, name, uid form user;
    }

    @ApiOperation(value = "insert user" ,notes ="inserts user info")
    @PostMapping(value = "/user")
    public User save(@ApiParam(value = "회원 아이디" , required = true) @RequestParam String uid,
                     @ApiParam(value = "회원 이름", required = true) @RequestParam String name){
        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();
        return userJpaRepo.save(user); //insert into user( msrl, name, uid) values(null,?,?)
    }
}

