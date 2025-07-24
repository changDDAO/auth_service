package com.changddao.auth_service.service;

import com.changddao.auth_service.dto.response.MultipleResult;
import com.changddao.auth_service.dto.response.Result;
import com.changddao.auth_service.dto.response.SingleResult;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ResponseService {
    //단일 결과 처리
    public <T> SingleResult<T> handleSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    // 다중 결과 처리
    public <T> MultipleResult<T> handleListResult(List<T> list) {
        MultipleResult<T> result = new MultipleResult<>();
        result.setDatas(list);
        setSuccessResult(result);
        return result;
    }

    //결과 처리
    public Result handleSuccessResult() {
        Result result = new Result();
        setSuccessResult(result);
        return result;
    }

    //실패 결과 처리하기
    public Result handleFailResult(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    private void setSuccessResult(Result result) {
        result.setSuccess(true);
        result.setCode(200);
        result.setMsg("success");
    }
}

