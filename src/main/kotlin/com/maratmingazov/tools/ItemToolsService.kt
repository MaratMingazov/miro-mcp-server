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

    @Tool(name = "getItems", description = Descriptions.TOOL_GET_ITEMS)
    fun getItems(
        @ToolParam(description = Descriptions.PARAM_BOARD_KEY, required = true) boardKey: String,
        @ToolParam(description = Descriptions.PARAM_LIMIT, required = true) limit: String = "10",
        @ToolParam(description = Descriptions.PARAM_CURSOR, required = false) cursor: String? = null,
    ): ResponseEntity<String> {
        val uriBuilder = StringBuilder("/boards/$boardKey/items?limit=$limit")
        if (!cursor.isNullOrBlank()) {
            uriBuilder.append("&cursor=$cursor")
        }

        return miroClient.get()
            .uri(uriBuilder.toString())
            .retrieve()
            .toEntity(String::class.java)
    }

    @Tool(name = "getItem", description = Descriptions.TOOL_GET_ITEM)
    fun getItem(
        @ToolParam(description = Descriptions.PARAM_BOARD_KEY, required = true) boardKey: String,
        @ToolParam(description = Descriptions.PARAM_ITEM_ID, required = true) itemId: String,
    ): ResponseEntity<String> {
        return miroClient.get()
            .uri("/boards/$boardKey/items/$itemId")
            .retrieve()
            .toEntity(String::class.java)
    }

}

object Descriptions {
    const val TOOL_GET_ITEMS = """
        Retrieves a list of items for a specific board. 
        You can retrieve all items on the board, a list of child items inside a parent item, or a list of specific types of items by specifying URL query parameter values. 
        This method returns results using a cursor-based approach. 
        A cursor-paginated method returns a portion of the total set of results based on the limit specified and a cursor that points to the next portion of the results. 
        To retrieve the next portion of the collection, on your next call to the same method, set the cursor parameter equal to the cursor value you received in the response of the previous request. 
        For example, if you set the limit query parameter to 10 and the board contains 20 objects, the first call will return information about the first 10 objects in the response along with a cursor parameter and value. 
        In this example, let's say the cursor parameter value returned in the response is foo. 
        If you want to retrieve the next set of objects, on your next call to the same method, set the cursor parameter value to foo. 
    """

    const val TOOL_GET_ITEM = """Retrieves information for a specific item on a board."""

    const val PARAM_BOARD_KEY = """Unique identifier (ID) of the board for which you want to retrieve the list of available items. """

    const val PARAM_ITEM_ID = """Unique identifier (ID) of the item that you want to retrieve."""

    const val PARAM_LIMIT = """
        The maximum number of results to return per call. 
        If the number of items in the response is greater than the limit specified, the response returns the cursor parameter with a value. 
        MIN VALUE = 10. 
        MAX VALUE = 50. 
        DEFAULT VALUE = 10. 
    """

    const val PARAM_CURSOR = """
        A cursor-paginated method returns a portion of the total set of results based on the limit specified and a cursor that points to the next portion of the results. 
        To retrieve the next portion of the collection, set the cursor parameter equal to the cursor value you received in the response of the previous request. 
    """
}