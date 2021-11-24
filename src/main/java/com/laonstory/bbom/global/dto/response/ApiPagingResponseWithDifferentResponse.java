package com.laonstory.bbom.global.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiPagingResponseWithDifferentResponse<T,P> {




    private T model;

    private PagingResponse<P> response;



}
