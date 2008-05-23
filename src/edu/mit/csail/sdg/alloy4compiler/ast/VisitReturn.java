/*
 * Alloy Analyzer 4 -- Copyright (c) 2006-2008, Felix Chang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package edu.mit.csail.sdg.alloy4compiler.ast;

import edu.mit.csail.sdg.alloy4.Err;
import edu.mit.csail.sdg.alloy4compiler.ast.Sig.Field;

/** This abstract class defines what a Return Visitor's interface needs to be. */

public abstract class VisitReturn<T,E> {

    /** Constructs a VisitReturn object. */
    public VisitReturn() { }

    /** This is the start method that begins a traversal over the given expression. */
    public final T visitThis(E context, Expr x) throws Err { return x.accept(context, this); }

    /** Visits an ExprBinary node. */
    public abstract T visit(E context, ExprBinary x) throws Err;

    /** Visits an ExprBuiltin node. */
    public abstract T visit(E context, ExprBuiltin x) throws Err;

    /** Visits an ExprCall node. */
    public abstract T visit(E context, ExprCall x) throws Err;

    /** Visits an ExprConstant node. */
    public abstract T visit(E context, ExprConstant x) throws Err;

    /** Visits an ExprITE node. */
    public abstract T visit(E context, ExprITE x) throws Err;

    /** Visits an ExprLet node. */
    public abstract T visit(E context, ExprLet x) throws Err;

    /** Visits an ExprQuant node. */
    public abstract T visit(E context, ExprQuant x) throws Err;

    /** Visits an ExprUnary node. */
    public abstract T visit(E context, ExprUnary x) throws Err;

    /** Visits an ExprVar node. */
    public abstract T visit(E context, ExprVar x) throws Err;

    /** Visits a Sig node. */
    public abstract T visit(E context, Sig x) throws Err;

    /** Visits a Field node. */
    public abstract T visit(E context, Field x) throws Err;
}
