package com.goom.springapi2.advice;

import com.goom.springapi2.advice.exception.CEmailSigninFailException;
import com.goom.springapi2.advice.exception.CuserNotFoundException;
import com.goom.springapi2.model.response.CommonResult;
import com.goom.springapi2.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * ControllerAdvice의 annotation은 @ControllerAdvice와 @RestControllerAdvice 두가지가 있습니다.
 * 예외 발생시 json 형대로 결과를 반환하려면 @RestControllerAdvice를 클래스 선언하면 됩니다.
 * annotation에 추가로 패키지를 적용하면 특정 패키지 하위의 Controller에만 로직이 적용되게 할 수 도 있음
 *  (ex. @RestControllerAdvice(basePackage = "com.goom.springapi2.controller")
 */


@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {
    private final ResponseService responseService;
    private final MessageSource messageSource;


    @ExceptionHandler(Exception.class) // Exception이 발생하면 해당 Handler에서 처리하겠다고 명시한 annotation
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 해당 Exception이 발생하면 Response에 출력되는 HttpStatus Code가 500으로 내려가도록 설정.
    protected CommonResult defaultExcepton(HttpServletRequest request, Exception e){
        return responseService.getFailResult(Integer.valueOf("unKnown.code"), getMessage("unKnown.msg")); // Exception 발생 시 이미 만들어둔 CommonResult의 실패 결과를 json 행태로 출력하도록 설정.
    }

    @ExceptionHandler(CuserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, Exception e){
        return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }

    @ExceptionHandler(CEmailSigninFailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailSigninFailed(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("emailSigninFailed.code")), getMessage("emailSigninFailed.msg"));
    }


    //code 정보에 해당하는 메시지를 조회
    private String getMessage(String code) {
        return getMessage(code, null);
    }
    //code 정보, 추가 argument로 현재 locale에 맞는 메시지 조회
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

}
