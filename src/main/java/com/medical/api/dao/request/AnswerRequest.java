package com.medical.api.dao.request;

import com.medical.api.domain.HistoryItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequest {
    private String question;
    private List<HistoryItem> history;
}
