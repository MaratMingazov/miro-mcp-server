package com.maratmingazov.tools

import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient

@Service
class StickyNoteToolsService(
    private val miroClient: RestClient,
) {

    @Tool(name = "createSticky", description = "create a sticky note widget (sticker) on a miro board")
    fun createSticky(
        @ToolParam(description = "Miro board key for a sticker creation", required = true) boardKey: String,
        @ToolParam(description = "The text to be displayed on the sticker") stickyNoteText: String = "",
        @ToolParam(description = "Sticker position x coordinate") x: Int = 0,
        @ToolParam(description = "Sticker position y coordinate") y: Int = 0,
        @ToolParam(description = "Sticker width. Sticker height is equal to width") width: Int = 200,
        @ToolParam(description = "Sticker fill color") fillColor: FillColor = FillColor.YELLOW,
    ): ResponseEntity<String> {
        val request = CreateStickyRequest(
            data = StickyData(content = stickyNoteText),
            style = StickyStyle(fillColor = fillColor.color),
            geometry = Geometry(width = width),
            position = Position(x, y),
        )

        return miroClient.post()
            .uri("/boards/$boardKey/sticky_notes")
            .contentType(APPLICATION_JSON)
            .body(request)
            .retrieve()
            .toEntity(String::class.java)
    }
}

data class StickyData(val content: String)
data class StickyStyle(val fillColor: String)
data class Position(val x: Int, val y: Int)
data class Geometry(val width: Int)
data class CreateStickyRequest(
    val data: StickyData,
    val style: StickyStyle,
    val position: Position,
    val geometry: Geometry,
)

enum class FillColor(val color: String) {
    GRAY("gray"),
    YELLOW("yellow"),
    LIGHT_YELLOW("light_yellow"),
    ORANGE("orange"),
    GREEN("green"),
    LIGHT_GREEN("light_green"),
    DARK_GREEN("dark_green"),
    PINK("pink"),
    LIGHT_PINK("light_pink"),
    RED("red"),
    BLUE("blue"),
    BLACK("black"),
}