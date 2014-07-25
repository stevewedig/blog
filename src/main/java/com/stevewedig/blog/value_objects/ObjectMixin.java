package com.stevewedig.blog.value_objects;


/**
 * Base class of ValueMixin and EntityMixin.
 */
public abstract class ObjectMixin implements HasObjectHelper {

  // ===========================================================================
  // abstract hooks
  // ===========================================================================

  // objects are either entities or values
  protected abstract boolean isEntity();

  protected abstract Object[] fields();

  /**
   * Convenient helper for subclasses to implement fields() by returning array("field1", field1,
   * "field2", field2, ...). The longer alternative is to return new Object[]{"field1", field1,
   * "field2", field2, ...}.
   * 
   * http://rethinktheworld.blogspot.com/2010/06/literal-arrays-and-lists-in-java.html
   * 
   * @param items
   * @return
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
  // delegate to objectHelper
  // (ValueMixin also delegates equals and hashCode)
  // ===========================================================================

  @Override
  public String toString() {
    return objectHelper().objectString();
  }

}
