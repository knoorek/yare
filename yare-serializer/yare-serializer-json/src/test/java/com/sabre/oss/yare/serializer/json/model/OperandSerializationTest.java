/*
 * MIT License
 *
 * Copyright 2018 Sabre GLBL Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.sabre.oss.yare.serializer.json.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sabre.oss.yare.serializer.json.RuleToJsonConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class OperandSerializationTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.objectMapper = RuleToJsonConverter.getObjectMapper();
    }

    @ParameterizedTest
    @MethodSource("conversionParams")
    void shouldProperlySerializeOperand(Operand expression, String expected) throws JsonProcessingException {
        System.out.println(ValueSerializationTest.TestClass.class.getName());
        String serialized = objectMapper.writeValueAsString(expression);

        assertThatJson(serialized).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("conversionParams")
    void shouldProperlyDeserializeOperand(Operand expected, String json) throws IOException {
        Operand operand = objectMapper.readValue(json, Operand.class);

        assertThat(operand).isEqualTo(expected);
    }

    private static Stream<Arguments> conversionParams() {
        return Stream.of(
                Arguments.of(createValueOperandInModel(), createValueOperandInJson()),
                Arguments.of(createValuesOperandInModel(), createValuesOperandInJson()),
                Arguments.of(createFunctionOperandInModel(), createFunctionOperandInJson()),
                Arguments.of(createOperatorOperandInModel(), createOperatorOperandInJson())
        );
    }

    private static Operand createValueOperandInModel() {
        return new Value()
                .withValue("test-value");
    }

    private static String createValueOperandInJson() {
        return "" +
                "{" +
                "  \"value\" : \"test-value\"" +
                "}";
    }

    private static Operand createValuesOperandInModel() {
        return new Values()
                .withValues(Arrays.asList(
                        new Value().withValue("test-value").withType("java.lang.String")
                ))
                .withType("test-type");
    }

    private static String createValuesOperandInJson() {
        return "" +
                "{" +
                "  \"values\": [" +
                "    {" +
                "      \"value\": \"test-value\"" +
                "    }" +
                "  ]," +
                "  \"type\": \"test-type\"" +
                "}";
    }

    private static Operand createFunctionOperandInModel() {
        return new Function()
                .withName("function-name")
                .withParameters(Collections.singletonList(
                        new Parameter().withName("param-name")
                                .withExpression(new Value().withValue("test-value"))
                        )
                );
    }

    private static String createFunctionOperandInJson() {
        return "" +
                "{" +
                "  \"function\": {" +
                "    \"name\": \"function-name\"," +
                "    \"parameters\": [" +
                "      {" +
                "        \"name\": \"param-name\"," +
                "        \"value\": \"test-value\"" +
                "      }" +
                "    ]" +
                "  }" +
                "}";
    }

    private static Operand createOperatorOperandInModel() {
        return new Operator()
                .withType("equal")
                .withOperands(Collections.singletonList(new Value().withValue("test-value")));
    }

    private static String createOperatorOperandInJson() {
        return "" +
                "{" +
                "  \"equal\": [" +
                "    {" +
                "      \"value\": \"test-value\"" +
                "    }" +
                "  ]" +
                "}";
    }
}
