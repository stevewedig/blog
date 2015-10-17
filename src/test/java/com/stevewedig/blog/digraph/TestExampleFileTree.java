package com.stevewedig.blog.digraph;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.stevewedig.blog.apache.commons.lang3.StringUtils;
import com.stevewedig.blog.digraph.id_graph.IdTree;
import com.stevewedig.blog.digraph.node.*;
import com.stevewedig.blog.digraph.node_graph.*;
import com.stevewedig.blog.errors.Bug;
import com.stevewedig.blog.util.LambdaLib.Fn1;
import com.stevewedig.blog.value_objects.ValueMixin;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestExampleFileTree {

  // ===========================================================================
  // interfaces
  // ===========================================================================

  interface FileNode extends UpNode<String> {

    String path();

    ImmutableList<String> pathParts();

    Optional<String> parentPath();

    boolean hasParent();

    boolean isRoot();

    boolean isDirectory();

    boolean isFile();

  }

  interface FileTree extends Tree<String, FileNode> {

    Iterable<FileNode> orderedDepthFirst();

    ImmutableSet<FileNode> fileSet();

    ImmutableSet<FileNode> directorySet();
  }

  // ===========================================================================
  // FileNodeClass
  // ===========================================================================

  static class FileNodeClass extends ValueMixin implements FileNode {

    // =================================
    // state
    // =================================

    private final String path;

    @Override
    protected Object[] fields() {
      return array("path", path);
    }

    // =================================
    // constructor
    // =================================

    private FileNodeClass(String path) {
      super();
      this.path = path;

      if (path.isEmpty() || path.charAt(0) != '/')
        throw new Bug("paths should start with /, got %s", path);
    }

    // =================================
    // path
    // =================================

    @Override
    public String path() {
      return path;
    }

    @Override
    public boolean isDirectory() {
      return path.charAt(path.length() - 1) == '/';
    }

    @Override
    public boolean isFile() {
      return !isDirectory();
    }

    @Override
    public boolean isRoot() {
      return path.equals("/");
    }

    @Override
    public boolean hasParent() {
      return !isRoot();
    }

    // =================================
    // pathParts
    // =================================

    @Override
    public ImmutableList<String> pathParts() {
      
      
      if (pathParts == null) {

        if (isRoot())
          pathParts = ImmutableList.of();
        else {
          String inner = StringUtils.strip(path(), "/");

          pathParts = ImmutableList.copyOf(Splitter.on('/').split(inner));
        }
      }

      return pathParts;
    }

    private ImmutableList<String> pathParts;

    // =================================
    // parentPath
    // =================================

    @Override
    public Optional<String> parentPath() {
      if (parentPath == null) {

        if (isRoot())
          parentPath = Optional.absent();
        else if (pathParts().size() == 1)
          parentPath = Optional.of("/");
        else {
          List<String> parts = Lists.newArrayList(pathParts());
          parts.remove(parts.size() - 1);
          parentPath = Optional.of('/' + Joiner.on('/').join(parts) + '/');
        }

      }
      return parentPath;
    }

    private Optional<String> parentPath;

    // =================================
    // implementing UpNode
    // =================================

    @Override
    public String id() {
      return path();
    }

    @Override
    public ImmutableSet<String> parentIds() {
      if (parentIds == null) {

        if (isRoot())
          parentIds = ImmutableSet.of();
        else
          parentIds = ImmutableSet.of(parentPath().get());
      }

      return parentIds;
    }

    private ImmutableSet<String> parentIds;

  }

  // ===========================================================================
  // createNode
  // ===========================================================================

  public static FileNode createNode(String path) {
    return new FileNodeClass(path);
  }

  // ===========================================================================
  // FileTreeClass
  // ===========================================================================

  static class FileTreeClass extends TreeClass<String, FileNode> implements FileTree {

    // =================================
    // constructor
    // =================================

    private FileTreeClass(IdTree<String> idTree, ImmutableBiMap<String, FileNode> id__node) {
      super(idTree, id__node, false);
    }

    // =================================
    // node filters
    // =================================

    @Override
    public ImmutableSet<FileNode> fileSet() {
      if (fileSet == null)
        fileSet = ImmutableSet.copyOf(Iterables.filter(nodeSet(), new Predicate<FileNode>() {
          @Override
          public boolean apply(FileNode node) {
            return node.isFile();
          }
        }));

      return fileSet;
    }

    private ImmutableSet<FileNode> fileSet;

    @Override
    public ImmutableSet<FileNode> directorySet() {
      if (directorySet == null)
        directorySet = ImmutableSet.copyOf(Iterables.filter(nodeSet(), new Predicate<FileNode>() {
          @Override
          public boolean apply(FileNode node) {
            return node.isDirectory();
          }
        }));

      return directorySet;
    }

    private ImmutableSet<FileNode> directorySet;

    // =================================
    // providing a new depth first traversal, one that orders nodes alphabetically
    // =================================

    @Override
    public Iterable<FileNode> orderedDepthFirst() {

      boolean depthFirst = true;
      boolean inclusive = true;

      ImmutableList<String> startIds = ImmutableList.of(rootId());

      return traverseNodeIterable(depthFirst, inclusive, startIds, node__childList);
    }

    private Fn1<FileNode, List<String>> node__childList = new Fn1<FileNode, List<String>>() {
      @Override
      public List<String> apply(FileNode node) {

        List<String> childIdList = Lists.newArrayList(childIdSet(node.path()));

        // order child nodes alphabetically
        Collections.sort(childIdList);

        return childIdList;
      }
    };
  }

  // ===========================================================================
  // createTree
  // ===========================================================================

  public static FileTree createTree(FileNode... nodes) {
    return createTree(ImmutableSet.copyOf(nodes));
  }

  public static FileTree createTree(ImmutableSet<FileNode> nodes) {

    ImmutableSet<FileNode> allPaths = addAncestorNodes(nodes);

    IdTree<String> idTree = UpNodeLib.nodes__idTree(allPaths);

    ImmutableBiMap<String, FileNode> id__node = UpNodeLib.nodes__nodeMap(allPaths);

    return new FileTreeClass(idTree, id__node);
  }

  // ===================================

  private static ImmutableSet<FileNode> addAncestorNodes(ImmutableSet<FileNode> nodes) {

    Map<String, FileNode> path__node = new HashMap<>();

    for (FileNode node : nodes)
      path__node.put(node.path(), node);

    for (FileNode node : nodes) {

      FileNode currentNode = node;

      while (currentNode.hasParent() && !path__node.containsKey(currentNode.parentPath().get())) {

        FileNode parentNode = createNode(currentNode.parentPath().get());

        path__node.put(parentNode.path(), parentNode);

        currentNode = parentNode;
      }
    }

    return ImmutableSet.copyOf(path__node.values());
  }

  // ===========================================================================
  // tests
  // ===========================================================================

  @Test
  public void testExampleFileTree() {

    // =================================
    // paths
    // =================================

    FileNode root = createNode("/");
    FileNode license = createNode("/license.txt");
    FileNode code = createNode("/code/");
    FileNode hello = createNode("/code/HelloWorld.java");
    FileNode tests = createNode("/code/tests/");
    FileNode testHello = createNode("/code/tests/TestHelloWorld.java");

    // =================================
    // verify custom node class
    // =================================

    assertTrue(root.isRoot());
    assertTrue(root.isDirectory());
    assertFalse(root.parentPath().isPresent());

    assertFalse(license.isRoot());
    assertFalse(license.isDirectory());
    assertEquals("/", license.parentPath().get());

    assertFalse(code.isRoot());
    assertTrue(code.isDirectory());
    assertEquals("/", code.parentPath().get());

    assertFalse(hello.isRoot());
    assertFalse(hello.isDirectory());
    assertEquals("/code/", hello.parentPath().get());

    assertFalse(tests.isRoot());
    assertTrue(tests.isDirectory());
    assertEquals("/code/", tests.parentPath().get());

    assertFalse(testHello.isRoot());
    assertFalse(testHello.isDirectory());
    assertEquals("/code/tests/", testHello.parentPath().get());

    // =================================
    // verify custom tree class
    // =================================

    FileTree tree = createTree(root, license, code, hello, tests, testHello);

    assertEquals(6, tree.nodeSize());

    assertEquals(ImmutableSet.of(hello, tests), tree.childNodeSet("/code/"));

    assertEquals(ImmutableSet.of(hello, tests, testHello), tree.descendantNodeSet("/code/", false));

    assertEquals(ImmutableSet.of(root, code, tests), tree.directorySet());

    assertEquals(ImmutableSet.of(license, hello, testHello), tree.fileSet());

    assertEquals(ImmutableList.of(root, code, hello, tests, testHello, license),
        ImmutableList.copyOf(tree.orderedDepthFirst()));

    // =================================
    // verify that ancestor nodes are filled in
    // =================================

    assertEquals(tree, createTree(license, hello, testHello));

  }
}
