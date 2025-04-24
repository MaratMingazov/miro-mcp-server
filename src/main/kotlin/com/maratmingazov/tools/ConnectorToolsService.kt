package com.maratmingazov.tools

import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class ConnectorToolsService(
    private val miroClient: RestClient,
) {

    @Tool(name = "createConnector", description = "create a connector (line) widget between 2 specified widgets")
    fun createConnector(
        @ToolParam(description = "Miro board key to get widgets", required = true) boardKey: String,
        @ToolParam(description = "first widget id for connector", required = true) firstWidgetId: String,
        @ToolParam(description = "second widget id for connector", required = true) secondWidgetId: String,
        @ToolParam(description = "the text to be displayed on connector") connectorText: String = "",
    ) : ResponseEntity<String> {
        val request = CreateConnectorRequest(
            startItem = ConnectorItem(firstWidgetId.toLong()),
            endItem = ConnectorItem(secondWidgetId.toLong()),
            captions = listOf(ConnectorCaptions(connectorText))
        )
        return miroClient.post()
            .uri("/boards/$boardKey/connectors")
            .contentType(APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toEntity(String::class.java)
    }
}

data class ConnectorItem(val id: Long)
data class ConnectorCaptions(val content: String)
data class CreateConnectorRequest(
    val startItem: ConnectorItem,
    val endItem: ConnectorItem,
    val captions: List<ConnectorCaptions>
)