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

package org.apache.doris.nereids.rules.expression.rewrite.rules;

import org.apache.doris.nereids.rules.expression.rewrite.AbstractExpressionRewriteRule;
import org.apache.doris.nereids.rules.expression.rewrite.ExpressionRewriteContext;
import org.apache.doris.nereids.trees.expressions.Expression;
import org.apache.doris.qe.ConnectContext;

/**
 * Constant evaluation of an expression.
 */
public class FoldConstantRule extends AbstractExpressionRewriteRule {

    public static final FoldConstantRule INSTANCE = new FoldConstantRule();

    @Override
    public Expression rewrite(Expression expr, ExpressionRewriteContext ctx) {
        if (ctx.connectContext != null && ctx.connectContext.getSessionVariable().isEnableFoldConstantByBe()) {
            return FoldConstantRuleOnBE.INSTANCE.rewrite(expr, ctx);
        }
        return FoldConstantRuleOnFE.INSTANCE.rewrite(expr, ctx);
    }

    public Expression rewrite(Expression expr) {
        ExpressionRewriteContext ctx = new ExpressionRewriteContext(ConnectContext.get());
        return rewrite(expr, ctx);
    }

}

