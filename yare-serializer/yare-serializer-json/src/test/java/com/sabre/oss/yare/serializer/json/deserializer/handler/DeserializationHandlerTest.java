package com.sabre.oss.yare.serializer.json.deserializer.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.sabre.oss.yare.serializer.json.model.Operand;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeserializationHandlerTest {
    @Test
    void shouldHandleJsonWithApplicableHandler()
            throws JsonProcessingException {
        //given
        JsonNode node = mock(JsonNode.class);
        Operand expected = mock(Operand.class);

        DeserializationHandler handler =
                mockDeserializationHandler(true, expected);

        //when
        Operand result = handler.handle(node, null);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldHandleJsonWithFirstApplicableHandler()
            throws JsonProcessingException {
        //given
        JsonNode node = mock(JsonNode.class);
        Operand expected = mock(Operand.class);

        DeserializationHandler handler =
                mockNotApplicableDeserializationHandler()
                        .withNext(mockApplicableDeserializationHandler(expected));

        //when
        Operand result = handler.handle(node, null);

        //then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void shouldThrownAnExceptionWhenApplicableHandlerCannotBeFound()
            throws JsonProcessingException {
        //given
        JsonNode node = mock(JsonNode.class);
        when(node.toString()).thenReturn("{ TEST NODE }");

        DeserializationHandler handler =
                mockNotApplicableDeserializationHandler()
                        .withNext(mockNotApplicableDeserializationHandler());

        //when / then
        String expectedMessage = "Given node: { TEST NODE } could not be deserialized to any known operand model";
        assertThatThrownBy(() -> handler.handle(node, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    private DeserializationHandler mockNotApplicableDeserializationHandler()
            throws JsonProcessingException {
        return mockDeserializationHandler(false, null);
    }

    private DeserializationHandler mockApplicableDeserializationHandler(Operand producedResult)
            throws JsonProcessingException {
        return mockDeserializationHandler(true, producedResult);
    }

    private DeserializationHandler mockDeserializationHandler(Boolean isApplicable, Operand producedResult)
            throws JsonProcessingException {
        DeserializationHandler handler = mock(DeserializationHandler.class, CALLS_REAL_METHODS);
        when(handler.isApplicable(any()))
                .thenReturn(isApplicable);
        when(handler.deserialize(any(), any()))
                .thenReturn(producedResult);
        return handler;
    }
}
