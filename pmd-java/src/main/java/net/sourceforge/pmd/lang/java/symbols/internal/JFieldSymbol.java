/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.java.symbols.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclaratorId;
import net.sourceforge.pmd.lang.java.ast.AccessNode;


/**
 * Represents a field declaration.
 *
 * @author Clément Fournier
 * @since 7.0.0
 */
public final class JFieldSymbol extends JAccessibleDeclarationSymbol<ASTVariableDeclaratorId> implements JValueSymbol {


    /**
     * Constructor for fields found through reflection.
     *
     * @param field          Field for which to create a reference
     */
    public JFieldSymbol(Field field) {
        super(field.getModifiers(), field.getName(), toResolvable(field.getDeclaringClass()));
    }


    /**
     * Constructor using the AST node.
     *
     * @param node           Node representing the id of the field, must be from an ASTFieldDeclaration
     */
    JFieldSymbol(ASTVariableDeclaratorId node) {
        super(node, getModifiers(node), node.getVariableName());

    }


    public boolean isVolatile() {
        return Modifier.isVolatile(modifiers);
    }


    public boolean isTransient() {
        return Modifier.isTransient(modifiers);
    }


    public boolean isStatic() {
        return Modifier.isStatic(modifiers);
    }


    @Override
    public boolean isFinal() {
        return Modifier.isFinal(modifiers);
    }


    private static int getModifiers(ASTVariableDeclaratorId node) {
        Node fieldDecl = node.jjtGetParent().jjtGetParent();
        if (fieldDecl instanceof ASTFieldDeclaration) {
            return accessNodeToModifiers((AccessNode) fieldDecl);
        } else {
            throw new IllegalArgumentException("Not a field id");
        }
    }
}
