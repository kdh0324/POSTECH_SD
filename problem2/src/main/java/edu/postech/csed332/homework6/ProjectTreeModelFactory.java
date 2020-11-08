package edu.postech.csed332.homework6;

import com.intellij.ide.projectView.impl.nodes.PackageUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.util.*;

class ProjectTreeModelFactory {

    /**
     * Create a tree model that describes the structure of a java project. This method use JavaElementVisitor to
     * traverse the Java hierarchy of each root package in the source directory, and to create a tree. Each node is an
     * instance of {@link DefaultMutableTreeNode} that can have a user object. The user object of root is the project
     * itself, and other nodes have corresponding instances of PsiPackage, PsiClass, PsiMethod, and PsiField.
     *
     * @param project a project
     * @return a tree model to describe the structure of project
     */
    public static TreeModel createProjectTreeModel(Project project) {
        // the root node of the tree
        final DefaultMutableTreeNode root = new DefaultMutableTreeNode(project);

        // The visitor to traverse the Java hierarchy and to construct the tree
        final JavaElementVisitor visitor = new JavaElementVisitor() {
            final Deque<DefaultMutableTreeNode> stack = new ArrayDeque<>(Collections.singleton(root));

            @Override
            public void visitPackage(PsiPackage pack) {
                final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(pack);
                stack.getFirst().add(newChild);
                stack.push(newChild);
                Arrays.stream(pack.getSubPackages()).forEach(psiPackage -> psiPackage.accept(this));
                Arrays.stream(pack.getClasses()).forEach(psiClass -> psiClass.accept(this));
                stack.pop();
            }

            @Override
            public void visitClass(PsiClass aClass) {
                final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(aClass);
                stack.getFirst().add(newChild);
                stack.push(newChild);
                Arrays.stream(aClass.getInnerClasses()).forEach(psiClass -> psiClass.accept(this));
                Arrays.stream(aClass.getFields()).forEach(psiField -> psiField.accept(this));
                Arrays.stream(aClass.getMethods()).forEach(psiMethod -> psiMethod.accept(this));
                stack.pop();
            }

            @Override
            public void visitMethod(PsiMethod method) {
                final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(method);
                stack.getFirst().add(newChild);
            }

            @Override
            public void visitField(PsiField field) {
                final DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(field);
                stack.getFirst().add(newChild);
            }
        };

        // apply the visitor for each root package in the source directory
        getRootPackages(project).forEach(aPackage -> aPackage.accept(visitor));
        return new DefaultTreeModel(root);
    }

    /**
     * Returns the root package(s) in the source directory of a project. The default package will not be considered, as
     * it includes all Java classes. Note that classes in the default package (i.e., having no package statement) will
     * be ignored for this assignment. To be completed, this case must be separately handled.
     *
     * @param project a project
     * @return a set of root packages
     */
    private static Set<PsiPackage> getRootPackages(Project project) {
        final Set<PsiPackage> rootPackages = new HashSet<>();
        PsiElementVisitor visitor = new PsiElementVisitor() {
            @Override
            public void visitDirectory(PsiDirectory dir) {
                final PsiPackage psiPackage = JavaDirectoryService.getInstance().getPackage(dir);
                if (psiPackage != null && !PackageUtil.isPackageDefault(psiPackage))
                    rootPackages.add(psiPackage);
                else
                    Arrays.stream(dir.getSubdirectories()).forEach(sd -> sd.accept(this));
            }
        };

        ProjectRootManager rootManager = ProjectRootManager.getInstance(project);
        PsiManager psiManager = PsiManager.getInstance(project);
        Arrays.stream(rootManager.getContentSourceRoots())
                .map(psiManager::findDirectory)
                .filter(Objects::nonNull)
                .forEach(dir -> dir.accept(visitor));

        return rootPackages;
    }
}

