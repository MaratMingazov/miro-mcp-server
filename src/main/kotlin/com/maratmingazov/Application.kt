package com.maratmingazov

import com.maratmingazov.tools.ConnectorToolsService
import com.maratmingazov.tools.ItemToolsService
import com.maratmingazov.tools.StickyNoteToolsService
import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.ai.tool.method.MethodToolCallbackProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@SpringBootApplication
class Application {

    @Bean
    fun tools(
        stickyNoteTools: StickyNoteToolsService,
        itemToolsService: ItemToolsService,
        connectorToolsService: ConnectorToolsService,
    ): ToolCallbackProvider {
        return MethodToolCallbackProvider.builder()
            .toolObjects(
                stickyNoteTools,
                itemToolsService,
                connectorToolsService
            ).build()
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Configuration
class MiroConfig(
    /**
     * Spring Boot обрабатывает аргументы командой строки и положит сюда значение токена
     * Другой способ, можно передать этот аргумент через application.yml : token: your_token_here
     */
    @Value("\${MIRO_TOKEN}") private val token: String
) {

    @Bean
    fun miroClient() = RestClient.builder()
        .baseUrl("https://api.miro.com/v2/")
        .defaultHeader("Accept", "application/json")
        .defaultHeader("content-type", "application/json")
        .defaultHeader("authorization", "Bearer $token")
        .build()
}