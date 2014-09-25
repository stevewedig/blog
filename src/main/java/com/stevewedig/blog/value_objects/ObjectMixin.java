package com.stevewedig.blog.value_objects;

/**
 * Base class of ValueMixin and EntityMixin.
 */
public abstract class ObjectMixin implements HasObjectHelper {

  // ===========================================================================
  // abstract hooks
  // ===========================================================================

  // Objects are either entities or values.
  //
  // Having this as a flag at this level enables us to setup implementation inheritance hierarchies
  // that looks like:
  // ContainerMixin < ObjectMixin
  // ImmutableContainer < ContainerMixin (set isEntity to false)
  // MutableContainer < ContainerMixin (set isEntity to true)
  /**
   * Should this object behave as an entity or a value?
   */
  protected abstract boolean isEntity();

  /**
   * An array of alternating field names and field values.
   */
  protected abstract Object[] fields();

  // ===========================================================================
  // utility
  // ===========================================================================

  /**
   * Convenience method so subclasses can implement fields() by returning array("field1", field1,
   * "field2", field2, ...). The longer alternative is to return new Object[]{"field1", field1,
   * "field2", field2, ...}.
   * 
   * http://rethinktheworld.blogspot.com/2010/06/literal-arrays-and-lists-in-java.html
   */
  protected Object[] array(Object... items) {
    return items;
  }

  // ===========================================================================
  // creating objectHelper
  // ===========================================================================

  @Override
  public ObjectHelper objectHelper() {

    if (isEntity())
      return uncachedHelper();

    if (cachedHelper == null)
      cachedHelper = uncachedHelper();

    return cachedHelper;
  }

  private ObjectHelper uncachedHelper() {
    return ObjectHelperLib.helper(getClass(), fields());
  }

  private ObjectHelper cachedHelper;

  // ===========================================================================
  // delegating to objectHelper
  // ===========================================================================

  @Override
  public String toString() {
    return objectHelper().classAndStateString();
  }

  // ValueMixin also delegates equals() and hashCode()

}
