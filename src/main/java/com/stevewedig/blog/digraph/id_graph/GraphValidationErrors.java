package com.stevewedig.blog.digraph.id_graph;

import com.stevewedig.blog.errors.ErrorMixin;

public abstract class GraphValidationErrors {

  public static class GraphContainedUnexpectedIds extends ErrorMixin {
    private static final long serialVersionUID = 1L;

    public GraphContainedUnexpectedIds() {
      super();
    }

    public GraphContainedUnexpectedIds(String template, Object... parts) {
      super(template, parts);
    };
  }
  
  public static class DagCannotHaveCycle extends ErrorMixin {
    private static final long serialVersionUID = 1L;
    
    public DagCannotHaveCycle() {
      super();
    }
    
    public DagCannotHaveCycle(String template, Object... parts) {
      super(template, parts);
    };
  }
  
  public static class TreeCannotHaveMultipleRoots extends ErrorMixin {
    private static final long serialVersionUID = 1L;

    public TreeCannotHaveMultipleRoots() {
      super();
    }

    public TreeCannotHaveMultipleRoots(String template, Object... parts) {
      super(template, parts);
    };
  }
  
  public static class TreeCannotBeEmpty extends ErrorMixin {
    private static final long serialVersionUID = 1L;
    
    public TreeCannotBeEmpty() {
      super();
    }
    
    public TreeCannotBeEmpty(String template, Object... parts) {
      super(template, parts);
    };
  }
  
  public static class TreeNodesCannotHaveMultipleParents extends ErrorMixin {
    private static final long serialVersionUID = 1L;
    
    public TreeNodesCannotHaveMultipleParents() {
      super();
    }
    
    public TreeNodesCannotHaveMultipleParents(String template, Object... parts) {
      super(template, parts);
    };
  }
  
}
