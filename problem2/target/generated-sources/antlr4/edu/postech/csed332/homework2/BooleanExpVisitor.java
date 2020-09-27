// Generated from edu\postech\csed332\homework2\BooleanExp.g4 by ANTLR 4.8
package edu.postech.csed332.homework2;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link BooleanExpParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface BooleanExpVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code constant}
	 * labeled alternative in {@link BooleanExpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(BooleanExpParser.ConstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryExp}
	 * labeled alternative in {@link BooleanExpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExp(BooleanExpParser.BinaryExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code unaryExp}
	 * labeled alternative in {@link BooleanExpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExp(BooleanExpParser.UnaryExpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code variable}
	 * labeled alternative in {@link BooleanExpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(BooleanExpParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenExp}
	 * labeled alternative in {@link BooleanExpParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExp(BooleanExpParser.ParenExpContext ctx);
}