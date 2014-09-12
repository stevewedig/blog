package com.stevewedig.blog.digraph;

import static org.junit.Assert.assertEquals;

import java.util.*;

import org.junit.Test;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import com.stevewedig.blog.digraph.id_graph.*;
import com.stevewedig.blog.errors.*;
import com.stevewedig.blog.value_objects.*;

public class TestExampleTwitterGraph {

  // ===========================================================================
  // interfaces
  // ===========================================================================

  interface TwitterAccount {

    String name();

    ImmutableSet<String> following();

    ImmutableSet<String> followers();

    int authority();
  }

  interface TwitterService {

    TwitterAccount getAccount(String name);

    void addAccount(String name);

    void addFollowing(String follower, String following);

  }

  // ===========================================================================
  // TwitterAccountClass
  // ===========================================================================

  static class TwitterAccountClass extends ValueMixin implements TwitterAccount {

    // =================================
    // state
    // =================================

    private final String name;
    private final ImmutableSet<String> following;
    private final ImmutableSet<String> followers;
    private final int authority;

    @Override
    protected Object[] fields() {
      return array("name", name, "following", following, "followers", followers, "authority",
          authority);
    }

    // =================================
    // constructor
    // =================================

    public TwitterAccountClass(String name, ImmutableSet<String> following,
        ImmutableSet<String> followers, int authority) {
      this.name = name;
      this.following = following;
      this.followers = followers;
      this.authority = authority;
    }

    // =================================
    // properties
    // =================================

    @Override
    public String name() {
      return name;
    }

    @Override
    public ImmutableSet<String> following() {
      return following;
    }

    @Override
    public ImmutableSet<String> followers() {
      return followers;
    }

    @Override
    public int authority() {
      return authority;
    }

  }

  // ===========================================================================
  // TwitterServiceClass
  // ===========================================================================

  static class TwitterServiceClass extends EntityMixin implements TwitterService {

    // =================================
    // state
    // =================================

    private final Set<String> names = new HashSet<>();
    private final SetMultimap<String, String> follower__following = HashMultimap.create();

    @Override
    protected Object[] fields() {
      return array("names", names, "follower__following", follower__following);
    }

    // =================================
    // graph
    // =================================

    private IdGraph<String> graph() {
      if (!graph.isPresent())
        graph = Optional.of(IdGraphLib.fromParentMap(names, follower__following));
      return graph.get();
    }

    // this is an application where a mutable graph would be more efficient
    private void resetGraph() {
      graph = Optional.absent();
    }

    private Optional<IdGraph<String>> graph = Optional.absent();

    // =================================
    // getAccount
    // =================================

    @Override
    public TwitterAccount getAccount(String name) {

      assertContains(name);

      IdGraph<String> graph = graph();

      ImmutableSet<String> following = graph.parentIdSet(name);

      ImmutableSet<String> followers = graph.childIdSet(name);

      // Define authority as the number of followers, and followers of followers, and so forth.
      //
      // Something like PageRank would be better:
      // http://en.wikipedia.org/wiki/PageRank
      int authority = graph.descendantIdSet(name).size();

      return new TwitterAccountClass(name, following, followers, authority);
    }

    // =================================
    // assertContains
    // =================================

    private void assertContains(String name) {
      if (!names.contains(name))
        throw new NotContained("name = %s", name);
    }

    // =================================
    // addAccount
    // =================================

    @Override
    public void addAccount(String name) {

      if (names.contains(name))
        throw new AlreadyContained("name = %s", name);

      names.add(name);

      resetGraph();
    }

    // =================================
    // addFollowing
    // =================================

    @Override
    public void addFollowing(String follower, String following) {

      assertContains(follower);
      assertContains(following);

      follower__following.put(follower, following);

      resetGraph();
    }
  }

  // ===========================================================================
  // test
  // ===========================================================================

  @Test
  public void testTwitterGraph() {

    TwitterService service = new TwitterServiceClass();

    // =================================
    // accounts
    // =================================

    service.addAccount("BarackObama");
    service.addAccount("charlierose");
    service.addAccount("tim_cook");
    service.addAccount("wedig");

    // =================================
    // arcs
    // =================================

    // cycle between tim cook and charlie rose
    service.addFollowing("charlierose", "BarackObama");
    service.addFollowing("charlierose", "tim_cook");
    service.addFollowing("tim_cook", "charlierose");
    service.addFollowing("wedig", "tim_cook");

    // =================================
    // barack obama: authority 3
    // =================================

    TwitterAccount barackObama =
        new TwitterAccountClass("BarackObama", ImmutableSet.<String>of(),
            ImmutableSet.of("charlierose"), 3);

    assertEquals(barackObama, service.getAccount("BarackObama"));

    // =================================
    // charlie rose: authority 2
    // =================================

    TwitterAccount charlieRose =
        new TwitterAccountClass("charlierose", ImmutableSet.of("BarackObama", "tim_cook"),
            ImmutableSet.of("tim_cook"), 2);

    assertEquals(charlieRose, service.getAccount("charlierose"));

    // =================================
    // tim cook: authority 2
    // =================================

    TwitterAccount timeCook =
        new TwitterAccountClass("tim_cook", ImmutableSet.of("charlierose"), ImmutableSet.of(
            "charlierose", "wedig"), 2);

    assertEquals(timeCook, service.getAccount("tim_cook"));

    // =================================
    // steve wedig: authority 0
    // =================================

    TwitterAccount steveWedig =
        new TwitterAccountClass("wedig", ImmutableSet.of("tim_cook"), ImmutableSet.<String>of(), 0);

    assertEquals(steveWedig, service.getAccount("wedig"));

  }

}
