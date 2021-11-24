package com.laonstory.bbom.global.dto.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

public final class PageRequest {

    private int page;
    private int size;

    // getter

    public org.springframework.data.domain.PageRequest of() {
        return org.springframework.data.domain.PageRequest.of(page - 1, size);
    }
}