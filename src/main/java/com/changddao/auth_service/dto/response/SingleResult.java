package com.changddao.auth_service.dto.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SingleResult<T> extends Result{
    T data;
}
