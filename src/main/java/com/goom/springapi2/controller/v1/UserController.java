package com.goom.springapi2.controller.v1;

import com.goom.springapi2.advice.exception.CuserNotFoundException;
import com.goom.springapi2.entity.User;
import com.goom.springapi2.model.response.CommonResult;
import com.goom.springapi2.model.response.ListResult;
import com.goom.springapi2.model.response.SingleResult;
import com.goom.springapi2.repo.UserJpaRepo;
import com.goom.springapi2.service.ResponseService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. User"})
//class 상단에 선언하면 class 내부에 final로 선언된 객체에 대해서 constructor Injection을 수행.
//해당 annotation을 사용하지 않고 선언된 객체에 @Autowired 를 사용해도 됨
@RequiredArgsConstructor
@RestController // 결과값을 JSON으로 출력합니다.
@RequestMapping(value = "/v1")
public class UserController {
    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService; // 결과를 처리할 service


    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 리스트 조회" , notes = "모든 회원을 조회한다.")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser(){
        //return userJpaRepo.findAll(); // select msrl, name, uid form user;
        //결과 데이터가 여러 건인 경우 getListResult 를 이용해서 결과를 출력
        return responseService.getListResult(userJpaRepo.findAll());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 단건 조회", notes = "회원 번호(msrl)로 회원을 조회")
    @GetMapping(value = "/user")
    public SingleResult<User> findUserById(@ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
        /*
        //결과 데이터가 단일건인 경우 getBasicResult를 이용해서 결과를 출력
        //return responseService.getSingleResult(userJpaRepo.getById(msrl));
        return responseService.getSingleResult(userJpaRepo.findById((long) userId).orElseThrow(CuserNotFoundException::new));
        */
        //SecurityContext에서 인증받은 회원의 정보를 얻어옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        //결과 데이터가 단일건인 경우 getSingleResult를 이용해서 결과 출력
        return responseService.getSingleResult(userJpaRepo.findByUid(id).orElseThrow(CuserNotFoundException::new));
    }


    @ApiOperation(value = "회원 입력" ,notes ="회원을 입력한다.")
    @PostMapping(value = "/user")
    public SingleResult<User> save(@ApiParam(value = "회원 아이디" , required = true) @RequestParam String uid,
                     @ApiParam(value = "회원 이름", required = true) @RequestParam String name){
        User user = User.builder()
                .uid(uid)
                .name(name)
                .build();
        //return userJpaRepo.save(user); //insert into user( msrl, name, uid) values(null,?,?)
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN" ,value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 수정", notes = "회원 정보를 수정한다.")
    @PutMapping(value = "/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원 번호" , required = true) @RequestParam int msrl,
            //@ApiParam(value = "회원 아이디" , required = true) @RequestParam String uid,
            @ApiParam(value = "회원 이름" , required = true) @RequestParam String name)
    {
        User user = User.builder()
                .msrl(msrl)
              //.uid(uid)
                .name(name)
                .build();
        return responseService.getSingleResult(userJpaRepo.save(user));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "회원 삭제", notes = "userId로 회원 정보를 삭제한다.")
    @DeleteMapping(value = "/user/{msrl}")
    public CommonResult delete(
           @ApiParam(value = "회원번호", required = true) @PathVariable int msrl)
    {
        userJpaRepo.deleteById(msrl);
        //성공 결과 정보만 필요한 경우 getSuccessResult()를 이용하여 결과를 출력
        return responseService.getSuccessResult();
    }

}

