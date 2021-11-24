package com.laonstory.bbom.global.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PagingResponse<T> {



    private int currentPage;

    private int totalPage;

    private Long totalCount;

    private List<T> dataList;

    public PagingResponse(Page<T> list){

        this.dataList = list.getContent();

        this.currentPage = list.getPageable().getPageNumber() + 1;

        this.totalCount = list.getTotalElements();

        this.totalPage = list.getTotalPages();


    }
}
