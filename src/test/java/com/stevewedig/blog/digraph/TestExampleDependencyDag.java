package com.stevewedig.blog.digraph;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.*;
import com.stevewedig.blog.digraph.errors.DagCannotHaveCycle;
import com.stevewedig.blog.digraph.node.UpNode;
import com.stevewedig.blog.digraph.node_graph_partial.*;
import com.stevewedig.blog.errors.NotThrown;
import com.stevewedig.blog.value_objects.ValueMixin;

public class TestExampleDependencyDag {

  // ===========================================================================
  // interfaces
  // ===========================================================================

  /**
   * A code file with a path and imported dependency paths.
   */
  interface Module extends UpNode<String> {

    String path();

    ImmutableSet<String> importPaths();
  }

  /**
   * A set of provided dependency paths.
   */
  interface Environment {

    ImmutableSet<String> providedPaths();

    ImmutableList<Module> findBuildOrder(Module... modules) throws MissingDependency,
        DagCannotHaveCycle;
  }

  // ===========================================================================
  // MissingDependency
  // ===========================================================================

  /**
   * Thrown when a set of modules cannot be built in an environment.
   */
  static class MissingDependency extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final ImmutableSet<String> missingPaths;

    public MissingDependency(Iterable<String> missingPaths) {
      super();
      this.missingPaths = ImmutableSet.copyOf(missingPaths);
    }

    public ImmutableSet<String> missingPaths() {
      return missingPaths;
    }
  }

  // ===========================================================================
  // ModuleClass
  // ModuleClass
  // ===========================================================================

  static class ModuleClass extends ValueMixin implements Module {

    // =================================
    // state
    // =================================

    private final String path;
    private final ImmutableSet<String> importPaths;

    @Override
    protected Object[] fields() {
      return array("path", path, "importPaths", importPaths);
    }

    // =================================
    // constructor
    // =================================

    public ModuleClass(String path, String... importPaths) {
      super();
      this.path = path;
      this.importPaths = ImmutableSet.copyOf(importPaths);
    }

    // =================================
    // properties
    // =================================

    @Override
    public String path() {
      return path;
    }

    @Override
    public ImmutableSet<String> importPaths() {
      return importPaths;
    }

    // =================================
    // implementing UpNode
    // =================================

    @Override
    public String id() {
      return path();
    }

    @Override
    public ImmutableSet<String> parentIds() {
      return importPaths();
    }

  }

  // ===========================================================================
  // EnvironmentClass
  // ===========================================================================

  static class EnvironmentClass extends ValueMixin implements Environment {

    // =================================
    // state
    // =================================

    private final ImmutableSet<String> providedPaths;

    @Override
    protected Object[] fields() {
      return array("providePaths", providedPaths);
    }

    // =================================
    // constructor
    // =================================

    public EnvironmentClass(String... providedPaths) {
      super();
      this.providedPaths = ImmutableSet.copyOf(providedPaths);
    }

    // =================================
    // properties
    // =================================

    @Override
    public ImmutableSet<String> providedPaths() {
      return providedPaths;
    }

    // =================================
    // buildOrder
    // =================================

    @Override
    public ImmutableList<Module> findBuildOrder(Module... modules) throws MissingDependency {

      // use DagLib.up.up() because Module < UpNode
      PartialDag<String, Module> dag = PartialDagLib.up(modules);

      validateDependencies(dag);

      return dag.transformList(dag.topsortIdList(), true);
    }

    private void validateDependencies(PartialDag<String, Module> dag) {

      ImmutableSet<String> dependencyPaths = dag.unboundIdSet();

      Set<String> missingPaths = Sets.difference(dependencyPaths, providedPaths());

      if (!missingPaths.isEmpty())
        throw new MissingDependency(missingPaths);
    }

  }

  // ===========================================================================
  // tests
  // ===========================================================================

  @Test
  public void testExampleDependencyDag() {

    // =================================
    // environments
    // =================================

    Environment partialEnv = new EnvironmentClass("java.sql");

    Environment fullEnv = new EnvironmentClass("java.sql", "com.google.gson", "javax.servlet");

    // =================================
    // modules
    // =================================

    Module basicRepo = new ModuleClass("com.example.BasicRepo", "java.sql");

    Module jsonRepo = new ModuleClass("com.example.JsonRepo", "java.sql", "com.google.gson");

    Module component =
        new ModuleClass("com.example.Component", "com.example.JsonRepo", "com.example.BasicRepo");

    Module service =
        new ModuleClass("com.example.Service", "com.example.Component", "javax.servlet");

    // =================================
    // basicRepo
    // =================================

    assertEquals(ImmutableList.of(basicRepo), partialEnv.findBuildOrder(basicRepo));

    // =================================
    // jsonRepo
    // =================================

    try {
      partialEnv.findBuildOrder(jsonRepo);

      throw new NotThrown(MissingDependency.class);
    } catch (MissingDependency e) {
      assertEquals(ImmutableSet.of("com.google.gson"), e.missingPaths());
    }

    assertEquals(ImmutableList.of(jsonRepo), fullEnv.findBuildOrder(jsonRepo));

    // =================================
    // component
    // =================================

    try {
      fullEnv.findBuildOrder(jsonRepo, component);

      throw new NotThrown(MissingDependency.class);
    } catch (MissingDependency e) {
      assertEquals(ImmutableSet.of("com.example.BasicRepo"), e.missingPaths());
    }

    try {
      partialEnv.findBuildOrder(basicRepo, jsonRepo, component);

      throw new NotThrown(MissingDependency.class);
    } catch (MissingDependency e) {
      assertEquals(ImmutableSet.of("com.google.gson"), e.missingPaths());
    }

    try {
      partialEnv.findBuildOrder(jsonRepo, component);

      throw new NotThrown(MissingDependency.class);
    } catch (MissingDependency e) {
      assertEquals(ImmutableSet.of("com.google.gson", "com.example.BasicRepo"), e.missingPaths());
    }

    ImmutableList<Module> middleOrder = fullEnv.findBuildOrder(basicRepo, jsonRepo, component);

    ImmutableSet<ImmutableList<Module>> middleOrders =
        ImmutableSet.of(ImmutableList.of(basicRepo, jsonRepo, component),
            ImmutableList.of(jsonRepo, basicRepo, component));

    assertTrue(middleOrders.contains(middleOrder));

    // =================================
    // service
    // =================================

    try {
      fullEnv.findBuildOrder(jsonRepo, basicRepo, service);

      throw new NotThrown(MissingDependency.class);
    } catch (MissingDependency e) {
      assertEquals(ImmutableSet.of("com.example.Component"), e.missingPaths());
    }

    try {
      partialEnv.findBuildOrder(jsonRepo, basicRepo, component, service);

      throw new NotThrown(MissingDependency.class);
    } catch (MissingDependency e) {
      assertEquals(ImmutableSet.of("com.google.gson", "javax.servlet"), e.missingPaths());
    }

    try {
      partialEnv.findBuildOrder(jsonRepo, component, service);

      throw new NotThrown(MissingDependency.class);
    } catch (MissingDependency e) {
      assertEquals(ImmutableSet.of("com.google.gson", "javax.servlet", "com.example.BasicRepo"),
          e.missingPaths());
    }

    ImmutableList<Module> serviceOrder =
        fullEnv.findBuildOrder(basicRepo, jsonRepo, component, service);

    ImmutableSet<ImmutableList<Module>> serviceOrders =
        ImmutableSet.of(ImmutableList.of(basicRepo, jsonRepo, component, service),
            ImmutableList.of(jsonRepo, basicRepo, component, service));

    assertTrue(serviceOrders.contains(serviceOrder));

  }

  @Test
  public void testExampleWithCycle() {

    // =================================
    // environment
    // =================================

    Environment env = new EnvironmentClass();

    // =================================
    // modules
    // =================================

    Module a = new ModuleClass("com.example.A", "com.example.B");

    Module b = new ModuleClass("com.example.B", "com.example.C");

    Module c = new ModuleClass("com.example.C", "com.example.A");

    // =================================
    // attempt to find build order
    // =================================

    try {
      env.findBuildOrder(a, b, c);

      throw new NotThrown(DagCannotHaveCycle.class);
    } catch (DagCannotHaveCycle e) {
    }

  }
}
