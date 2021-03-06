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

package com.sabre.oss.yare.serializer.xml.mapper.converter.rule;

import com.sabre.oss.yare.core.model.Expression;
import com.sabre.oss.yare.serializer.model.ActionSer;

import java.util.List;
import java.util.stream.Collectors;

import static com.sabre.oss.yare.core.model.ExpressionFactory.actionOf;

class ActionConverter {
    private final NodeConverter nodeConverter;

    ActionConverter(NodeConverter nodeConverter) {
        this.nodeConverter = nodeConverter;
    }

    List<Expression.Action> convert(List<ActionSer> action) {
        return action.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private Expression.Action convert(ActionSer action) {
        return actionOf(action.getName(), action.getName(), nodeConverter.convert(action.getParameter()));
    }
}
