package com.laonstory.bbom.global.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class PagingInfo<T> {

    private int currentPage;
    private int totalPage;
    private Long totalCount;


    public PagingInfo(Page<T> list){


        this.currentPage = list.getPageable().getPageNumber();

        this.totalCount = list.getTotalElements();

        this.totalPage = list.getTotalPages();


    }

}
