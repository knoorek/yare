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
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static net.javacrumbs.jsonunit.fluent.JsonFluentAssert.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;

class AttributeSerializationTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = RuleToJsonConverter.getObjectMapper();
    }

    @Test
    void shouldProperlySerializeAttribute() throws JsonProcessingException {
        Attribute attribute = getAttributeModel();

        String serialized = objectMapper.writeValueAsString(attribute);

        String expected = getAttributeJson();
        assertThatJson(serialized).isEqualTo(expected);
    }

    @Test
    void shouldProperlyDeserializeAttribute() throws IOException {
        String serialized = getAttributeJson();

        Attribute attribute = objectMapper.readValue(serialized, Attribute.class);

        Attribute expected = getAttributeModel();
        assertThat(attribute).isEqualTo(expected);
    }

    private Attribute getAttributeModel() {
        return new Attribute()
                .withName("attribute-name")
                .withValue("attribute-value")
                .withType("attribute-type");
    }

    private String getAttributeJson() {
        return "" +
                "{" +
                "  \"name\": \"attribute-name\"," +
                "  \"value\": \"attribute-value\"," +
                "  \"type\": \"attribute-type\"" +
                "}";
    }
}
