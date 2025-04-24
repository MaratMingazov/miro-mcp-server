package com.maratmingazov.tools

import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class ItemToolsService(
    private val miroClient: RestClient,
) {

    @Tool(name = "getWidgets", description = "get the widgets on a miro board")
    fun getWidgets(
        @ToolParam(description = "Miro board key to get widgets", required = true) boardKey: String,
    ) : ResponseEntity<String> {
        return miroClient.get()
            .uri("/boards/$boardKey/items")
            .retrieve()
            .toEntity(String::class.java)
    }
}