package com.diemyolo.blog_service.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionResponse {
    private List<String> categories;
    private List<String> tags;
}
