package org.zgl.dto.clinet.game2;


import org.zgl.build.desc.Protostuff;
import org.zgl.dto.SerializeMessage;

import java.util.List;

@Protostuff("game2")
public class Game2HistoryListDto implements SerializeMessage {
    private List<Game2HistoryDto> history;

    public Game2HistoryListDto() {
    }

    public Game2HistoryListDto(List<Game2HistoryDto> history) {
        this.history = history;
    }

    public List<Game2HistoryDto> getHistory() {
        return history;
    }

    public void setHistory(List<Game2HistoryDto> history) {
        this.history = history;
    }
}
