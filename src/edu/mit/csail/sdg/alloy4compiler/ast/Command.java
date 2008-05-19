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

import java.util.List;
import edu.mit.csail.sdg.alloy4.ConstList;
import edu.mit.csail.sdg.alloy4.Pair;
import edu.mit.csail.sdg.alloy4.Pos;
import edu.mit.csail.sdg.alloy4.ConstList.TempList;

/**
 * Immutable; reresents a "run" or "check" command.
 *
 * <p> <b>Invariant:</b>  expects == -1, 0, or 1
 * <p> <b>Invariant:</b>  overall >= -1
 * <p> <b>Invariant:</b>  bitwidth >= -1
 * <p> <b>Invariant:</b>  maxseq >= -1
 */

public final class Command {

    /** The position in the original file where this command was declared; never null. */
    public final Pos pos;

    /** The label for the command; it is just for pretty-printing and does not have to be unique. */
    public final String label;

    /** true if this is a "check"; false if this is a "run". */
    public final boolean check;

    /** The overall scope (0 or higher) (Or -1 if there is no overall scope). */
    public final int overall;

    /** The integer bitwidth (0 or higher) (Or -1 if it was not specified). */
    public final int bitwidth;

    /** The maximum sequence length (0 or higher) (Or -1 if it was not specified). */
    public final int maxseq;

    /** The expected answer (either 0 or 1) (Or -1 if there is no expected answer). */
    public final int expects;

    /**
     * This maps each signature to a number that represents its bound as well as its exactness.
     * <p> If the number N is >= 0: the sig is bound to have at most N atoms.
     * <p> Otherwise: the sig is bound to have exactly (0-(N+1)) atoms.
     */
    public final ConstList<Pair<Sig,Integer>> scope;

    /** This stores a list of Sig whose scope shall be considered "exact", but we don't know what its scope is yet. */
    public final ConstList<Sig> additionalExactScopes;

    /** Returns a human-readable string that summarizes this Run or Check command. */
    @Override public final String toString() {
        boolean first=true;
        StringBuilder sb=new StringBuilder(check?"Check ":"Run ").append(label);
        if (overall>=0 && (bitwidth>=0 || maxseq>=0 || scope.size()>0))
            sb.append(" for ").append(overall).append(" but");
        else if (overall>=0)
            sb.append(" for ").append(overall);
        else if (bitwidth>=0 || maxseq>=0 || scope.size()>0)
            sb.append(" for");
        if (bitwidth>=0) { sb.append(" ").append(bitwidth).append(" int"); first=false; }
        if (maxseq>=0) { sb.append(first?" ":", ").append(maxseq).append(" seq"); first=false; }
        for(Pair<Sig,Integer> e:scope) {
            sb.append(first?" ":", ");
            int num=e.b;
            if (num<0) { sb.append("exactly "); num=0-(num+1); }
            String label=e.a.label;
            int index=label.lastIndexOf('/');
            if (index>=0) label=label.substring(index+1);
            sb.append(num).append(' ').append(label);
            first=false;
        }
        if (expects>=0) sb.append(" expect ").append(expects);
        return sb.toString();
    }

    /**
     * Constructs a new Command object.
     *
     * @param pos - the original position in the file (must not be null)
     * @param label - the label for this command (it is only for pretty-printing and does not have to be unique)
     * @param check - true if this is a "check"; false if this is a "run"
     * @param overall - the overall scope (0 or higher) (-1 if no overall scope was specified)
     * @param bitwidth - the integer bitwidth (0 or higher) (-1 if it was not specified)
     * @param maxseq - the maximum sequence length (0 or higher) (-1 if it was not specified)
     * @param expects - the expected value (0 or 1) (-1 if no expectation was specified)
     * @param scope - Sig-to-Integer map (see the "scope" field of the Command class for its meaning)
     * @param additionalExactSig - a list of sigs whose scope shall be considered exact but we may not know what the scope is yet
     */
    public Command(Pos pos, String label, boolean check, int overall, int bitwidth,
    int maxseq, int expects, List<Pair<Sig,Integer>> scope, Sig... additionalExactSig) {
        if (pos==null) pos = Pos.UNKNOWN;
        this.pos = pos;
        this.label = (label==null ? "" : label);
        this.check = check;
        this.overall = (overall<0 ? -1 : overall);
        this.bitwidth = (bitwidth<0 ? -1 : bitwidth);
        this.maxseq = (maxseq<0 ? -1 : maxseq);
        this.expects = (expects<0 ? -1 : (expects>0 ? 1 : 0));
        this.scope = ConstList.make(scope);
        TempList<Sig> tmp = new TempList<Sig>(additionalExactSig.length);
        for(int i=0; i<additionalExactSig.length; i++) tmp.add(additionalExactSig[i]);
        this.additionalExactScopes = tmp.makeConst();
    }

    /**
     * Constructs a new Command object where it is the same as the current object, except with a different scope.
     * @param scope - Sig-to-Integer map to be associated with the new command (see the "scope" field for its meaning)
     */
    public Command make(ConstList<Pair<Sig,Integer>> scope) {
        return new Command(pos, label, check, overall, bitwidth, maxseq, expects, scope, additionalExactScopes.toArray(new Sig[additionalExactScopes.size()]));
    }

    /**
     * Constructs a new Command object where it is the same as the current object, except with a different list of additional exact sigs.
     * @param additionalExactScopes - a list of sigs whose scope shall be considered exact (even though we may not know its scope yet)
     */
    public Command make(Sig... additionalExactScopes) {
        return new Command(pos, label, check, overall, bitwidth, maxseq, expects, scope, additionalExactScopes);
    }
}
