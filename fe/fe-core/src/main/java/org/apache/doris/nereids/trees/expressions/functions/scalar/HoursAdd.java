// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.apache.doris.nereids.trees.expressions.functions.scalar;

import org.apache.doris.catalog.FunctionSignature;
import org.apache.doris.nereids.trees.expressions.Expression;
import org.apache.doris.nereids.trees.expressions.functions.AlwaysNullable;
import org.apache.doris.nereids.trees.expressions.functions.ExplicitlyCastableSignature;
import org.apache.doris.nereids.trees.expressions.shape.BinaryExpression;
import org.apache.doris.nereids.trees.expressions.visitor.ExpressionVisitor;
import org.apache.doris.nereids.types.DateTimeType;
import org.apache.doris.nereids.types.DateTimeV2Type;
import org.apache.doris.nereids.types.DateType;
import org.apache.doris.nereids.types.DateV2Type;
import org.apache.doris.nereids.types.IntegerType;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * ScalarFunction 'days_add'.
 */
public class HoursAdd extends ScalarFunction implements BinaryExpression, ExplicitlyCastableSignature, AlwaysNullable {
    public static final List<FunctionSignature> SIGNATURES = ImmutableList.of(
            FunctionSignature.ret(DateTimeType.INSTANCE).args(DateTimeType.INSTANCE, IntegerType.INSTANCE),
            FunctionSignature.ret(DateTimeV2Type.SYSTEM_DEFAULT)
                    .args(DateTimeV2Type.SYSTEM_DEFAULT, IntegerType.INSTANCE),
            FunctionSignature.ret(DateTimeType.INSTANCE).args(DateType.INSTANCE, IntegerType.INSTANCE),
            FunctionSignature.ret(DateTimeV2Type.SYSTEM_DEFAULT).args(DateV2Type.INSTANCE, IntegerType.INSTANCE)
    );

    public HoursAdd(Expression arg0, Expression arg1) {
        super("hours_add", arg0, arg1);
    }

    @Override
    public HoursAdd withChildren(List<Expression> children) {
        Preconditions.checkArgument(children.size() == 2);
        return new HoursAdd(children.get(0), children.get(1));
    }

    @Override
    public List<FunctionSignature> getSignatures() {
        return SIGNATURES;
    }

    @Override
    public <R, C> R accept(ExpressionVisitor<R, C> visitor, C context) {
        return visitor.visitHoursAdd(this, context);
    }
}
